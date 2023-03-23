package shop.mtcoding.job.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import shop.mtcoding.job.handler.exception.CustomException;
import shop.mtcoding.job.model.enterprise.Enterprise;

@Component
public class EntInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // MainController obj = new MainController();
        // final Logger logger = LoggerFactory.getLogger(obj.getClass());

        final Logger logger = LoggerFactory.getLogger("[preHandle]");

        logger.info("PreHandle 진입!");

        HttpSession session = request.getSession();
        Enterprise principalEnt = (Enterprise) session.getAttribute("principalEnt");
        if (principalEnt == null) {
            // return false;
            throw new CustomException("기업회원으로 로그인을 해주세요",
                    HttpStatus.UNAUTHORIZED);
        }
        return true;
    }
}
