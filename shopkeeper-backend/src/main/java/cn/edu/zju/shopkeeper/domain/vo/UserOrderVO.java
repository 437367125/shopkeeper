package cn.edu.zju.shopkeeper.domain.vo;

import cn.edu.zju.shopkeeper.domain.Address;

import java.util.Date;
import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午11:16
 * Description 用户订单VO
 */
public class UserOrderVO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户手机
     */
    private Long phoneNumber;
    /**
     * 用户昵称
     */
    private String nickname;
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
     * 用于付款的银行主键
     */
    private Integer bankcardId;
    /**
     * 银行卡号
     */
    private Long bankcardNumber;
    /**
     * 订单类别（0为无需配送，1为需要配送）
     */
    private Integer type;
    /**
     * 配送地址（无需配送则为空）
     */
    private Integer addressId;
    /**
     * 订单所处状态（0为待付款，1为已付款，2为待发货，3为已发货，4为已取消，5为已完成）
     */
    private Integer status;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 付款时间
     */
    private Date payTime;
    /**
     * 发货时间（无需配送则为空）
     */
    private Date deliveryTime;
    /**
     * 完成时间（无需配送则这个时间与付款时间相同）
     */
    private Date completeTime;
    /**
     * 取消时间
     */
    private Date cancelTime;
    /**
     * 订单商品列表
     */
    private List<CommodityVO> commodityList;
    /**
     * 配送地址
     */
    private AddressVO addressVO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getBankcardId() {
        return bankcardId;
    }

    public void setBankcardId(Integer bankcardId) {
        this.bankcardId = bankcardId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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

    public List<CommodityVO> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<CommodityVO> commodityList) {
        this.commodityList = commodityList;
    }

    public AddressVO getAddressVO() {
        return addressVO;
    }

    public void setAddressVO(AddressVO addressVO) {
        this.addressVO = addressVO;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getBankcardNumber() {
        return bankcardNumber;
    }

    public void setBankcardNumber(Long bankcardNumber) {
        this.bankcardNumber = bankcardNumber;
    }

    @Override
    public String toString() {
        return "UserOrderVO{" +
                "id=" + id +
                ", phoneNumber=" + phoneNumber +
                ", nickname='" + nickname + '\'' +
                ", orderNumber=" + orderNumber +
                ", totalNum=" + totalNum +
                ", totalPrice=" + totalPrice +
                ", bankcardId=" + bankcardId +
                ", bankcardNumber=" + bankcardNumber +
                ", type=" + type +
                ", addressId=" + addressId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", deliveryTime=" + deliveryTime +
                ", completeTime=" + completeTime +
                ", cancelTime=" + cancelTime +
                ", commodityList=" + commodityList +
                ", addressVO=" + addressVO +
                '}';
    }
}
