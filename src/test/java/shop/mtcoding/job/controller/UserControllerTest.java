package shop.mtcoding.job.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.job.dto.user.UserReqDto.JoinUserReqDto;
import shop.mtcoding.job.dto.user.UserReqDto.LoginUserReqDto;
import shop.mtcoding.job.dto.user.UserReqDto.UpdateUserReqDto;
import shop.mtcoding.job.model.user.User;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private HttpSession session;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockHttpSession mockSession;

    String requestBody = "username=ssar&password=1";

    String jwt = JWT.create()
            .withSubject("토큰제목")
            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .withClaim("id", 1)
            .withClaim("role", "guest")
            .sign(Algorithm.HMAC512("Highre"));

    @Test
    public void testNotNullOrEmptyString() {
        assertNotNull(requestBody);
        assertFalse(requestBody.isEmpty());
    }

    @Test
    public void login_test() throws Exception {
        // given
        LoginUserReqDto loginUserReqDto = new LoginUserReqDto();
        loginUserReqDto.setUsername("ssar");
        loginUserReqDto.setPassword("1");
        loginUserReqDto.setRemember("true");

        String requestBody = objectMapper.writeValueAsString(loginUserReqDto);
        System.out.println(requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/ns/user/login").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void join_test() throws Exception {
        // given
        JoinUserReqDto joinUserReqDto = new JoinUserReqDto();
        joinUserReqDto.setUsername("test");
        joinUserReqDto.setPassword("test");
        joinUserReqDto.setName("test");
        joinUserReqDto.setEmail("test");
        joinUserReqDto.setContact("test");
        joinUserReqDto.setRole("user");

        String requestBody = objectMapper.writeValueAsString(joinUserReqDto);
        System.out.println(requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/ns/user/join").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void update_test() throws Exception {
        // given
        User principal = new User();
        principal.setId(1);
        principal.setUsername("ssar");
        principal.setPassword(
                "356067e7d02ead0e9086e3f9e9cef88e8f6ca59222cd180bbf1a6205b7b40631");
        principal.setSalt("{bcrypt}$2a$10$4h5bhPEcnLEsQ7fe.1Rx5OfeEH0VLV9LE0kDb1WqwWMRsjsCptRmy");
        principal.setName("김동석");
        principal.setEmail("ssar@nate.com");
        principal.setContact("010-1111-2222");
        principal.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", principal);

        UpdateUserReqDto updateUserReqDto = new UpdateUserReqDto();
        updateUserReqDto.setPassword("test");
        updateUserReqDto.setEmail("test");
        updateUserReqDto.setContact("test");

        String requestBody = objectMapper.writeValueAsString(updateUserReqDto);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/user/update")
                        .session(mockSession)
                        .content(requestBody).header("Authorization", jwt)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        resultActions.andExpect(status().is3xxRedirection());
    }

}
