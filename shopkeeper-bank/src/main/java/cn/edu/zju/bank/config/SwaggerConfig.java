package cn.edu.zju.bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/17 上午10:33
 * Description swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        List<Parameter> pars = new ArrayList<>();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("cn.edu.zju.bank.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars) ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("银行后端API文档")
                .description("银行系统API文档")
                //服务条款网址
                //.termsOfServiceUrl("http://blog.csdn.net/forezp")
                .version("1.0")
                .build();
    }
}
