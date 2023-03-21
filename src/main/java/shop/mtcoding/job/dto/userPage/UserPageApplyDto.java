package shop.mtcoding.job.dto.userPage;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.job.util.DateUtil;

import java.sql.Timestamp;

@Getter @Setter
public class UserPageApplyDto {
    private UserMyApplyReqDto userApply;
    private ApplyResumeDto applyResume;
    private RecruitmentPostListRespDto recruitmentList;

    @Getter
    @Setter
    public static class UserMyApplyReqDto {
        private Integer id;
        private Integer enterpriseId;
        private Integer recruitmentPostId;
        private String enterpriseName;
        private String title;
        private String sector;
        private Integer applyResumeId;
        private Boolean result;
        private Timestamp createdAt;
    }

    @Setter
    @Getter
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