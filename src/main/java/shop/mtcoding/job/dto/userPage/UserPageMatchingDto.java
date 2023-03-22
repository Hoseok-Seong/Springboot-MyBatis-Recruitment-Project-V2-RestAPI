package shop.mtcoding.job.dto.userPage;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.job.util.DateUtil;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter @Getter
public class UserPageMatchingDto {
    private UserMatchingDto userMatching;
    private RecruitmentPostListRespDto recruitment;
//    private List<UserSkillDto> userSkill;

    @Setter @Getter
    public static class UserMatchingDto{
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
            private Integer userId;
            private List<Integer> skill;
            private List<String> skillString;


//        public String getSkill() {
//            Map<Integer, String> skillMap = new HashMap<>();
//            skillMap.put(1, "Java");
//            skillMap.put(2, "HTML");
//            skillMap.put(3, "JavaScript");
//            skillMap.put(4, "VueJS");
//            skillMap.put(5, "CSS");
//            skillMap.put(6, "Node.js");
//            skillMap.put(7, "React");
//            skillMap.put(8, "ReactJS");
//            skillMap.put(9, "Typescript");
//            skillMap.put(10, "Zustand");
//            skillMap.put(11, "AWS");
//
//            return skillMap.get(skill);
//        }

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
        private List<RecruitmentSkillDto> skill;
        private Long diffDays;
        private Timestamp createdAt;

        public void calculateDiffDays() { // D-Day 계산하는 메서드 추가
            diffDays = DateUtil.deadline(deadline);
        }
    }

    @Getter
    @Setter
    public static class RecruitmentSkillDto {
        private Integer recruitmentId;
        private Integer skill;
    }


}
