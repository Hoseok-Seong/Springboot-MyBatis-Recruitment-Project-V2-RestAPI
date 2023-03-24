package shop.mtcoding.job.controller;

import static org.assertj.core.api.Assertions.assertThat;
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

import shop.mtcoding.job.dto.enterprise.EnterpriseReqDto.JoinEnterpriseReqDto;
import shop.mtcoding.job.dto.enterprise.EnterpriseReqDto.LoginEnterpriseReqDto;
import shop.mtcoding.job.dto.enterprise.EnterpriseReqDto.UpdateEnterpriseReqDto;
import shop.mtcoding.job.model.enterprise.Enterprise;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class EnterpriseControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockHttpSession mockSession;

    String requestBody = "username=긴트&password=1";

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
        LoginEnterpriseReqDto loginEnterpriseReqDto = new LoginEnterpriseReqDto();
        loginEnterpriseReqDto.setEnterpriseName("긴트");
        loginEnterpriseReqDto.setPassword("1");
        loginEnterpriseReqDto.setRememberEnt("true");

        String requestBody = objectMapper.writeValueAsString(loginEnterpriseReqDto);
        System.out.println(requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/ns/enterprise/login").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void join_test() throws Exception {
        // given
        JoinEnterpriseReqDto joinEnterpriseReqDto = new JoinEnterpriseReqDto();
        joinEnterpriseReqDto.setEnterpriseName("test");
        joinEnterpriseReqDto.setPassword("test");
        joinEnterpriseReqDto.setAddress("test");
        joinEnterpriseReqDto.setEmail("test");
        joinEnterpriseReqDto.setContact("test");
        joinEnterpriseReqDto.setSector("test");
        joinEnterpriseReqDto.setSize("test");

        String requestBody = objectMapper.writeValueAsString(joinEnterpriseReqDto);
        System.out.println(requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/ns/enterprise/join").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void update_test() throws Exception {
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

        UpdateEnterpriseReqDto updateEnterpriseReqDto = new UpdateEnterpriseReqDto();
        updateEnterpriseReqDto.setPassword("test");
        updateEnterpriseReqDto.setAddress("test");
        updateEnterpriseReqDto.setEmail("test");
        updateEnterpriseReqDto.setContact("test");
        updateEnterpriseReqDto.setSector("test");
        updateEnterpriseReqDto.setSize("test");

        String requestBody = objectMapper.writeValueAsString(updateEnterpriseReqDto);
        System.out.println(requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(post("/enterprise/update").session(mockSession).content(requestBody)
                        .header("Authorization", jwt)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        resultActions.andExpect(status().is3xxRedirection());
    }
}
