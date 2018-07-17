package cn.edu.zju.shopkeeper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 上午10:02
 * Description 全局配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sellerLoginInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(buyerLoginInteceptor()).addPathPatterns("/**");
    }

    @Bean
    public SellerLoginInterceptor sellerLoginInterceptor() {
        return new SellerLoginInterceptor();
    }

    @Bean
    public BuyerLoginInterceptor buyerLoginInteceptor() {
        return new BuyerLoginInterceptor();
    }
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(fastJsonHttpMessageConverterEx());
//    }
}
