package cn.edu.zju.shopkeeper.service;

import cn.edu.zju.shopkeeper.domain.req.CommodityReq;
import cn.edu.zju.shopkeeper.domain.res.BaseRes;
import cn.edu.zju.shopkeeper.domain.res.ListRes;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.CommodityVO;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午7:42
 * Description 商品服务接口
 */
public interface CommodityService {
    /**
     * 根据商品id获取单个商品信息
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    ObjectRes<CommodityVO> getCommodity(CommodityReq req) throws ShopkeeperException;

    /**
     * 根据商品类别获取三个商品（用于推荐）
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    ListRes<CommodityVO> queryCommodityListByType(CommodityReq req) throws ShopkeeperException;

    /**
     * 创建新商品
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    ObjectRes<Integer> createCommodity(CommodityReq req) throws ShopkeeperException;

    /**
     * 删除商品（设置为无效）
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes deleteCommodity(CommodityReq req) throws ShopkeeperException;

    /**
     * 更新商品详情
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    BaseRes updateCommodity(CommodityReq req) throws ShopkeeperException;

    /**
     * 根据商品名模糊查询商品列表
     *
     * @param req
     * @return
     * @throws ShopkeeperException
     */
    ListRes<CommodityVO> queryCommodityList(CommodityReq req) throws ShopkeeperException;
}
