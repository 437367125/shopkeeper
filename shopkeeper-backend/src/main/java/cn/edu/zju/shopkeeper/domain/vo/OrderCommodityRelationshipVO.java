package cn.edu.zju.shopkeeper.domain.vo;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/16 上午11:19
 * Description 订单-商品VO
 */
public class OrderCommodityRelationshipVO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 商品名
     */
    private String commodityName;
    /**
     * 所在位置
     */
    private String location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderCommodityRelationshipVO{" +
                "id=" + id +
                ", commodityName='" + commodityName + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", picture='" + picture + '\'' +
                ", type=" + type +
                ", count=" + count +
                '}';
    }
}
