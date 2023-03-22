package shop.mtcoding.job.dto.userPage;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.job.util.DateUtil;

import java.sql.Timestamp;

@Setter @Getter
public class UserPageMatchingDto {
    private UserMatchingDto userMatching;
    private RecruitmentPostListRespDto recruitment;

    @Setter @Getter
    public static class UserMatchingDto{
        private Integer userId;
        private String enterpriseName;
        private String enterpriseLogo;
        private String title;
        private String sector;
        private Integer recruitmentId;
    }

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
        private Long diffDays;
        private Timestamp createdAt;

        public void calculateDiffDays() { // D-Day 계산하는 메서드 추가
            diffDays = DateUtil.deadline(deadline);
        }
    }
}
