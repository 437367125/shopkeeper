package cn.edu.zju.shopkeeper.service.impl;

import cn.edu.zju.shopkeeper.domain.Commodity;
import cn.edu.zju.shopkeeper.domain.CommodityType;
import cn.edu.zju.shopkeeper.domain.req.CommodityTypeReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.CommodityTypeVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.mapper.CommodityTypeMapper;
import cn.edu.zju.shopkeeper.service.CommodityTypeService;
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
 * @date 2018/7/15 下午3:50
 * Description 商品类型服务实现类
 */
@Service
public class CommodityTypeServiceImpl implements CommodityTypeService {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CommodityTypeServiceImpl.class);

    private CommodityTypeMapper commodityTypeMapper;

    @Autowired
    public CommodityTypeServiceImpl(CommodityTypeMapper commodityTypeMapper) {
        this.commodityTypeMapper = commodityTypeMapper;
    }

    /**
     * 根据商品类型主键获取该类型的详情
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ObjectRes<CommodityTypeVO> getCommodityType(CommodityTypeReq req) throws ShopkeeperException {
        logger.info("invoke CommodityTypeServiceImpl getCommodityType, req:{}", req);
        //参数校验
        if (req.getId() == null) {
            logger.error("CommodityTypeServiceImpl getCommodityType missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        ObjectRes<CommodityTypeVO> res = new ObjectRes<>();
        try {
            CommodityType commodityType = commodityTypeMapper.getCommodityType(req.getId());
            res.setResultObj(DozerBeanUtil.map(commodityType, CommodityTypeVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("CommodityTypeServiceImpl getCommodityType error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 获取可用的商品类型列表
     *
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public ListRes<CommodityTypeVO> queryCommodityTypeList() throws ShopkeeperException {
        logger.info("invoke CommodityTypeServiceImpl getCommodityType");
        ListRes<CommodityTypeVO> res = new ListRes<>();
        try {
            List<CommodityType> list = commodityTypeMapper.queryCommodityTypeList();
            res.setResultList(DozerBeanUtil.mapList(list, CommodityTypeVO.class));
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("CommodityTypeServiceImpl queryCommodityTypeList error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_QUERY_FAIL);
        }
        return res;
    }

    /**
     * 创建新的商品类型
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes createCommodityType(CommodityTypeReq req) throws ShopkeeperException {
        logger.info("invoke CommodityTypeServiceImpl createCommodityType, req:{}", req);
        //参数校验
        if (StringUtils.isBlank(req.getTypeName()) || StringUtils.isBlank(req.getDescription()) ||
                StringUtils.isBlank(req.getCreater()) || StringUtils.isBlank(req.getModifier())) {
            logger.error("CommodityTypeServiceImpl createCommodityType missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            CommodityType entity = DozerBeanUtil.map(req, CommodityType.class);
            entity.setState(1);
            entity.setCreateTime(date);
            entity.setModifyTime(date);
            commodityTypeMapper.createCommodityType(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("CommodityTypeServiceImpl createCommodityType error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_INSERT_FAIL);
        }
        return res;
    }

    /**
     * 根据主键删除某个商品类型（在数据库中设置为禁用）
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes deleteCommodityType(CommodityTypeReq req) throws ShopkeeperException {
        logger.info("invoke CommodityTypeServiceImpl deleteCommodityType, req:{}", req);
        //参数校验
        if (req.getId() == null || StringUtils.isBlank(req.getModifier())) {
            logger.error("CommodityTypeServiceImpl deleteCommodityType missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            CommodityType entity = DozerBeanUtil.map(req, CommodityType.class);
            entity.setModifyTime(date);
            commodityTypeMapper.deleteCommodityType(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("CommodityTypeServiceImpl deleteCommodityType error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }

    /**
     * 更新某个商品类型的详情
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    @Override
    public BaseRes updateCommodityType(CommodityTypeReq req) throws ShopkeeperException {
        logger.info("invoke CommodityTypeServiceImpl updateCommodityType, req:{}", req);
        //参数校验
        if (req.getId() == null || StringUtils.isBlank(req.getModifier()) ||
                StringUtils.isBlank(req.getTypeName()) || StringUtils.isBlank(req.getDescription())) {
            logger.error("CommodityTypeServiceImpl updateCommodityType missing param, req:{}", req);
            throw new ShopkeeperException(ResultEnum.MISSING_PARAM);
        }
        BaseRes res = new BaseRes();
        Date date = new Date();
        try {
            CommodityType entity = DozerBeanUtil.map(req, CommodityType.class);
            entity.setModifyTime(date);
            commodityTypeMapper.updateCommodityType(entity);
            res.setResultCode(ResultEnum.SUCCESS.getCode());
            res.setResultMsg(ResultEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("CommodityTypeServiceImpl updateCommodityType error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.DATA_UPDATE_FAIL);
        }
        return res;
    }
}
