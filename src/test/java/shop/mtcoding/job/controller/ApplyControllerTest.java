package shop.mtcoding.job.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.job.config.auth.LoginEnt;
import shop.mtcoding.job.config.auth.LoginUser;
import shop.mtcoding.job.dto.apply.ApplyReqDto.InsertApplyReqDto;
import shop.mtcoding.job.dto.apply.ApplyReqDto.UpdateApplicantResultReqDto;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ApplyControllerTest {

        @Autowired
        private MockMvc mvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private MockHttpSession mockSession;

        String jwt = JWT.create()
                        .withSubject("토큰제목")
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                        .withClaim("id", 1)
                        .withClaim("role", "test")
                        .sign(Algorithm.HMAC512("Highre"));

        @BeforeEach
        public void setUp() {
                LoginUser loginUser = new LoginUser(1, "test");
                mockSession = new MockHttpSession();
                mockSession.setAttribute("loginUser", loginUser);
        }

        @Test
        public void insertApply_test() throws Exception {
                // given
                int id = 8;

                InsertApplyReqDto insertApplyReqDto = new InsertApplyReqDto();
                insertApplyReqDto.setEnterpriseId(8);
                insertApplyReqDto.setRecruitmentPostId(8);
                insertApplyReqDto.setSector("test");
                insertApplyReqDto.setApplyResumeId(1);
                insertApplyReqDto.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

                String requestBody = objectMapper.writeValueAsString(insertApplyReqDto);
                System.out.println(requestBody);

                // when
                ResultActions resultActions = mvc.perform(
                                post("/applications/" + id).content(requestBody)
                                                .header("Authorization", jwt).session(mockSession)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE));

                // then
                resultActions.andExpect(jsonPath("$.code").value(1));
                resultActions.andExpect(status().isCreated());
        }

        @Test
        public void deleteApply_test() throws Exception {
                // given
                int id = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                delete("/applications/" + id).session(mockSession).header("Authorization", jwt));

                // then
                resultActions.andExpect(jsonPath("$.code").value(1));
                resultActions.andExpect(status().isOk());

        }

        @Test
        public void updateResult_test() throws Exception {
                // given
                LoginEnt loginEnt = new LoginEnt(1, "test");
                mockSession = new MockHttpSession();
                mockSession.setAttribute("loginEnt", loginEnt);

                int id = 1;

                UpdateApplicantResultReqDto updateApplicantResultReqDto = new UpdateApplicantResultReqDto();
                updateApplicantResultReqDto.setResult(true);
                updateApplicantResultReqDto.setNotify(true);

                String requestBody = objectMapper.writeValueAsString(updateApplicantResultReqDto);
                System.out.println(requestBody);

                // when
                ResultActions resultActions = mvc.perform(
                                put("/applications/result/" + id).content(requestBody).session(mockSession)
                                                .header("Authorization", jwt)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE));

                // then
                resultActions.andExpect(jsonPath("$.code").value(1));
                resultActions.andExpect(status().isOk());

        }
}
