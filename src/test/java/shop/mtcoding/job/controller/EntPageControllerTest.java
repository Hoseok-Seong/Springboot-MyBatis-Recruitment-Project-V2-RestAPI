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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import shop.mtcoding.job.config.auth.LoginEnt;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class EntPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MockHttpSession mockSession;

    String jwt = JWT.create()
            .withSubject("토큰제목")
            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .withClaim("id", 1)
            .withClaim("role", "guest")
            .sign(Algorithm.HMAC512("Highre"));

    @BeforeEach
    public void setUp() {
        LoginEnt loginEnt = new LoginEnt(1, "test");
        mockSession = new MockHttpSession();
        mockSession.setAttribute("loginEnt", loginEnt);
    }

    @Test
    public void myapplicant_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/myapplicant").session(mockSession).header("Authorization", jwt));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("data_test : " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void myrecommend_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/myrecommend").session(mockSession).header("Authorization", jwt));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("data_test : " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void mybookmark_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/mybookmarkEnt").session(mockSession).header("Authorization", jwt));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("data_test : " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }
}
