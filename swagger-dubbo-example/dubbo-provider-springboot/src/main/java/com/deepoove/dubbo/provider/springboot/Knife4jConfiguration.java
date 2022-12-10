package com.deepoove.dubbo.provider.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author wade
 * @date 2022/10/14 15:31
 **/
@Configuration
@EnableSwagger2
public class Knife4jConfiguration {
    @Bean
    @Primary
    public SwaggerResourcesProvider newSwaggerResourcesProvider(Environment env, DocumentationCache documentationCache) {
        return new InMemorySwaggerResourcesProvider(env, documentationCache) {

            @Override
            public List<SwaggerResource> get() {
                // 1. 调用 InMemorySwaggerResourcesProvider
                List<SwaggerResource> resources = super.get();
                // 2. 添加 swagger-dubbo 的资源地址
                SwaggerResource dubboSwaggerResource = new SwaggerResource();
                dubboSwaggerResource.setName("dubbo");
                dubboSwaggerResource.setSwaggerVersion("2.0");
                dubboSwaggerResource.setUrl("/swagger-dubbo/api-docs");
                dubboSwaggerResource.setLocation("/swagger-dubbo/api-docs"); // 即将废弃，和 url 属性等价。
                resources.add(0, dubboSwaggerResource);
                return resources;
            }

        };
    }
}
