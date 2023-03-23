package shop.mtcoding.job.dto.resume;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.job.model.resume.Resume;

@Setter
@Getter
public class SaveResumeDto {
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

    public Resume toModel(int enterpriseId, String enterpriseLogo) {
        Resume resume = new Resume();
        resume.setTitle(title);
        resume.setContent(content);
        resume.setCareer(career);
        resume.setEducation(education);
        resume.setSkill(skill);
        resume.setAward(award);
        resume.setAddress(address);
        resume.setLanguage(language);
        resume.setLink(link);
        resume.setBirthdate(birthdate);
        resume.setFinish(finish);
        resume.setCreatedAt(createdAt);
        return resume;
    }
}
