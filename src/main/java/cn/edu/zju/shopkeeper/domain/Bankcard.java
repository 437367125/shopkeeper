package cn.edu.zju.shopkeeper.domain;

import java.util.Date;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午11:36
 * Description 银行卡实体类
 */
public class Bankcard {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户主键
     */
    private Integer userId;
    /**
     * 银行卡号
     */
    private Long bankcardNumber;
    /**
     * 银行名
     */
    private String bankName;
    /**
     * 余额
     */
    private Double balance;
    /**
     * 状态（0失效，1生效）
     */
    private Integer state;
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

    public Long getBankcardNumber() {
        return bankcardNumber;
    }

    public void setBankcardNumber(Long bankcardNumber) {
        this.bankcardNumber = bankcardNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Bankcard{" +
                "id=" + id +
                ", userId=" + userId +
                ", bankcardNumber=" + bankcardNumber +
                ", bankName='" + bankName + '\'' +
                ", balance=" + balance +
                ", state=" + state +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
