package shop.mtcoding.job.dto.entPage;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntPageMyRecommendRespDto {
    private EnterpriseDto enterprise;
    private RecruitmentSkillDto recruitmentSkill;
    private UserSkillDto userSkill;
    private UserDto user;

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
    public static class RecruitmentSkillDto {
        private Integer id;
        private Integer recruitmentId;
        private Integer skill;
    }

    @Setter
    @Getter
    public static class UserSkillDto {
        private Integer id;
        private Integer userId;
        private Integer skill;
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
}
