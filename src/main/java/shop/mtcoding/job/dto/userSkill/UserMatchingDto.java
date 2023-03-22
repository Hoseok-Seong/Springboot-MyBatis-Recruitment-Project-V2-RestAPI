package shop.mtcoding.job.dto.userSkill;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.job.util.DateUtil;

import java.sql.Timestamp;

@Setter
@Getter
public class UserMatchingDto {
    private int userId;
    private String enterpriseName;
    private String enterpriseLogo;
    private String title;
    private String sector;
    private int recruitmentId;

    @Setter
    @Getter
    public static class RecruitmentPostListRespDto {
        private Integer id;
        private String enterpriseId;
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