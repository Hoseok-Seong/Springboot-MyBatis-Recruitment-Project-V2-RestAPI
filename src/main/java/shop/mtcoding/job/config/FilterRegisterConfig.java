package shop.mtcoding.job.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.job.config.filter.JwtVerifyFilter;

@Configuration
public class FilterRegisterConfig {

    @Bean
    public FilterRegistrationBean<JwtVerifyFilter> jwtVerifyFilterAdd() {
        FilterRegistrationBean<JwtVerifyFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtVerifyFilter());
        registration.addUrlPatterns("/*"); // 모든 URL 패턴에 대해 필터 적용
        registration.setOrder(1);

        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("exclusions", "/s/*"); // 제외할 URL 패턴 설정
        registration.setInitParameters(initParameters);

        return registration;
    }
}
