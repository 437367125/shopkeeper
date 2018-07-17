package cn.edu.zju.shopkeeper.mapper;

import cn.edu.zju.shopkeeper.domain.Commodity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午3:26
 * Description 商品mapper
 */
@Component("commodityMapper")
public interface CommodityMapper {
    /**
     * 根据商品主键获取商品详情
     *
     * @param id 商品主键
     * @return 商品详情
     */
    Commodity getCommodity(@Param("id") Integer id);

    /**
     * 根据商品类别获取3个商品（用于推荐）
     *
     * @param commodity
     * @return
     */
    List<Commodity> queryCommodityListByType(Commodity commodity);

    /**
     * 创建新商品
     *
     * @param commodity
     */
    void createCommodity(Commodity commodity);

    /**
     * 删除商品（设置为禁用）
     *
     * @param commodity
     */
    void deleteCommodity(Commodity commodity);

    /**
     * 更新商品详情
     *
     * @param commodity
     */
    void updateCommodity(Commodity commodity);

    /**
     * 更新商品库存
     *
     * @param commodity
     */
    void updateInventory(Commodity commodity);

    /**
     * 根据商品名模糊查询商品
     *
     * @param commodity
     * @return
     */
    List<Commodity> queryCommodityList(Commodity commodity);
}
