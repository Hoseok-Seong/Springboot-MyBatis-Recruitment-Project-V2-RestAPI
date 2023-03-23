package shop.mtcoding.job.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.job.dto.apply.ApplyReqDto.InsertApplyReqDto;
import shop.mtcoding.job.dto.apply.ApplyReqDto.UpdateApplicantResultReqDto;
import shop.mtcoding.job.model.enterprise.Enterprise;
import shop.mtcoding.job.model.user.User;

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

    @BeforeEach
    public void setUp() throws Exception {
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
                post("/apply/" + id).session(mockSession).content(requestBody)
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
                delete("/apply/" + id).session(mockSession));

        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());

    }

    @Test
    public void updateResult_test() throws Exception {
        // given
        Enterprise principalEnt = new Enterprise();
        principalEnt.setId(1);
        principalEnt.setEnterpriseName("긴트");
        principalEnt.setPassword(
                "356067e7d02ead0e9086e3f9e9cef88e8f6ca59222cd180bbf1a6205b7b40631");
        principalEnt.setSalt("{bcrypt}$2a$10$4h5bhPEcnLEsQ7fe.1Rx5OfeEH0VLV9LE0kDb1WqwWMRsjsCptRmy");
        principalEnt.setAddress("강남구 삼성동 75-6 수당빌딩 4층");
        principalEnt.setContact("010-7763-4370");
        principalEnt.setEmail("company@nate.com");
        principalEnt.setSector("스타트업");
        principalEnt.setSize("스타트업");
        principalEnt.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        mockSession = new MockHttpSession();
        mockSession.setAttribute("principalEnt", principalEnt);

        int id = 1;

        UpdateApplicantResultReqDto updateApplicantResultReqDto = new UpdateApplicantResultReqDto();
        updateApplicantResultReqDto.setResult(true);
        updateApplicantResultReqDto.setNotify(true);

        String requestBody = objectMapper.writeValueAsString(updateApplicantResultReqDto);
        System.out.println(requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                put("/apply/result/" + id).session(mockSession).content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());

    }
}
