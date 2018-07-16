package cn.edu.zju.shopkeeper.service.impl;

import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.Commodity;
import cn.edu.zju.shopkeeper.domain.req.CommodityReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.CommodityVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.mapper.CommodityMapper;
import cn.edu.zju.shopkeeper.service.CommodityService;
import cn.edu.zju.shopkeeper.utils.DozerBeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午7:43
 * Description 商品服务实现类
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CommodityServiceImpl.class);

    private CommodityMapper commodityMapper;

    @Autowired
    public CommodityServiceImpl(CommodityMapper commodityMapper) {
        this.commodityMapper = commodityMapper;
    }

    /**
     * 根据商品id获取单个商品信息
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ObjectRes<CommodityVO> getCommodity(CommodityReq req) throws ShopkeeperException {
        logger.info("invoke CommodityServiceImpl getCommodity, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("CommodityServiceImpl getCommodity missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ObjectRes<CommodityVO> res = new ObjectRes<>();
        try {
            Commodity commodity = commodityMapper.getCommodity(req.getId());
            res.setResultObj(DozerBeanUtil.map(commodity, CommodityVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("CommodityServiceImpl getCommodity error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 根据商品类别获取三个商品（用于推荐）
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ListRes<CommodityVO> queryCommodityListByType(CommodityReq req) throws ShopkeeperException {
        logger.info("invoke CommodityServiceImpl queryCommodityListByType, req:{}", req);
        //参数校验
        if (req.getType() == null || req.getId() == null) {
            logger.error("CommodityServiceImpl queryCommodityListByType missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ListRes<CommodityVO> res = new ListRes<>();
        try {
            Commodity entity = DozerBeanUtil.map(req, Commodity.class);
            List<Commodity> list = commodityMapper.queryCommodityListByType(entity);
            res.setResultList(DozerBeanUtil.mapList(list, CommodityVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("CommodityServiceImpl queryCommodityListByType error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 创建新商品
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes createCommodity(CommodityReq req) throws ShopkeeperException {
        logger.info("invoke CommodityServiceImpl createCommodity, req:{}", req);
        //参数校验
        if (StringUtils.isBlank(req.getCommodityName()) || StringUtils.isBlank(req.getDescription()) ||
                StringUtils.isBlank(req.getLocation()) || StringUtils.isBlank(req.getPicture()) ||
                StringUtils.isBlank(req.getCreater()) || StringUtils.isBlank(req.getModifier()) ||
                req.getType() == null || req.getInventory() == null ||
                req.getPrice() == null) {
            logger.error("CommodityServiceImpl createCommodity missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            Commodity entity = DozerBeanUtil.map(req, Commodity.class);
            entity.setCreateTime(date);
            entity.setModifyTime(date);
            entity.setState(ShopkeeperConstant.VALID);
            commodityMapper.createCommodity(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("CommodityServiceImpl createCommodity error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_INSERT_FAIL);
        }
        return res;
    }

    /**
     * 删除商品（设置为无效）
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes deleteCommodity(CommodityReq req) throws ShopkeeperException {
        logger.info("invoke CommodityServiceImpl deleteCommodity, req:{}", req);
        //参数校验
        if (req.getId() == null || StringUtils.isBlank(req.getModifier())) {
            logger.error("CommodityServiceImpl deleteCommodity missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            Commodity entity = DozerBeanUtil.map(req, Commodity.class);
            entity.setModifyTime(date);
            commodityMapper.deleteCommodity(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("CommodityServiceImpl deleteCommodity error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 更新商品详情
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes updateCommodity(CommodityReq req) throws ShopkeeperException {
        logger.info("invoke CommodityServiceImpl updateCommodity, req:{}", req);
        //参数校验
        if (StringUtils.isBlank(req.getCommodityName()) || StringUtils.isBlank(req.getDescription()) ||
                StringUtils.isBlank(req.getLocation()) || StringUtils.isBlank(req.getPicture()) ||
                StringUtils.isBlank(req.getModifier()) || req.getType() == null ||
                req.getInventory() == null || req.getId() == null ||
                req.getPrice() == null) {
            logger.error("CommodityServiceImpl updateCommodity missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            Commodity entity = DozerBeanUtil.map(req, Commodity.class);
            entity.setModifyTime(date);
            commodityMapper.updateCommodity(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("CommodityServiceImpl updateCommodity error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }
}
