package shop.mtcoding.job.config.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import shop.mtcoding.job.config.auth.JwtProvider;
import shop.mtcoding.job.config.auth.LoginUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class JwtVerifyFilter implements Filter {
                
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 요청 URL 가져오기
        String requestUri = req.getRequestURI();
        
        // /user/login URL 패턴이 아닐 경우에만 필터링
        if (!requestUri.startsWith("/ns/")) {
            String prefixJwt = req.getHeader(JwtProvider.HEADER);
            String jwt = prefixJwt.replace(JwtProvider.TOKEN_PREFIX, "");
            try {
                DecodedJWT decodedJWT = JwtProvider.verify(jwt);
                int id = decodedJWT.getClaim("id").asInt();
                String role = decodedJWT.getClaim("role").asString();

                // 내부적으로 권한처리
                HttpSession session = req.getSession();
                LoginUser loginUser = LoginUser.builder().id(id).role(role).build();
                session.setAttribute("loginUser", loginUser);
                chain.doFilter(req, resp);
            } catch (SignatureVerificationException sve) {
                resp.setStatus(401);
                resp.setContentType("text/plain; charset=utf-8");
                resp.getWriter().println("검증 실패 : 로그인 다시해");
            } catch (TokenExpiredException tee) {
                resp.setStatus(401);
                resp.setContentType("text/plain; charset=utf-8");
                resp.getWriter().println("기간 만료 : 로그인 다시해");
            }
        } else {
            // /user/login URL 패턴일 경우 필터링하지 않음
            chain.doFilter(req, resp);
        }
    }

    //     HttpServletRequest req = (HttpServletRequest) request;
    //     HttpServletResponse resp = (HttpServletResponse) response;
    //     String prefixJwt = req.getHeader(JwtProvider.HEADER);
    //     String jwt = prefixJwt.replace(JwtProvider.TOKEN_PREFIX, "");
    //     try {
    //         DecodedJWT decodedJWT = JwtProvider.verify(jwt);
    //         int id = decodedJWT.getClaim("id").asInt();
    //         String role = decodedJWT.getClaim("role").asString();

    //         // 내부적으로 권한처리
    //         HttpSession session = req.getSession();
    //         LoginUser loginUser = LoginUser.builder().id(id).role(role).build();
    //         System.out.println("테스트 : 필터에 들어옴 " + loginUser);
    //         session.setAttribute("loginUser", loginUser);
    //         chain.doFilter(req, resp);
    //     } catch (SignatureVerificationException sve) {
    //         resp.setStatus(401);
    //         resp.setContentType("text/plain; charset=utf-8");
    //         resp.getWriter().println("검증 실패 : 로그인 다시해");
    //     } catch (TokenExpiredException tee) {
    //         resp.setStatus(401);
    //         resp.setContentType("text/plain; charset=utf-8");
    //         resp.getWriter().println("기간 만료 : 로그인 다시해");
    //     }
    // }

}