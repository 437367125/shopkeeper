package cn.edu.zju.shopkeeper.domain.req;

import java.io.Serializable;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午11:12
 * Description 订单-商品关系请求类
 */
public class OrderCommodityRelationshipReq implements Serializable {

    private static final long serialVersionUID = 6511941326671638725L;
    /**
     * 商品主键
     */
    private Integer commodityId;
    /**
     * 商品数量
     */
    private Integer count;

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
                ", commodityId=" + commodityId +
                ", count=" + count +
                '}';
    }
}
