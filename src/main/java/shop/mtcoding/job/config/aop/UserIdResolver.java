package shop.mtcoding.job.config.aop;

import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.config.auth.LoginUser;

@Component
@RequiredArgsConstructor
public class UserIdResolver implements HandlerMethodArgumentResolver {
    private final HttpSession session;

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        if (session != null) {
            if (session.getAttribute("loginUser") == null) {
                return null;
            } else {
                LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
                return loginUser.getId();
            }
        }
        return null;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class);
    }
}
