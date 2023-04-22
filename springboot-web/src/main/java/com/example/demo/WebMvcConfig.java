package com.example.demo;

import com.example.demo.handlerinterceptor.FooHandleIntercept;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final FooHandleIntercept fooHandleIntercept;

    public WebMvcConfig(FooHandleIntercept fooHandleIntercept) {
        this.fooHandleIntercept = fooHandleIntercept;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fooHandleIntercept).addPathPatterns("/foo/**");
    }
}
