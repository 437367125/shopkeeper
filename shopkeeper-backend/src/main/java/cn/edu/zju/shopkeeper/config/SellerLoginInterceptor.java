package cn.edu.zju.shopkeeper.config;

import cn.edu.zju.shopkeeper.annotation.SellerLoginRequired;
import cn.edu.zju.shopkeeper.constants.ShopkeeperConstant;
import cn.edu.zju.shopkeeper.domain.vo.UserVO;
import cn.edu.zju.shopkeeper.enums.ResultEnum;
import cn.edu.zju.shopkeeper.exception.ShopkeeperException;
import cn.edu.zju.shopkeeper.service.UserService;
import cn.edu.zju.shopkeeper.utils.TokenVerifierUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 上午9:39
 * Description 卖家登录拦截器
 */
@Component
public class SellerLoginInterceptor implements HandlerInterceptor {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(SellerLoginInterceptor.class);
    @Autowired
    private UserService userService;
    @Autowired
    private TokenVerifierUtil tokenVerifierUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 判断接口是否需要登录
        SellerLoginRequired methodAnnotation = method.getAnnotation(SellerLoginRequired.class);
        // 有 @SellerLoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 从 http 请求头中取出 token
            String token = request.getHeader("token");
            UserVO user = tokenVerifierUtil.verify(token);
            //验证用户类型是否为卖家
            if (!ShopkeeperConstant.SELLER.equals(user.getType())) {
                throw new ShopkeeperException(ResultEnum.USER_TYPE_ERROR);
            }
            //密码验证
            tokenVerifierUtil.verifyPassword(token, user);
            request.setAttribute("user", user);
        }
        return true;
    }
}
