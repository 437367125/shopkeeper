package cn.edu.zju.shopkeeper.domain;

/**
 * @version V1.0
 * @author Wang Zejie
 * @date 2018/7/15 上午10:13
 * Description 订单-商品关系实体类
 */
public class OrderCommodityRelationship {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 订单主键
     */
    private Integer orderId;
    /**
     * 商品主键
     */
    private Integer commodityId;
    /**
     * 商品数量
     */
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderCommodityRelationship{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", commodityId=" + commodityId +
                ", count=" + count +
                '}';
    }
}
