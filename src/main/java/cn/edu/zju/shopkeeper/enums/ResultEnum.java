package cn.edu.zju.shopkeeper.enums;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午1:24
 * Description 结果枚举类
 */
public enum ResultEnum {
    SUCCESS("0000", "操作成功"),
    MISSING_PARAM("0001", "参数缺失"),
    DATA_QUERY_FAIL("0002", "数据查询失败"),
    DATA_INSERT_FAIL("0003", "数据插入失败"),
    DATA_UPDATE_FAIL("0004", "数据更新失败"),
    DATA_DELETE_FAIL("0005", "数据删除失败"),
    USERNAME_EXIST("0006", "用户名已存在"),
    PHONE_NUMBER_EXIST("0007", "该手机已被使用"),
    EMAIL_EXIST("0008", "该邮箱已被使用"),
    PASSWORD_ERROR("0009", "密码错误"),
    PAY_FAIL("0010", "支付失败"),
    INSUFFICIENT_BALANCE("0011", "该银行卡余额不足"),
    INVENTORY_SHORTAGE("0012", "该商品库存不足"),
    SYSTEM_ERROR("9999", "系统异常");
    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
