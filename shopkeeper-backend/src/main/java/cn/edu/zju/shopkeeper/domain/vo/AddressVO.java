package cn.edu.zju.shopkeeper.domain.vo;

import java.util.Date;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午3:03
 * Description 地址VO
 */
public class AddressVO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 地址内容
     */
    private String addressDescription;
    /**
     * 联系电话
     */
    private Long phoneNumber;
    /**
     * 地址类型（0非默认地址，1默认地址）
     */
    private Integer type;
    /**
     * 最后操作时间
     */
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "AddressVO{" +
                "id=" + id +
                ", addressDescription='" + addressDescription + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", type=" + type +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
