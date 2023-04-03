package shop.mtcoding.job.config.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.config.auth.LoginUser;
import shop.mtcoding.job.handler.exception.CustomApiException;

@Component
@RequiredArgsConstructor
public class UserIdResolver implements HandlerMethodArgumentResolver {
    private final HttpSession session;

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String requestURI = request.getRequestURI();
        if (session != null) {
            if (session.getAttribute("loginUser") == null && requestURI.matches("/ns/recruitment/detail/.*")) {
                return UserInterface.defaultValue;
            }
            if (session.getAttribute("loginUser") == null) {
                throw new CustomApiException("개인회원으로 로그인해주세요", HttpStatus.BAD_REQUEST);
            } else {
                LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
                return loginUser.getId();
            }
        }
        return UserInterface.defaultValue;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class);
    }
}
