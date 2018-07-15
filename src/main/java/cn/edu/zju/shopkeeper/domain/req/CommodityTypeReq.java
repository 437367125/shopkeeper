package cn.edu.zju.shopkeeper.domain.req;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午3:33
 * Description 商品类别请求类
 */
public class CommodityTypeReq {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 类型名
     */
    private String typeName;
    /**
     * 类型描述
     */
    private String description;
    /**
     * 创建者
     */
    private String creater;
    /**
     * 修改者
     */
    private String modifier;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "CommodityTypeReq{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", description='" + description + '\'' +
                ", creater='" + creater + '\'' +
                ", modifier='" + modifier + '\'' +
                '}';
    }
}
