package shop.mtcoding.job.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import shop.mtcoding.job.config.auth.LoginUser;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserPageControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession mockSession;

    String jwt = JWT.create()
            .withSubject("토큰제목")
            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .withClaim("id", 1)
            .withClaim("role", "test")
            .sign(Algorithm.HMAC512("Highre"));

    @BeforeEach
    public void setUp() throws Exception {
        LoginUser loginUser = new LoginUser(1, "test");
        mockSession = new MockHttpSession();
        mockSession.setAttribute("loginUser", loginUser);
    }

    @Test
    public void apply_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/myapply").session(mockSession).header("Authorization", jwt));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + responseBody);
        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void matching_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/mymatching").session(mockSession).header("Authorization", jwt));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + responseBody);
        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void bookmark_test() throws Exception {
        ResultActions resultActions = mvc.perform(
                get("/mybookmark").session(mockSession).header("Authorization", jwt));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + responseBody);
        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }
}
