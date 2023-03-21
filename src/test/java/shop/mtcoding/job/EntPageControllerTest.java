package shop.mtcoding.job;

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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import shop.mtcoding.job.model.enterprise.Enterprise;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class EntPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MockHttpSession mockSession;

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
    public void myapplicant_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/myapplicant").session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("data_test : " + responseBody);

        // then

        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());

    }

}
