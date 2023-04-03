package shop.mtcoding.job.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import shop.mtcoding.job.config.auth.JwtProvider;
import shop.mtcoding.job.config.auth.LoginEnt;
import shop.mtcoding.job.config.auth.LoginUser;

public class JwtVerifyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 요청 URL 가져오기
        String requestUri = req.getRequestURI();
        if (JwtInterface.whiteUrlList.stream().noneMatch(url -> requestUri.startsWith(url))) {

            try {
                String prefixJwt = req.getHeader(JwtProvider.HEADER);
                String jwt = prefixJwt.replace(JwtProvider.TOKEN_PREFIX, "");
                DecodedJWT decodedJWT = JwtProvider.verify(jwt);
                int id = decodedJWT.getClaim("id").asInt();
                String role = decodedJWT.getClaim("role").asString();

                if (role.equals("user")) {
                    HttpSession session = req.getSession();
                    LoginUser loginUser = LoginUser.builder().id(id).role(role).build();
                    session.setAttribute("loginUser", loginUser);
                }

                if (role.equals("enterprise")) {
                    HttpSession session = req.getSession();
                    LoginEnt loginEnt = LoginEnt.builder().id(id).role(role).build();
                    session.setAttribute("loginEnt", loginEnt);
                }
                chain.doFilter(req, resp);
            } catch (NullPointerException | SignatureVerificationException sve) {
                resp.setStatus(401);
                resp.setContentType("text/plain; charset=utf-8");
                resp.getWriter().println("검증 실패 : 로그인 재요청");
            } catch (TokenExpiredException tee) {
                resp.setStatus(401);
                resp.setContentType("text/plain; charset=utf-8");
                resp.getWriter().println("기간 만료 : 로그인 재요청");
            }
        } else {
            chain.doFilter(req, resp);
        }
    }
}