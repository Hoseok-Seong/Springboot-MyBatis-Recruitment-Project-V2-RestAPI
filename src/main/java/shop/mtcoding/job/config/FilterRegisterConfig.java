package shop.mtcoding.job.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.job.config.filter.JwtVerifyFilter;

@Configuration
public class FilterRegisterConfig {
    @Bean
    public FilterRegistrationBean<?> jwtVerifyFilterAdd() {
        FilterRegistrationBean<JwtVerifyFilter> registraion = new FilterRegistrationBean<>();
        registraion.setFilter(new JwtVerifyFilter());
        registraion.addUrlPatterns("/user");
        registraion.setOrder(1);
        return registraion;
    }
}
