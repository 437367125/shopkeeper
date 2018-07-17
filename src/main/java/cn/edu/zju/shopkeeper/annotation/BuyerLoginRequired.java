package cn.edu.zju.shopkeeper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 下午1:38
 * Description 买家登录验证
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BuyerLoginRequired {
}
