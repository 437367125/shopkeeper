package cn.edu.zju.bank.exception;

import cn.edu.zju.bank.enums.ResultEnum;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 下午1:22
 * Description 项目异常类
 */
public class BankcardException extends Exception {

    private static final long serialVersionUID = -1999904905387978839L;
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
    public BankcardException(ResultEnum errEnum) {
        this.errorCode = errEnum.getCode();
        this.message = errEnum.getMsg().toString();
    }

    public BankcardException(ResultEnum errEnum, String errorMes) {
        this.errorCode = errEnum.getCode();
        this.message = new StringBuilder(errorMes).append(errEnum.getMsg()).toString();
    }

    public BankcardException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
