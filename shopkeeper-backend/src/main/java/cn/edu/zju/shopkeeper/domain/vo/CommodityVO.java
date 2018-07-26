package cn.edu.zju.shopkeeper.domain.vo;

import java.util.Date;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午7:48
 * Description 商品VO
 */
public class CommodityVO {
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
     * 修改者
     */
    private String modifier;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 购买数量
     */
    private Integer count;

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

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CommodityVO{" +
                "id=" + id +
                ", commodityName='" + commodityName + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", inventory=" + inventory +
                ", price=" + price +
                ", picture='" + picture + '\'' +
                ", type=" + type +
                ", modifier='" + modifier + '\'' +
                ", modifyTime=" + modifyTime +
                ", count=" + count +
                '}';
    }
}
