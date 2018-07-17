package cn.edu.zju.shopkeeper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 下午3:43
 * Description 登录控制器（卖家买家都行）
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
}
