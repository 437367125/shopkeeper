package cn.edu.zju.shopkeeper.domain.req;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午11:12
 * Description 订单-商品关系请求类
 */
public class OrderCommodityRelationshipReq {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 商品列表
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
                ", commodityId=" + commodityId +
                ", count=" + count +
                '}';
    }
}
