package cn.edu.zju.bank.domain.req;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午11:49
 * Description 银行卡请求类
 */
public class BankcardReq {
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
     * 银行卡密码
     */
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "BankcardReq{" +
                "id=" + id +
                ", userId=" + userId +
                ", bankcardNumber=" + bankcardNumber +
                ", password='" + password + '\'' +
                '}';
    }
}
