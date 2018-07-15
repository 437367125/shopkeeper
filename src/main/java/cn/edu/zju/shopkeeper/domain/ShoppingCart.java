package cn.edu.zju.shopkeeper.domain;

import java.util.Date;

/**
 * @version V1.0
 * @author Wang Zejie
 * @date 2018/7/15 上午10:15
 * Description 购物车实体类
 */
public class ShoppingCart {
    /**
     * 主键
     */
    Integer id;
    /**
     * 用户主键
     */
    Integer userId;
    /**
     * 商品主键
     */
    Integer commodityId;
    /**
     * 商品数量
     */
    Integer count;
    /**
     * 此商品状态（0失效，1生效）
     */
    Integer state;
    /**
     * 创建时间
     */
    Date createTime;
    /**
     * 修改时间
     */
    Date modifyTime;

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

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
        return "ShoppingCart{" +
                "id=" + id +
                ", userId=" + userId +
                ", commodityId=" + commodityId +
                ", count=" + count +
                ", state=" + state +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
