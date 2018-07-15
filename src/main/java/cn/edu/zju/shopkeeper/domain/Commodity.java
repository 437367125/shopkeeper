package cn.edu.zju.shopkeeper.domain;

import java.util.Date;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 上午9:59
 * Description 商品实体类
 */
public class Commodity {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 商品名
     */
    private String commodityName;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 所在位置
     */
    private String location;
    /**
     * 库存数量
     */
    private Integer inventory;
    /**
     * 价格
     */
    private Double price;
    /**
     * 图片地址
     */
    private String picture;
    /**
     * 商品类型（此处存商品类型表的主键）
     */
    private Integer type;
    /**
     * 商品状态（0失效，1生效）
     */
    private Integer state;
    /**
     * 创建者
     */
    private String creater;
    /**
     * 修改者
     */
    private String modifier;
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

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
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
        return "Commodity{" +
                "id=" + id +
                ", commodityName='" + commodityName + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", inventory=" + inventory +
                ", price=" + price +
                ", picture='" + picture + '\'' +
                ", type=" + type +
                ", state=" + state +
                ", creater='" + creater + '\'' +
                ", modifier='" + modifier + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
