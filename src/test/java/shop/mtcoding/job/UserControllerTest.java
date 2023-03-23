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

import shop.mtcoding.job.dto.user.UserReqDto.JoinUserReqDto;
import shop.mtcoding.job.dto.user.UserReqDto.LoginUserReqDto;
import shop.mtcoding.job.model.user.User;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private HttpSession session;

    @Autowired
    private ObjectMapper objectMapper;

    String requestBody = "username=ssar&password=1";

    @Test
    public void testNotNullOrEmptyString() {
        assertNotNull(requestBody);
        assertFalse(requestBody.isEmpty());
    }

    @Test
    public void login_test() throws Exception {
        // given
        LoginUserReqDto loginUserReqDto = new LoginUserReqDto();
        loginUserReqDto.setUsername("ssar");
        loginUserReqDto.setPassword("1");
        loginUserReqDto.setRemember("true");

        String requestBody = objectMapper.writeValueAsString(loginUserReqDto);
        System.out.println(requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/user/login").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        HttpSession session = resultActions.andReturn().getRequest().getSession();
        User principal = (User) session.getAttribute("principal");
        System.out.println("테스트 : " + principal.getUsername());
        System.out.println("테스트 : " + principal.getEmail());

        // then
        assertThat(principal.getUsername()).isEqualTo("ssar");
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void join_test() throws Exception {
        // given
        JoinUserReqDto joinUserReqDto = new JoinUserReqDto();
        joinUserReqDto.setUsername("test");
        joinUserReqDto.setPassword("test");
        joinUserReqDto.setName("test");
        joinUserReqDto.setEmail("test");
        joinUserReqDto.setContact("test");

        String requestBody = objectMapper.writeValueAsString(joinUserReqDto);
        System.out.println(requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/user/join").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        resultActions.andExpect(status().is3xxRedirection());
    }

}
