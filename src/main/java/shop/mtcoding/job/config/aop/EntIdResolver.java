package shop.mtcoding.job.config.aop;

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
import shop.mtcoding.job.config.auth.LoginEnt;
import shop.mtcoding.job.handler.exception.CustomApiException;

@Component
@RequiredArgsConstructor
public class EntIdResolver implements HandlerMethodArgumentResolver {
    private final HttpSession session;

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        if (session != null) {
            if (session.getAttribute("loginEnt") == null) {
                throw new CustomApiException("기업회원으로 로그인해주세요", HttpStatus.BAD_REQUEST);
            } else {
                LoginEnt loginEnt = (LoginEnt) session.getAttribute("loginEnt");
                return loginEnt.getId();
            }
        }
        return null;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(EntId.class);
    }
}
