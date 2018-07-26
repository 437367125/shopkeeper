package cn.edu.zju.bank.domain.vo;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午11:50
 * Description 银行卡VO
 */
public class BankcardVO {
    /**
     * 主键
     */
    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBankcardNumber() {
        return bankcardNumber;
    }

    public void setBankcardNumber(Long bankcardNumber) {
        this.bankcardNumber = bankcardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankcardVO{" +
                "id=" + id +
                ", bankcardNumber=" + bankcardNumber +
                ", bankName='" + bankName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
