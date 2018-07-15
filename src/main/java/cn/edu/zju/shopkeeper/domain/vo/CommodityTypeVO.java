package cn.edu.zju.shopkeeper.domain.vo;

import java.util.Date;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午3:51
 * Description 商品类型VO
 */
public class CommodityTypeVO {
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
     * 修改者
     */
    private String modifier;
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

    @Override
    public String toString() {
        return "CommodityTypeVO{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", description='" + description + '\'' +
                ", modifier='" + modifier + '\'' +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
