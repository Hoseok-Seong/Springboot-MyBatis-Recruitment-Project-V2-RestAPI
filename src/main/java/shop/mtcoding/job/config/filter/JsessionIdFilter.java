package shop.mtcoding.job.config.filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class JsessionIdFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        // 초기화 작업
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // JSessionID 쿠키를 제거
        if (request.isRequestedSessionIdFromURL()) {
            // URL Rewriting 방식일 경우
            String url = request.getRequestURL().toString();
            response.sendRedirect(url);
        } else {
            // 쿠키 방식일 경우
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }

        chain.doFilter(req, res);
    }

    public void destroy() {
        // 종료 작업
    }
}
