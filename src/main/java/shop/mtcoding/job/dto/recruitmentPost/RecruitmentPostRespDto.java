package shop.mtcoding.job.dto.recruitmentPost;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.job.util.DateUtil;

public class RecruitmentPostRespDto {

    @Setter
    @Getter
    public static class RecruitmentPostDetailRespDto {
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
        private String enterpriseName;
        private String deadline;
        @JsonIgnore
        private Timestamp createdAt;

        private long diffDays;

        public void calculateDiffDays() { // D-Day 계산하는 메서드 추가
            diffDays = DateUtil.deadline(deadline);
        }
    }

    @Setter
    @Getter
    public static class RecruitmentPostListRespDto {
        private Integer id;
        private Integer enterpriseId;
        private String title;
        private String career;
        private String education;
        private String pay;
        private String sector;
        private String position;
        private String address;
        private String enterpriseLogo;
        private String deadline;
        private Timestamp createdAt;
        private EnterpriseDto enterprise;
        private long diffDays;

        public void calculateDiffDays() { // D-Day 계산하는 메서드 추가
            diffDays = DateUtil.deadline(deadline);
        }

        @Getter
        @Setter
        public static class EnterpriseDto {
            private Integer id;
            private String enterpriseName;
        }
    }

    @Getter
    @Setter
    public static class RecruitmentPostSearchRespDto {
        private Integer id;
        private String title;
        private String address;
        private String enterpriseName;
        private String position;
        private String enterpriseLogo;
        private String searchString;
        private String deadline;
        private long diffDays;
        private Timestamp createdAt;

        public void calculateDiffDays() { // D-Day 계산하는 메서드 추가
            diffDays = DateUtil.deadline(deadline);
        }
    }

    @Getter
    @Setter
    public static class RecruitmentPostSkillRespDto {
        private int id;
        private int recruitmentId;
        private int skill;

        public String getSkill() {
            Map<Integer, String> skillMap = new HashMap<>();
            skillMap.put(1, "Java");
            skillMap.put(2, "HTML");
            skillMap.put(3, "JavaScript");
            skillMap.put(4, "VueJS");
            skillMap.put(5, "CSS");
            skillMap.put(6, "Node.js");
            skillMap.put(7, "React");
            skillMap.put(8, "ReactJS");
            skillMap.put(9, "Typescript");
            skillMap.put(10, "Zustand");
            skillMap.put(11, "AWS");
            return skillMap.get(skill);
        }
    }

    @Getter
    @Setter
    public static class RecruitmentPostCategoryRespDto {
        private Integer id;
        private String title;
        private String address;
        private String enterpriseName;
        private String enterpriseLogo;
        private String career;
        private String education;
        private String sector;
        private String position;
        private String skill;
        private String deadline;
        private long diffDays;
        private Timestamp createdAt;

        public void calculateDiffDays() { // D-Day 계산하는 메서드 추가
            diffDays = DateUtil.deadline(deadline);
        }
    }
}