package cn.edu.zju.shopkeeper.mapper;

import cn.edu.zju.shopkeeper.domain.CommodityType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午3:37
 * Description 商品类别mapper
 */
@Component("commodityTypeMapper")
public interface CommodityTypeMapper {
    /**
     * 根据类型主键获取类型详情
     *
     * @param id 类型主键
     * @return
     */
    CommodityType getCommodityType(@Param("id") Integer id);

    /**
     * 获取可用的类型列表
     *
     * @return
     */
    List<CommodityType> queryCommodityTypeList();

    /**
     * 创建新的商品类型
     *
     * @param commodityType
     */
    void createCommodityType(CommodityType commodityType);

    /**
     * 根据主键删除某个商品类型（在数据库中设置为禁用）
     *
     * @param commodityType
     */
    void deleteCommodityType(CommodityType commodityType);

    /**
     * 更新某个商品类型的详细信息
     *
     * @param commodityType
     */
    void updateCommodityType(CommodityType commodityType);
}