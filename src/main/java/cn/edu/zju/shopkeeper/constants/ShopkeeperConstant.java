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
     * 地址类型：默认
     */
    public static final Integer DEFAULT = 1;
    /**
     * 登录方式：用户名登录
     */
    public static final Integer USERNAME_LOGIN = 0;
    /**
     * 登录方式：手机号登录
     */
    public static final Integer PHONE_LOGIN = 1;
    /**
     * 用户类型：卖家
     */
    public static final Integer SELLER = 0;
    /**
     * 用户类型：买家
     */
    public static final Integer BUYER = 1;
    /**
     * 订单类别：无需配送
     */
    public static final Integer NOT_NEED_DELIVERY = 0;
    /**
     * 订单类别：需要配送
     */
    public static final Integer NEED_DELIVERY = 1;
    /**
     * 返回信息
     */
    public static final String RESULT_MSG = "resultMsg";
    /**
     * 返回编码
     */
    public static final String RESULT_CODE = "resultCode";
    /**
     * token
     */
    public static final String TOKEN = "token";
    /**
     * 地址列表
     */
    public static final String ADDRESS_LIST = "addressList";
    /**
     * 地址详情
     */
    public static final String ADDRESS_INFO = "addressInfo";
    /**
     * 银行卡列表
     */
    public static final String BANKCARD_LIST = "bankcardList";
    /**
     * 商品主键
     */
    public static final String COMMODITY_ID = "commodityId";
    /**
     * 商品列表
     */
    public static final String COMMODITY_LIST = "commodityList";
    /**
     * 商品详情
     */
    public static final String COMMODITY_INFO = "commodityInfo";
    /**
     * 商品类别列表
     */
    public static final String COMMODITY_TYPE_LIST = "commodityTypeList";
    /**
     * 商品类别详情
     */
    public static final String COMMODITY_TYPE_INFO = "commodityTypeInfo";
}
