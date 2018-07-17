package cn.edu.zju.shopkeeper.config;

import cn.edu.zju.shopkeeper.annotation.LoginRequired;
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
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 上午9:39
 * Description 登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    private UserService userService;

    public LoginInterceptor() {
    }

    @Autowired
    public LoginInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 执行认证
            // 从 http 请求头中取出 token
            String token = request.getHeader("token");
            if (token == null) {
                throw new ShopkeeperException(ResultEnum.TOKEN_NOT_EXIST);
            }
            int userId;
            try {
                // 获取 token 中的 user id
                userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));
            } catch (JWTDecodeException e) {
                logger.error("LoginInterceptor preHandle error:{}", ExceptionUtils.getStackTrace(e));
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
            // 验证 token
            try {
                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    verifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new ShopkeeperException(ResultEnum.TOKEN_INVALID);
                }
            } catch (UnsupportedEncodingException ignore) {
            }
            request.setAttribute("user", user);
            return true;
        }
        return true;
    }
}
