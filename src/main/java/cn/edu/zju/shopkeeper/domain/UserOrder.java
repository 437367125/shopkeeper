package cn.edu.zju.shopkeeper.domain;

import java.util.Date;

/**
 * @version V1.0
 * @author Wang Zejie
 * @date 2018/7/15 上午10:20
 * Description 订单实体类
 */
public class UserOrder {
    /**
     * 主键
     */
    Integer id;
    /**
     * 用户主键
     */
    Integer userId;
    /**
     * 订单号
     */
    Long orderNumber;
    /**
     * 商品总数
     */
    Integer totalNum;
    /**
     * 订单总额
     */
    Double totalPrice;
    /**
     * 订单类别（0为无需配送，1为需要配送）
     */
    Integer type;
    /**
     * 状态（是否被用户删除，0失效，1生效）
     */
    Integer state;
    /**
     * 订单所处状态（0为待付款，1为已付款，2为待发货，3为已发货，4为已取消，5为已完成）
     */
    Integer status;
    /**
     * 订单创建时间
     */
    Date createTime;
    /**
     * 付款时间
     */
    Date payTime;
    /**
     * 发货时间（无需配送则为空）
     */
    Date deliveryTime;
    /**
     * 完成时间（无需配送则这个时间与付款时间相同）
     */
    Date completeTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderNumber=" + orderNumber +
                ", totalNum=" + totalNum +
                ", totalPrice=" + totalPrice +
                ", type=" + type +
                ", state=" + state +
                ", status=" + status +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", deliveryTime=" + deliveryTime +
                ", completeTime=" + completeTime +
                '}';
    }
}
