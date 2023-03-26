package shop.mtcoding.job.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import shop.mtcoding.job.config.auth.LoginEnt;
import shop.mtcoding.job.config.auth.LoginUser;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class RecruitmentControllerTest {

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
        public void setUp() {
                LoginEnt loginEnt = new LoginEnt(1, "test");
                mockSession = new MockHttpSession();
                mockSession.setAttribute("loginEnt", loginEnt);
        }

        @Test
        public void deleteRecruitment_test() throws Exception {
                // given
                int id = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                delete("/recruitment/" + id).session(mockSession).header("Authorization", jwt));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("delete_test : " + responseBody);

                // then

                resultActions.andExpect(jsonPath("$.code").value(1));
                resultActions.andExpect(status().isOk());
        }

        @Test
        public void detail_test() throws Exception {
                // given
                int id = 1;
                LoginUser loginUser = new LoginUser(1, "user");
                mockSession = new MockHttpSession();
                mockSession.setAttribute("loginUser", loginUser);

                // when
                ResultActions resultActions = mvc.perform(
                                get("/ns/recruitment/detail/" + id).session(mockSession));

                MvcResult result = resultActions.andReturn();
                String content = result.getResponse().getContentAsString();
                System.out.println("테스트 결과: " + content);

                // then
                resultActions.andExpect(jsonPath("$.code").value(1));
                resultActions.andExpect(status().isOk());
        }

        @Test
        public void searchBoard_test() throws Exception {
                // given
                String searchString = "1";

                // when
                ResultActions resultActions = mvc.perform(post("/ns/recruitment/search")
                                .content("{\"searchString\": \"" + searchString + "\"}")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .session(mockSession).header("Authorization", jwt));

                // then
                resultActions.andExpect(jsonPath("$..[0].title").value("프론트엔드 개발자"));
                resultActions.andExpect(jsonPath("$..[0].id").value(1));
        }

        @Test
        public void update_test() throws Exception {
                // given
                int id = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                get("/recruitment/" + id + "/updateForm").session(mockSession).header("Authorization",
                                                jwt));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("data_test : " + responseBody);

                // then
                resultActions.andExpect(jsonPath("$.code").value(1));
                resultActions.andExpect(status().isOk());
        }
}