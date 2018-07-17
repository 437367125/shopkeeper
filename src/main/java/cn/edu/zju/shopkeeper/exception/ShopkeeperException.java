package cn.edu.zju.shopkeeper.exception;

import cn.edu.zju.shopkeeper.enums.ResultEnum;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午1:22
 * Description 项目异常类
 */
public class ShopkeeperException extends Exception {

    private static final long serialVersionUID = 1427588023095270067L;

    /**
     * 异常编码
     */
    private String errorCode;
    /**
     * 异常信息
     */
    private String message;


    /**
     * 服务异常构造函数.
     * @param errEnum 异常枚举类型
     *
     */
    public ShopkeeperException(ResultEnum errEnum) {
        this.errorCode = errEnum.getCode();
        this.message = errEnum.getMsg().toString();
    }

    public ShopkeeperException(ResultEnum errEnum, String errorMes) {
        this.errorCode = errEnum.getCode();
        this.message = new StringBuilder(errorMes).append(errEnum.getMsg()).toString();
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
