package cn.edu.zju.shopkeeper.config;

import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 上午10:07
 * Description 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(ShopkeeperException.class)
    public Object handleException(ShopkeeperException e) {
        logger.error("GlobalExceptionHandler catch exception:{}", ExceptionUtils.getStackTrace(e));
        String code = e.getErrorCode();
        String msg = e.getMessage();
        if (StringUtils.isBlank(code) || StringUtils.isBlank(msg)) {
            msg = ResultEnum.SYSTEM_ERROR.getMsg();
            code = ResultEnum.SYSTEM_ERROR.getCode();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, code);
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, msg);
        return jsonObject;
    }
}
