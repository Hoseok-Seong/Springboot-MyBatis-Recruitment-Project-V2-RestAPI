package shop.mtcoding.job.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.job.model.user.User;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserPageControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession mockSession;

    @BeforeEach
    public void setUp() throws Exception {
        // 데이터 인서트
        User user = new User();
        user.setId(1);
        user.setUsername("ssar");
        user.setPassword("1234");
        user.setEmail("ssar@nate.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", user);
    }

    @Test
    public void apply_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/myapply").session(mockSession));
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
                get("/mymatching").session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + responseBody);
        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void bookmark_test() throws Exception {
        ResultActions resultActions = mvc.perform(
                get("/mybookmark").session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : " + responseBody);
        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }
}
