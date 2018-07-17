package cn.edu.zju.shopkeeper.controller;

import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 下午12:58
 * Description 控制器基类
 */
public class BaseController {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(BaseController.class);

    public Integer getUser(String token) throws ShopkeeperException {

        if (token == null) {
            throw new ShopkeeperException(ResultEnum.TOKEN_NOT_EXIST);
        }
        Integer userId;
        try {
            // 获取 token 中的 user id
            userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));
        } catch (JWTDecodeException e) {
            logger.error("BaseController getUser error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.TOKEN_INVALID);
        }
        return userId;
    }
}
