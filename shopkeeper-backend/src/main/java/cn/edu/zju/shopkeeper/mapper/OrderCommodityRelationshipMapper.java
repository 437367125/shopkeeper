package cn.edu.zju.shopkeeper.mapper;

import cn.edu.zju.shopkeeper.domain.OrderCommodityRelationship;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 下午3:10
 * Description 订单-商品关系mapper
 */
@Component("orderCommodityRelationshipMapper")
public interface OrderCommodityRelationshipMapper {
    /**
     * 创建订单-商品关联关系
     *
     * @param orderCommodityRelationship
     */
    void createOrderCommodityRelationship(OrderCommodityRelationship orderCommodityRelationship);

    /**
     * 根据订单主键获取关联的商品列表
     *
     * @param orderId
     * @return
     */
    List<OrderCommodityRelationship> queryOrderCommodityRelationshipList(@Param("orderId") Integer orderId);
}
