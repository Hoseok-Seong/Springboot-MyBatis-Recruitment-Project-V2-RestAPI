package shop.mtcoding.job.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.config.aop.EntIdResolver;
import shop.mtcoding.job.config.aop.UserIdResolver;
import shop.mtcoding.job.config.interceptor.CustomInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UserIdResolver userIdResolver;
    private final EntIdResolver entIdResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userIdResolver);
        resolvers.add(entIdResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor())
                .excludePathPatterns("/ns/**");
    }
}
