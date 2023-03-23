package shop.mtcoding.job.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.job.model.enterprise.Enterprise;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class RecruitmentControllerTest {

        @Autowired
        private MockMvc mvc;

        private MockHttpSession mockSession;

        @Autowired
        private ObjectMapper om;

        @BeforeEach
        public void setUp() {
                Enterprise enterprise = new Enterprise();
                enterprise.setId(1);
                enterprise.setEnterpriseName("긴트");
                enterprise.setPassword(
                                "356067e7d02ead0e9086e3f9e9cef88e8f6ca59222cd180bbf1a6205b7b40631");
                enterprise.setSalt("{bcrypt}$2a$10$4h5bhPEcnLEsQ7fe.1Rx5OfeEH0VLV9LE0kDb1WqwWMRsjsCptRmy");
                enterprise.setAddress("강남구 삼성동 75-6 수당빌딩 4층");
                enterprise.setContact("010-7763-4370");
                enterprise.setEmail("company@nate.com");
                enterprise.setSector("스타트업");
                enterprise.setSize("스타트업");
                enterprise.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

                mockSession = new MockHttpSession();
                mockSession.setAttribute("principalEnt", enterprise);
        }

        @Test
        public void deleteRecruitment_test() throws Exception {
                // given
                int id = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                delete("/recruitment/" + id).session(mockSession));
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

                // when
                ResultActions resultActions = mvc.perform(
                                get("/recruitment/detail/" + id));

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
                ResultActions resultActions = mvc.perform(post("/recruitment/search")
                                .content("{\"searchString\": \"" + searchString + "\"}")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .session(mockSession));

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
                                get("/recruitment/" + id + "/updateForm").session(mockSession));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("data_test : " + responseBody);

                // then
                resultActions.andExpect(jsonPath("$.code").value(1));
                resultActions.andExpect(status().isOk());
        }
}