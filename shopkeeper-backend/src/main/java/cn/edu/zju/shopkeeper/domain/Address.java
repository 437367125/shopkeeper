package cn.edu.zju.shopkeeper.domain;

import java.util.Date;

/**
 * @version V1.0
 * @author Wang Zejie
 * @date 2018/7/15 上午9:53
 * Description 地址实体类
 */
public class Address {
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
     * 状态（0失效，1生效）
     */
    private Integer state;
    /**
     * 地址类型（0非默认地址，1默认地址）
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", userId=" + userId +
                ", addressDescription='" + addressDescription + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", state=" + state +
                ", type=" + type +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
