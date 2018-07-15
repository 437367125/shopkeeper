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
}
