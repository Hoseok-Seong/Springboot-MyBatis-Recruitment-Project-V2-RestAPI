package shop.mtcoding.job.dto.userPage;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.job.util.DateUtil;

@Setter
@Getter
public class UserPageMatchingDto {
    private UserMatchingDto userMatching;
    private RecruitmentPostListRespDto recruitment;

    @Setter
    @Getter
    public static class UserMatchingDto {
        private Integer id;
        private Integer userId;
        private String enterpriseName;
        private String enterpriseLogo;
        private String title;
        private String sector;
        private UserSkillDto userSkillDto;
        private Integer recruitmentId;

        @Setter
        @Getter
        public static class UserSkillDto {
            @JsonIgnore
            private List<Integer> skill;
            private List<String> skillString;

        }
    }

    @Setter
    @Getter
    public static class RecruitmentPostListRespDto {
        private Integer id;
        private String enterpriseId;
        private String recruitmentId;
        private String title;
        private String career;
        private String education;
        private String pay;
        private String sector;
        private String position;
        private String content;
        private String address;
        private String enterpriseLogo;
        private String enterpriseName;
        private String deadline;
        private long diffDays;
        private Timestamp createdAt;

        public void calculateDiffDays() { // D-Day 계산하는 메서드 추가
            diffDays = DateUtil.deadline(deadline);
        }
    }

}
