package cn.edu.zju.shopkeeper.domain.req;

import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午11:01
 * Description 用户订单请求类
 */
public class UserOrderReq {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户主键
     */
    private Integer userId;
    /**
     * 订单号
     */
    private Long orderNumber;
    /**
     * 商品总数
     */
    private Integer totalNum;
    /**
     * 订单总额
     */
    private Double totalPrice;
    /**
     * 用于付款的银行卡主键
     */
    private Integer bankcardId;
    /**
     * 订单类别（0为无需配送，1为需要配送）
     */
    private Integer type;
    /**
     * 配送地址
     */
    private Integer addressId;
    /**
     * 订单所处状态（0为待付款，1为已付款，2为待发货，3为已发货，4为已取消，5为已完成）
     */
    private Integer status;
    /**
     * 订单的商品列表
     */
    private List<OrderCommodityRelationshipReq> commodityList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getBankcardId() {
        return bankcardId;
    }

    public void setBankcardId(Integer bankcardId) {
        this.bankcardId = bankcardId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OrderCommodityRelationshipReq> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<OrderCommodityRelationshipReq> commodityList) {
        this.commodityList = commodityList;
    }

    @Override
    public String toString() {
        return "UserOrderReq{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderNumber=" + orderNumber +
                ", totalNum=" + totalNum +
                ", totalPrice=" + totalPrice +
                ", bankcardId=" + bankcardId +
                ", type=" + type +
                ", addressId=" + addressId +
                ", status=" + status +
                ", commodityList=" + commodityList +
                '}';
    }
}
