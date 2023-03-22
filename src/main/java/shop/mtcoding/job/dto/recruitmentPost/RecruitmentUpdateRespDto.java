package shop.mtcoding.job.dto.recruitmentPost;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitmentUpdateRespDto {
    private RecruitmentPostUpdateRespDto recruitmentPost;
    private RecruitmentPostSkillUpdateRespDto recruitmentPostSkill;

    @Getter
    @Setter
    public static class RecruitmentPostUpdateRespDto {
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
    public static class RecruitmentPostSkillUpdateRespDto {
        private Integer id;
        private Integer recruitmentId;
        private Integer skill;

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
}
