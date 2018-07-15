package cn.edu.zju.shopkeeper.domain;

/**
 * @version V1.0
 * @author Wang Zejie
 * @date 2018/7/15 上午10:12
 * Description 订单-地址关系实体类
 */
public class OrderAddressRelationship {
    /**
     * 主键
     */
    Integer id;
    /**
     * 订单主键
     */
    Integer orderId;
    /**
     * 地址主键
     */
    Integer addressId;

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

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "OrderAddressRelationship{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", addressId=" + addressId +
                '}';
    }
}
