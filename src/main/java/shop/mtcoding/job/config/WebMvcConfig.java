package shop.mtcoding.job.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import shop.mtcoding.job.interceptor.EntInterceptor;
import shop.mtcoding.job.interceptor.UserInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/user/update", "/mybookmark", "/mymatching", "/myapply", "/bookmark*", "/resume*",
                        "/apply/{id}")
                .excludePathPatterns();
        registry.addInterceptor(new EntInterceptor())
                .addPathPatterns("/apply/result/{id}", "/enterprise/update", "/myapplicant", "/myrecommend",
                        "/mybookmarkEnt", "/recruitment/saveForm", "/recruitment", "/recruitment/{id}")
                .excludePathPatterns();
    }
}