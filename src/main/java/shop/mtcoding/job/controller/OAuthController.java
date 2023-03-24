package shop.mtcoding.job.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.config.auth.JwtProvider;
import shop.mtcoding.job.model.enterprise.Enterprise;
import shop.mtcoding.job.model.enterprise.EnterpriseRepository;
import shop.mtcoding.job.model.user.User;
import shop.mtcoding.job.model.user.UserRepository;
import shop.mtcoding.job.service.OAuthService;

@RequiredArgsConstructor
@RestController
public class OAuthController {

    private final OAuthService oAuthService;
    private final UserRepository userRepository;
    private final EnterpriseRepository enterpriseRepository;

    @GetMapping("/kakao_user_callback")
    public ResponseEntity<String> kakaoUserCallback(String code) throws JsonProcessingException {

        // 1. code 값 존재 유무 확인
        if (code == null || code.isEmpty()) {
            // code가 없다
            return ResponseEntity.badRequest().body("code없음");
        }

        String accessToken = oAuthService.토큰받기_유저(code);
        String email = oAuthService.이메일받기(accessToken);
        Long id = oAuthService.아이디받기(accessToken);

        // 2. 해당 email로 회원가입되어 있는 user 정보가 있는지 db 조회
        User user = userRepository.findByName("kakao_" + id);

        // 3. 존재하지 않을 시, 강제 회원가입 후, 그 user 정보로 session 생성(자동 로그인)
        if (user == null) {
            userRepository.insert("kakao_" + id, UUID.randomUUID().toString(), UUID.randomUUID().toString(), "", email,
                    "", "user");
            String jwt = JwtProvider.create(user);
            return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body("로그인 성공");
        } // 처음 가입시 이름 전화번호 공백

        // 4. 존재할 시
        if (user != null) {
            String jwt = JwtProvider.create(user);
            return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body("로그인 성공");
        }
    }

    @GetMapping("/kakao_enterprise_callback")
    public ResponseEntity<String> kakaoEnterpriseCallback(String code) throws JsonProcessingException {

        // 1. code 값 존재 유무 확인
        if (code == null || code.isEmpty()) {
            // code가 없다
            return ResponseEntity.badRequest().body("code없음");
        }

        String accessToken = oAuthService.토큰받기_기업(code);
        String email = oAuthService.이메일받기(accessToken);
        Long id = oAuthService.아이디받기(accessToken);

        // 2. 해당 email로 회원가입되어 있는 enterprise 정보가 있는지 db 조회
        Enterprise enterprise = enterpriseRepository.findByName("kakao_" + id);

        // 3. 존재하지 않을 시, 강제 회원가입
        if (enterprise == null) {
            enterpriseRepository.insert("kakao_" + id, UUID.randomUUID().toString(), UUID.randomUUID().toString(), "",
                    "", email,
                    "", "", "enterprise");
            String jwt = JwtProvider.createEnt(enterprise);
            return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body("로그인 성공");
        } // 처음 가입시 주소 전화번호 size sector 공백

        // 4. 존재할 시
        if (enterprise != null) {
            String jwt = JwtProvider.createEnt(enterprise);
            return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body("로그인 성공");
        }
    }
}
