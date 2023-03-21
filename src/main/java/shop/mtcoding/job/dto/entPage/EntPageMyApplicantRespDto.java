package shop.mtcoding.job.dto.entPage;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.job.util.DateUtil;

@Getter
@Setter
public class EntPageMyApplicantRespDto {
    private ApplyDto apply;
    private RecruitmentPostDto recruitmentPost;
    private UserDto user;
    private EnterpriseDto enterprise;
    private ApplyResumeDto applyResume;

    @Getter
    @Setter
    public static class ApplyDto {
        private Integer id;
        private Integer userId;
        private Integer enterpriseId;
        private Integer recruitmentPostId;
        private String sector;
        private Integer applyResumeId;
        private Boolean result;
        private Boolean notify;
        private Timestamp createdAt;

        public String getCreatedAtToString() {
            return DateUtil.format(createdAt);
        }
    }

    @Getter
    @Setter
    public static class RecruitmentPostDto {
        private Integer id;
        private Integer enterpriseId;
        private String title;
        private String career;
        private String education;
        private String pay;
        private String sector;
        private String position;
        private String address;
        private String content;
        private String enterpriseLogo;
        private String deadline;
        private Timestamp createdAt;
    }

    @Getter
    @Setter
    public static class UserDto {
        private Integer id;
        private String username;
        private String password;
        private String salt;
        private String name;
        private String email;
        private String contact;
        private Timestamp createdAt;
    }

    @Getter
    @Setter
    public static class EnterpriseDto {
        private Integer id;
        private String enterpriseName;
        private String password;
        private String salt;
        private String address;
        private String contact;
        private String email;
        private String sector;
        private String size;
        private Timestamp createdAt;
    }

    @Getter
    @Setter
    public static class ApplyResumeDto {
        private Integer id;
        private Integer recruitmentPostId;
        private Integer userId;
        private Integer enterpriseId;
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
        private Boolean finish;
        private Timestamp createdAt;
    }
}
