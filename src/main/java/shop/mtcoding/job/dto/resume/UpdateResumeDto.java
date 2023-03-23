package shop.mtcoding.job.dto.resume;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UpdateResumeDto {
    private String title;
    private String content;
    private String career;
    private String education;
    private String skill;
    private String award;
    private String language;
    private String link;
    private String birthdate;
    private String address;
    private boolean finish;
    private Timestamp createdAt;
}
