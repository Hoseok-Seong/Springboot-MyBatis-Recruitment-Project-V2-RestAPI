package shop.mtcoding.job;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.job.dto.enterprise.EnterpriseReqDto.JoinEnterpriseReqDto;
import shop.mtcoding.job.dto.enterprise.EnterpriseReqDto.LoginEnterpriseReqDto;
import shop.mtcoding.job.model.enterprise.Enterprise;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class EnterpriseControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private HttpSession session;

    @Autowired
    private ObjectMapper objectMapper;

    String requestBody = "username=긴트&password=356067e7d02ead0e9086e3f9e9cef88e8f6ca59222cd180bbf1a6205b7b40631";

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
        ResultActions resultActions = mvc.perform(post("/enterprise/login").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        HttpSession session = resultActions.andReturn().getRequest().getSession();
        if (session == null) {
            System.out.println("테스트 : 세션이없어!");
        }
        Enterprise principalEnt = (Enterprise) session.getAttribute("principalEnt");
        System.out.println("테스트 : " + principalEnt.getEnterpriseName());
        System.out.println("테스트 : " + principalEnt.getId());

        // then
        assertThat(principalEnt.getEnterpriseName()).isEqualTo("긴트");
        resultActions.andExpect(status().isCreated());
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

        // when
        ResultActions resultActions = mvc.perform(post("/join").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        // then
        resultActions.andExpect(status().is3xxRedirection());
    }
}
