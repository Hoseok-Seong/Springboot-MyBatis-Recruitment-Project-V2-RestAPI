package shop.mtcoding.job.dto.entPage;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntPageMyBookmarkRespDto {
    private BookmarkDto bookmark;
    private UserDto user;
    private RecruitmentPostDto recruitmentPost;

    @Getter
    @Setter
    public static class BookmarkDto {
        private Integer id;
        private Integer userId;
        private Integer recruitmentId;
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
}
