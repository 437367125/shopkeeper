package cn.edu.zju.shopkeeper.service;

import cn.edu.zju.shopkeeper.domain.CommodityType;
import cn.edu.zju.shopkeeper.domain.req.CommodityReq;
import cn.edu.zju.shopkeeper.domain.req.CommodityTypeReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.CommodityTypeVO;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午3:50
 * Description 商品类型服务接口
 */
public interface CommodityTypeService {
    /**
     * 根据商品类型主键获取该类型的详情
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    ObjectRes<CommodityTypeVO> getCommodityType(CommodityTypeReq req) throws ShopkeeperException;

    /**
     * 获取可用的商品类型列表
     *
     * @return
     * @throws ShopkeeperException
     */
    ListRes<CommodityTypeVO> queryCommodityTypeList() throws ShopkeeperException;

    /**
     * 创建新的商品类型
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes createCommodityType(CommodityTypeReq req) throws ShopkeeperException;

    /**
     * 根据主键删除某个商品类型（在数据库中设置为禁用）
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes deleteCommodityType(CommodityTypeReq req) throws ShopkeeperException;

    /**
     * 更新某个商品类型的详情
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes updateCommodityType(CommodityTypeReq req) throws ShopkeeperException;
}
