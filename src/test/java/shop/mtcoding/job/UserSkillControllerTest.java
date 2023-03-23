package shop.mtcoding.job;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserSkillControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void user_skill_test() throws Exception {
        // given
        String requestBody = "userId=1&skill=1&skill=2&skill=3";

        // when
        System.out.println("테스트: " + requestBody);
        ResultActions resultActions = mvc.perform(post("/user/skill").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        // then
        resultActions.andExpect(status().isOk());
    }
}
