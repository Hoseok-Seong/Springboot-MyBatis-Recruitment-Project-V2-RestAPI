package shop.mtcoding.job.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.job.dto.resume.UpdateResumeDto;
import shop.mtcoding.job.model.resume.Resume;
import shop.mtcoding.job.model.resume.ResumeRepository;
import shop.mtcoding.job.model.user.User;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ResumeControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession mockSession;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ResumeRepository resumeRepository;

    String employeeJwtToken;
    String companyJwtToken;

    @BeforeEach
    public void setUp() throws Exception {

        // employee test용
        MockHttpServletRequestBuilder employeeLoginRequest = post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"ssar\", \"password\":\"1\"}");
        MvcResult employeeLoginResult = mvc.perform(employeeLoginRequest).andReturn();

        // 로그인 응답에서 토큰 추출하기
        employeeJwtToken = employeeLoginResult.getResponse().getHeader("Authorization");

        // Company test용
        MockHttpServletRequestBuilder companyLoginRequest = post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"enterpriseName\":\"긴트\", \"password\":\"1\"}");
        MvcResult companyLoginResult = mvc.perform(companyLoginRequest).andReturn();

        // 로그인 응답에서 토큰 추출하기
        companyJwtToken = companyLoginResult.getResponse().getHeader("Authorization");
    
        // 데이터 인서트
        User user = new User();
        user.setId(1);
        user.setUsername("ssar");
        user.setPassword("1234");
        user.setEmail("ssar@nate.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", user);

        Resume resume = new Resume();
        resume.setId(1);
        resume.setTitle("제목1");
        resume.setContent("내용1");
        resume.setCareer("경력1");
        resume.setSkill("기술1");
        resume.setAward("수상1");
        resume.setAddress("주소1");
        resume.setLink("링크1");
        resume.setEducation("학력1");
        resume.setLanguage("외국어1");
        resume.setBirthdate("생일1");
    }

    @Test
    public void resume_test() throws Exception {
        // given
        
        // when
        ResultActions resultActions = mvc.perform(
                get("/resumes").session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void resumeInsert_test() throws Exception {
        // given
        Resume resume = new Resume();
        resume.setId(1);
        resume.setTitle("제목1");
        resume.setContent("내용1");
        resume.setCareer("경력1");
        resume.setSkill("기술1");
        resume.setAward("수상1");
        resume.setAddress("주소1");
        resume.setLink("링크1");
        resume.setEducation("학력1");
        resume.setLanguage("외국어1");
        resume.setBirthdate("생일1");

        // when
        ResultActions resultActions = mvc.perform(
                post("/resume")
                        .session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        // then

    }

    @Test
    @Transactional
    public void resumeUpdate_test() throws Exception {
        // given
        int id = 1;

        Resume resumePS = resumeRepository.findById(id);

        UpdateResumeDto resumeUpdateDto = new UpdateResumeDto();
        resumeUpdateDto.setTitle("제목1-수정");
        resumeUpdateDto.setContent("내용1-수정");
        resumeUpdateDto.setCareer("경력1-수정");
        resumeUpdateDto.setSkill("기술1-수정");
        resumeUpdateDto.setAward("수상1-수정");
        resumeUpdateDto.setAddress("주소1-수정");
        resumeUpdateDto.setLink("링크1-수정");
        resumeUpdateDto.setEducation("학력1-수정");
        resumeUpdateDto.setLanguage("외국어1-수정");
        resumeUpdateDto.setBirthdate("생일1-수정");

        String requestBody = om.writeValueAsString(resumeUpdateDto);

        // WHEN
        ResultActions resultActions = mvc.perform(
                put("/resume/" + id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .session(mockSession));

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
    }

    @Test
    @Transactional
    public void resumeDelete_test() throws Exception {
        int id = 1;
        // when
        ResultActions resultActions = mvc.perform(
                delete("/resume/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .session(mockSession));

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
    }

}
