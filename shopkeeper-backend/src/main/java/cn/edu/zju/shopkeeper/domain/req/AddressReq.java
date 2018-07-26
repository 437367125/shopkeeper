package cn.edu.zju.shopkeeper.domain.req;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 上午11:50
 * Description 地址请求类
 */
public class AddressReq {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户主键
     */
    private Integer userId;
    /**
     * 地址内容
     */
    private String addressDescription;
    /**
     * 联系电话
     */
    private Long phoneNumber;
    /**
     * 状态（0无效，1有效）
     */
    private Integer state;

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

    public String getAddressDescription() {
        return addressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AddressReq{" +
                "id=" + id +
                ", userId=" + userId +
                ", addressDescription='" + addressDescription + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", state=" + state +
                '}';
    }
}
