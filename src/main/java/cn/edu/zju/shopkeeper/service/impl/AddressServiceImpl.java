package cn.edu.zju.shopkeeper.service.impl;

import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.Address;
import cn.edu.zju.shopkeeper.domain.req.AddressReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.AddressVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.mapper.AddressMapper;
import cn.edu.zju.shopkeeper.service.AddressService;
import cn.edu.zju.shopkeeper.utils.DozerBeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 上午10:56
 * Description 地址服务实现类
 */
@Service
public class AddressServiceImpl implements AddressService {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    private AddressMapper addressMapper;

    @Autowired
    public AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    /**
     * 查询地址列表
     *
     * @param req 地址请求
     * @return 地址列表
     * @throws ShopkeeperException
     */
    @Override
    public ListRes<AddressVO> queryAddressList(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl queryAddressList, req:{}", req);
        //参数校验
        if (req.getUserId() == null) {
            logger.error("AddressServiceImpl queryAddressList missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ListRes<AddressVO> res = new ListRes<>();
        try {
            List<Address> list = addressMapper.queryAddressList(req.getUserId());
            res.setResultList(DozerBeanUtil.mapList(list, AddressVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl queryAddressList error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 创建新地址
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes createAddress(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl createAddress, req:{}", req);
        //参数校验
        if (req.getUserId() == null || StringUtils.isBlank(req.getAddressDescription()) ||
                req.getPhoneNumber() == null) {
            logger.error("AddressServiceImpl createAddress missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            Address entity = DozerBeanUtil.map(req, Address.class);
            entity.setState(ShopkeeperConstant.VALID);
            entity.setCreateTime(date);
            entity.setModifyTime(date);
            List<Address> curAddressList = addressMapper.queryAddressList(req.getUserId());
            if (CollectionUtils.isEmpty(curAddressList)) {
                entity.setType(ShopkeeperConstant.DEFAULT);
            } else {
                entity.setType(ShopkeeperConstant.NOT_DEFAULT);
            }
            addressMapper.createAddress(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl createAddress error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_INSERT_FAIL);
        }
        return res;
    }

    /**
     * 删除地址（实际是设置地址为无效，数据库保留数据）
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseRes deleteAddress(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl deleteAddress, req:{}", req);
        //参数校验
        if (req.getId() == null || req.getUserId() == null) {
            logger.error("AddressServiceImpl deleteAddress missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            //先删除这个地址
            Address entity = DozerBeanUtil.map(req, Address.class);
            entity.setModifyTime(date);
            addressMapper.deleteAddress(entity);
            //获取当前地址列表，如果列表不为空且没有默认地址，则设置一个默认地址
            List<Address> list = addressMapper.queryAddressList(req.getUserId());
            if (CollectionUtils.isNotEmpty(list)) {
                boolean flag = false;
                for (Address address : list) {
                    if (ShopkeeperConstant.DEFAULT.equals(address.getType())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    entity = new Address();
                    entity.setId(list.get(0).getId());
                    entity.setModifyTime(date);
                    entity.setUserId(req.getUserId());
                    addressMapper.setDefaultAddress(entity);
                }
            }
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl deleteAddress error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 更新默认地址
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseRes updateDefaultAddress(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl updateDefaultAddress, req:{}", req);
        //参数校验
        if (req.getId() == null || req.getUserId() == null) {
            logger.error("AddressServiceImpl updateDefaultAddress missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            //首先清除默认地址
            Address entity = new Address();
            entity.setUserId(req.getUserId());
            entity.setModifyTime(date);
            addressMapper.clearDefaultAddress(entity);
            //接着设置新的默认地址
            entity = DozerBeanUtil.map(req, Address.class);
            entity.setModifyTime(date);
            addressMapper.setDefaultAddress(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl updateDefaultAddress error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 更新地址详情
     *
     * @param req 地址请求
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes updateAddress(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl updateAddress, req:{}", req);
        //参数校验
        if (req.getId() == null || req.getPhoneNumber() == null ||
                StringUtils.isBlank(req.getAddressDescription()) || req.getUserId() == null) {
            logger.error("AddressServiceImpl updateAddress missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            Address entity = DozerBeanUtil.map(req, Address.class);
            entity.setModifyTime(date);
            addressMapper.updateAddress(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl updateAddress error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 根据地址主键查询地址详情
     *
     * @param req 地址请求
     * @return 地址详情
     * @throws ShopkeeperException
     */
    @Override
    public ObjectRes<AddressVO> getAddress(AddressReq req) throws ShopkeeperException {
        logger.info("invoke AddressServiceImpl getAddress, req:{}", req);
        //参数校验
        if (req.getId() == null || req.getUserId() == null) {
            logger.error("AddressServiceImpl getAddress missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ObjectRes<AddressVO> res = new ObjectRes<>();
        try {
            Address address = addressMapper.getAddress(DozerBeanUtil.map(req, Address.class));
            if (address != null) {
                res.setResultObj(DozerBeanUtil.map(address, AddressVO.class));
            }
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("AddressServiceImpl getAddress error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }
}
