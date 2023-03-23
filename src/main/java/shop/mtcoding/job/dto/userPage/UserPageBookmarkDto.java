package shop.mtcoding.job.dto.userPage;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.job.util.DateUtil;

@Setter
@Getter
public class UserPageBookmarkDto {
    private BookmarkReqDto bookmark;
    private RecruitmentPostListRespDto recruitmentList;

    @Setter
    @Getter
    public static class BookmarkReqDto {
        private Integer id;
        private Integer recruitmentId;
        private String enterpriseName;
        private String title;
        private String sector;
        private String deadline;
        private long diffDays;

        public void calculateDiffDays() { // D-Day 계산하는 메서드 추가
            diffDays = DateUtil.deadline(deadline);
        }

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
        private long diffDays;
        private Timestamp createdAt;

        public void calculateDiffDays() { // D-Day 계산하는 메서드 추가
            diffDays = DateUtil.deadline(deadline);
        }
    }
}
