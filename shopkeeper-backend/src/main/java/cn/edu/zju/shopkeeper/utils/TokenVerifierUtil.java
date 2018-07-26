package cn.edu.zju.shopkeeper.utils;

import cn.edu.zju.shopkeeper.domain.req.UserReq;
import cn.edu.zju.shopkeeper.domain.res.ObjectRes;
import cn.edu.zju.shopkeeper.domain.vo.UserVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 下午1:42
 * Description token验证工具类
 */
@Component
public class TokenVerifierUtil {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(TokenVerifierUtil.class);

    private UserService userService;

    @Autowired
    public TokenVerifierUtil(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户信息验证
     *
     * @param token
     * @return
     * @throws ShopkeeperException
     */
    public UserVO verify(String token) throws ShopkeeperException {
        if (token == null) {
            throw new ShopkeeperException(ResultEnum.TOKEN_NOT_EXIST);
        }
        int userId;
        try {
            // 获取 token 中的 user id
            userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));
        } catch (JWTDecodeException e) {
            logger.error("SellerLoginInterceptor preHandle error:{}", ExceptionUtils.getStackTrace(e));
            throw new ShopkeeperException(ResultEnum.TOKEN_INVALID);
        }
        //通过用户主键查找数据库
        UserReq req = new UserReq();
        req.setId(userId);
        ObjectRes<UserVO> res = userService.getUser(req);
        UserVO user = res.getResultObj();
        if (user == null) {
            throw new ShopkeeperException(ResultEnum.USER_NOT_EXIST);
        }
        return user;
    }


    /**
     * 密码验证
     *
     * @param token
     * @param user
     * @throws ShopkeeperException
     */
    public void verifyPassword(String token, UserVO user) throws ShopkeeperException {
        // 验证 token
        try {
            JWTVerifier verifierPassword = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            verifierPassword.verify(token);
        } catch (Exception e) {
            throw new ShopkeeperException(ResultEnum.SYSTEM_ERROR);
        }
    }
}
