package cn.edu.zju.bank.domain.res;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/15 上午11:19
 * Description 返回结果基类
 */
public class BaseRes {
    /**
     * 返回代码
     */
    private String resultCode;
    /**
     * 返回信息
     */
    private String resultMsg;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
