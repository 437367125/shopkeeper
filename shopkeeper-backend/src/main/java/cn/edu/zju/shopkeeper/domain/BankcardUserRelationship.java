package cn.edu.zju.shopkeeper.domain;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/21 下午3:31
 * Description 银行卡-用户关系实体类
 */
public class BankcardUserRelationship {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户主键
     */
    private Integer userId;
    /**
     * 银行卡主键
     */
    private Integer bankcardId;
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

    public Integer getBankcardId() {
        return bankcardId;
    }

    public void setBankcardId(Integer bankcardId) {
        this.bankcardId = bankcardId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BankcardUserRelationship{" +
                "id=" + id +
                ", userId=" + userId +
                ", bankcardId=" + bankcardId +
                ", state=" + state +
                '}';
    }
}
