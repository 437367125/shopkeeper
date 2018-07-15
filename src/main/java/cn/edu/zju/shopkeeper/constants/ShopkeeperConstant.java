package cn.edu.zju.shopkeeper.constants;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 上午10:47
 * Description 常量
 */
public class ShopkeeperConstant {
    /**
     * 订单状态：待付款
     */
    public static final Integer WAITING_PAY = 0;
    /**
     * 订单状态：已付款
     */
    public static final Integer PAYING = 1;
    /**
     * 订单状态：待发货
     */
    public static final Integer WAITING_DELIVERY = 2;
    /**
     * 订单状态：已发货
     */
    public static final Integer DELIVERY = 3;
    /**
     * 订单状态：已取消
     */
    public static final Integer CANCEL = 4;
    /**
     * 订单状态：已完成
     */
    public static final Integer COMPLETED = 5;
    /**
     * 状态0：失效
     */
    public static final Integer INVALID = 0;
    /**
     * 状态1：生效
     */
    public static final Integer VALID = 1;
    /**
     * 地址类型：非默认
     */
    public static final Integer NOT_DEFAULT = 0;
    /**
     * 地质类型：默认
     */
    public static final Integer DEFAULT = 1;
}
