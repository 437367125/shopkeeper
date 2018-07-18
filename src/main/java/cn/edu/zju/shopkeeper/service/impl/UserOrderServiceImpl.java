package cn.edu.zju.shopkeeper.service.impl;

import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.*;
import cn.edu.zju.shopkeeper.domain.req.OrderCommodityRelationshipReq;
import cn.edu.zju.shopkeeper.domain.req.UserOrderReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.AddressVO;
import cn.edu.zju.shopkeeper.domain.vo.CommodityVO;
import cn.edu.zju.shopkeeper.domain.vo.UserOrderVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.mapper.*;
import cn.edu.zju.shopkeeper.service.UserOrderService;
import cn.edu.zju.shopkeeper.utils.DozerBeanUtil;
import cn.edu.zju.shopkeeper.utils.OrderNumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午10:58
 * Description 用户订单服务实现类
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(UserOrderServiceImpl.class);
    private BankcardMapper bankcardMapper;
    private UserOrderMapper userOrderMapper;
    private OrderCommodityRelationshipMapper orderCommodityRelationshipMapper;
    private CommodityMapper commodityMapper;
    private AddressMapper addressMapper;

    @Autowired
    public UserOrderServiceImpl(BankcardMapper bankcardMapper, UserOrderMapper userOrderMapper, OrderCommodityRelationshipMapper orderCommodityRelationshipMapper, CommodityMapper commodityMapper, AddressMapper addressMapper) {
        this.bankcardMapper = bankcardMapper;
        this.userOrderMapper = userOrderMapper;
        this.orderCommodityRelationshipMapper = orderCommodityRelationshipMapper;
        this.commodityMapper = commodityMapper;
        this.addressMapper = addressMapper;
    }


    /**
     * 创建新订单
     *
     * @param req
     * @throws ShopkeeperException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseRes createOrder(UserOrderReq req) throws ShopkeeperException {
        logger.info("invoke UserOrderServiceImpl createOrder, req:{}", req);
        //参数校验
        if (req.getUserId() == null || req.getType() == null ||
                req.getBankcardId() == null || CollectionUtils.isEmpty(req.getCommodityList()) ||
                (ShopkeeperConstant.NEED_DELIVERY.equals(req.getType()) && req.getAddressId() == null)) {
            logger.error("UserOrderServiceImpl createOrder missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        //首先校验商品库存，校验通过后获取商品的列表，便于接下来的库存扣除
        List<Commodity> commodities = checkInventory(req.getCommodityList());
        if (commodities == null) {
            throw new ShopkeeperException(ResultEnum.INVENTORY_SHORTAGE);
        }
        //计算商品总额、总数
        Double totalPrice = 0.0;
        Integer totalNum = 0;
        for (int i = 0; i < commodities.size(); i++) {
            totalPrice += commodities.get(i).getPrice() * req.getCommodityList().get(i).getCount();
            totalNum += req.getCommodityList().get(i).getCount();
        }
        req.setTotalNum(totalNum);
        req.setTotalPrice(totalPrice);
        //创建订单前先进行付款
        if (!pay(req)) {
            throw new ShopkeeperException(ResultEnum.INSUFFICIENT_BALANCE);
        }
        //创建一个新订单，并获取订单的主键
        Integer userOrderId = createNewOrder(req);
        //创建订单-商品关系
        req.setId(userOrderId);
        createOrderCommodityRelationship(req, commodities);
        res.setResultCode(ResultEnum.SUCCESS.getCode());
        res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        return res;
    }

    /**
     * 删除订单
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes deleteOrder(UserOrderReq req) throws ShopkeeperException {
        logger.info("invoke UserOrderServiceImpl deleteOrder, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("UserOrderServiceImpl deleteOrder missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        try {
            userOrderMapper.deleteOrder(req.getId());
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("UserOrderServiceImpl deleteOrder error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 订单发货
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes updateDelivery(UserOrderReq req) throws ShopkeeperException {
        logger.info("invoke UserOrderServiceImpl updateDelivery, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("UserOrderServiceImpl updateDelivery missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            UserOrder userOrderInDatabase = userOrderMapper.getUserOrderById(DozerBeanUtil.map(req, UserOrder.class));
            if (userOrderInDatabase == null || !ShopkeeperConstant.WAITING_DELIVERY.equals(userOrderInDatabase.getStatus())) {
                res.setResultCode(ResultEnum.ORDER_STATUS_ERROR.getCode());
                res.setResultMsg(ResultEnum.ORDER_STATUS_ERROR.getMsg());
                return res;
            }
            UserOrder entity = new UserOrder();
            entity.setId(req.getId());
            entity.setDeliveryTime(date);
            entity.setStatus(ShopkeeperConstant.DELIVERY);
            userOrderMapper.updateDelivery(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("UserOrderServiceImpl updateDelivery error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 确认收货
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes updateComplete(UserOrderReq req) throws ShopkeeperException {
        logger.info("invoke UserOrderServiceImpl updateComplete, req:{}", req);
        //参数校验
        if (req.getId() == null || req.getUserId() == null) {
            logger.error("UserOrderServiceImpl updateComplete missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            //首先判断订单状态是否正常
            UserOrder userOrderInDatabase = userOrderMapper.getUserOrderById(DozerBeanUtil.map(req, UserOrder.class));
            if (userOrderInDatabase == null || !userOrderInDatabase.getUserId().equals(req.getUserId()) ||
                    !ShopkeeperConstant.DELIVERY.equals(userOrderInDatabase.getStatus())) {
                res.setResultCode(ResultEnum.ORDER_STATUS_ERROR.getCode());
                res.setResultMsg(ResultEnum.ORDER_STATUS_ERROR.getMsg());
                return res;
            }
            //更新订单状态
            UserOrder entity = new UserOrder();
            entity.setId(req.getId());
            entity.setCompleteTime(date);
            entity.setStatus(ShopkeeperConstant.COMPLETED);
            userOrderMapper.updateComplete(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("UserOrderServiceImpl updateComplete error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 获取用户的有效订单列表
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ListRes<UserOrderVO> queryUserOrderList(UserOrderReq req) throws ShopkeeperException {
        logger.info("invoke UserOrderServiceImpl queryUserOrderList, req:{}", req);
        //参数校验
        if (req.getUserId() == null) {
            logger.error("UserOrderServiceImpl queryUserOrderList missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ListRes<UserOrderVO> res = new ListRes<>();
        try {
            List<UserOrder> list = userOrderMapper.queryUserOrderList(req.getUserId());
            res.setResultList(DozerBeanUtil.mapList(list, UserOrderVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("UserOrderServiceImpl queryUserOrderList error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 根据主键获取订单详情
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ObjectRes<UserOrderVO> getUserOrderById(UserOrderReq req) throws ShopkeeperException {
        logger.info("invoke UserOrderServiceImpl getUserOrderById, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("UserOrderServiceImpl getUserOrderById missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ObjectRes<UserOrderVO> res = new ObjectRes<>();
        try {
            UserOrder entity = DozerBeanUtil.map(req, UserOrder.class);
            UserOrder userOrder = userOrderMapper.getUserOrderById(entity);
            //首先校验该订单是否是自己的订单
            if (userOrder == null || req.getUserId() != null && !req.getUserId().equals(userOrder.getUserId())) {
                res.setResultCode(ResultEnum.ORDER_STATUS_ERROR.getCode());
                res.setResultMsg(ResultEnum.ORDER_STATUS_ERROR.getMsg());
                return res;
            }
            UserOrderVO userOrderVO = DozerBeanUtil.map(userOrder, UserOrderVO.class);
            List<OrderCommodityRelationship> list = orderCommodityRelationshipMapper.queryOrderCommodityRelationshipList(req.getId());
            List<CommodityVO> commodityVOS = new ArrayList<>();
            //查询商品详情，并加入购买数量
            for (OrderCommodityRelationship o : list) {
                Commodity commodity = commodityMapper.getCommodity(o.getCommodityId());
                CommodityVO commodityVO = DozerBeanUtil.map(commodity, CommodityVO.class);
                commodityVO.setCount(o.getCount());
                commodityVOS.add(commodityVO);
            }
            //如果需要配送，还需设置地址详情
            if (userOrder.getAddressId() != null) {
                Address addressEntity = new Address();
                addressEntity.setId(userOrder.getAddressId());
                Address address = addressMapper.getAddress(addressEntity);
                userOrderVO.setAddressVO(DozerBeanUtil.map(address, AddressVO.class));
            }

            userOrderVO.setCommodityList(commodityVOS);
            res.setResultObj(userOrderVO);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("UserOrderServiceImpl getUserOrderById error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 根据订单的状态获取所有订单
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ListRes<UserOrderVO> queryAllOrderListByStatus(UserOrderReq req) throws ShopkeeperException {
        logger.info("invoke UserOrderServiceImpl queryAllOrderListByStatus, req:{}", req);
//        //参数校验
//        if (req.getStatus() == null) {
//            logger.error("UserOrderServiceImpl queryAllOrderListByStatus missing param, req:{}", req);
//            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
//        }
        ListRes<UserOrderVO> res = new ListRes<>();
        try {
            UserOrder entity = DozerBeanUtil.map(req, UserOrder.class);
            List<UserOrder> list = userOrderMapper.queryAllOrderListByStatus(entity);
            res.setResultList(DozerBeanUtil.mapList(list, UserOrderVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("UserOrderServiceImpl queryAllOrderListByStatus error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 更新订单状态（包括发货、收货、取消、删除）
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes updateOrder(UserOrderReq req) throws ShopkeeperException {
        logger.info("invoke UserOrderServiceImpl updateOrder, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("UserOrderServiceImpl updateOrder missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            UserOrder entity = new UserOrder();
            entity.setId(req.getId());
            //根据主键获取该订单在数据库中的情况
            UserOrder userOrderInDatabase = userOrderMapper.getUserOrderById(entity);
            entity = DozerBeanUtil.map(req, UserOrder.class);
            //发货、收货、取消
            if (req.getStatus() != null) {
                if (ShopkeeperConstant.DELIVERY.equals(req.getStatus())) {
                    //订单发货
                    entity.setDeliveryTime(date);
                } else if (ShopkeeperConstant.COMPLETED.equals(req.getStatus())) {
                    if (req.getUserId() == null || !req.getUserId().equals(userOrderInDatabase.getUserId()) ||
                            ShopkeeperConstant.INVALID.equals(userOrderInDatabase.getState())) {
                        //发起请求的用户主键与订单所属的用户不匹配，或是订单已被买家删除，说明是恶意请求
                        res.setResultCode(ResultEnum.ORDER_STATUS_ERROR.getCode());
                        res.setResultMsg(ResultEnum.ORDER_STATUS_ERROR.getMsg());
                        return res;
                    }
                    //确认收货
                    entity.setCompleteTime(date);
                } else if (ShopkeeperConstant.CANCEL.equals(req.getStatus())) {
                    //订单状态不是待发货，或者订单无效，或者订单主键不属于请求发起者，都是恶意请求
                    if (!ShopkeeperConstant.WAITING_DELIVERY.equals(userOrderInDatabase.getStatus()) ||
                            ShopkeeperConstant.INVALID.equals(userOrderInDatabase.getState()) ||
                            req.getUserId() == null ||
                            !req.getUserId().equals(userOrderInDatabase.getUserId())) {
                        res.setResultCode(ResultEnum.ORDER_STATUS_ERROR.getCode());
                        res.setResultMsg(ResultEnum.ORDER_STATUS_ERROR.getMsg());
                        return res;
                    }
                    //取消订单
                    entity.setCancelTime(date);

                } else {
                    //非以上三种状态说明是恶意请求
                    res.setResultCode(ResultEnum.ORDER_STATUS_ERROR.getCode());
                    res.setResultMsg(ResultEnum.ORDER_STATUS_ERROR.getMsg());
                    return res;
                }
            } else if (req.getState() == null || req.getUserId() == null ||
                    !req.getUserId().equals(userOrderInDatabase.getUserId()) ||
                    ShopkeeperConstant.INVALID.equals(userOrderInDatabase.getState())) {
                //发起请求的用户主键与订单所属的用户不匹配，说明是恶意请求
                res.setResultCode(ResultEnum.ORDER_STATUS_ERROR.getCode());
                res.setResultMsg(ResultEnum.ORDER_STATUS_ERROR.getMsg());
                return res;
            }
            userOrderMapper.updateOrder(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("UserOrderServiceImpl updateOrder error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 校验库存
     *
     * @param list
     * @return
     * @throws ShopkeeperException
     */
    private List<Commodity> checkInventory(List<OrderCommodityRelationshipReq> list) throws ShopkeeperException {
        List<Commodity> commodities = new ArrayList<>();
        try {
            for (OrderCommodityRelationshipReq o : list) {
                Commodity commodity = commodityMapper.getCommodity(o.getCommodityId());
                if (commodity.getInventory() < o.getCount()) {
                    return null;
                }
                commodities.add(commodity);
            }
        } catch (Exception e) {
            logger.error("UserOrderServiceImpl checkInventory error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return commodities;
    }

    /**
     * 支付
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    private boolean pay(UserOrderReq req) throws ShopkeeperException {
        Date date = new Date();
        try {
            Bankcard bankcard = bankcardMapper.getBankcardById(req.getBankcardId());
            //余额不足，则直接返回
            if (bankcard == null || bankcard.getBalance() < req.getTotalPrice()) {
                return false;
            } else {
                //扣款
                Bankcard entity = new Bankcard();
                entity.setId(req.getBankcardId());
                entity.setBalance(bankcard.getBalance() - req.getTotalPrice());
                entity.setModifyTime(date);
                bankcardMapper.updateBalance(entity);
                return true;
            }
        } catch (Exception e) {
            logger.error("UserOrderServiceImpl pay error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.PAY_FAIL);
        }
    }

    /**
     * 创建新订单
     *
     * @param req
     * @return 生成的订单主键
     * @throws ShopkeeperException
     */
    private Integer createNewOrder(UserOrderReq req) throws ShopkeeperException {
        Date date = new Date();
        UserOrder entity = DozerBeanUtil.map(req, UserOrder.class);
        entity.setOrderNumber(OrderNumberUtil.getRandomFileName());
        entity.setCreateTime(date);
        entity.setPayTime(date);
        entity.setState(ShopkeeperConstant.VALID);
        if (ShopkeeperConstant.NOT_NEED_DELIVERY.equals(req.getType())) {
            //无需发货，则订单状态为已完成
            entity.setCompleteTime(date);
            entity.setStatus(ShopkeeperConstant.COMPLETED);
        } else {
            //需要发货，订单状态为待发货
            entity.setStatus(ShopkeeperConstant.WAITING_DELIVERY);
        }
        try {
            userOrderMapper.createOrder(entity);
        } catch (Exception e) {
            logger.error("UserOrderServiceImpl createNewOrder error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_INSERT_FAIL);
        }
        return entity.getId();
    }

    /**
     * 创建订单-商品关联关系
     *
     * @param req
     * @throws ShopkeeperException
     */
    private void createOrderCommodityRelationship(UserOrderReq req, List<Commodity> commodities) throws ShopkeeperException {
        List<OrderCommodityRelationship> list = DozerBeanUtil.mapList(req.getCommodityList(), OrderCommodityRelationship.class);
        Integer userOrderId = req.getId();
        try {
            for (OrderCommodityRelationship o : list) {

                o.setOrderId(userOrderId);
            }
            for (int i = 0; i < list.size(); i++) {
                Integer inventory = commodities.get(i).getInventory() - list.get(i).getCount();
                list.get(i).setOrderId(userOrderId);
                //创建订单-商品关联
                orderCommodityRelationshipMapper.createOrderCommodityRelationship(list.get(i));
                //更新商品库存
                commodities.get(i).setInventory(inventory);
                commodityMapper.updateInventory(commodities.get(i));
            }
        } catch (Exception e) {
            logger.error("UserOrderServiceImpl createOrderCommodityRelationship error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_INSERT_FAIL);
        }

    }
}
