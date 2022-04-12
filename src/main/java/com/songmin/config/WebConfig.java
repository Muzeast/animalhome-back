package com.songmin.config;

import com.songmin.interceptor.AuthenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthenInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public AuthenInterceptor getAuthenInterceptor() {
        return new AuthenInterceptor();
    }
}
