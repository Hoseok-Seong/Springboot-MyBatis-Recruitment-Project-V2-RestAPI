package shop.mtcoding.job.dto.recruitmentPost;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentUpdateRespDto.RecruitmentPostSkillUpdateRespDto;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentUpdateRespDto.RecruitmentPostUpdateRespDto;

@Getter
@Setter
public class RecruitmentPostAndSkillUpdateRespDto {
    private RecruitmentPostUpdateRespDto recruitmentPost;
    private List<RecruitmentPostSkillUpdateRespDto> recruitmentPostSkill;

    public RecruitmentPostAndSkillUpdateRespDto(RecruitmentPostUpdateRespDto recruitmentPost,
            List<RecruitmentPostSkillUpdateRespDto> recruitmentPostSkill) {
        this.recruitmentPost = recruitmentPost;
        this.recruitmentPostSkill = recruitmentPostSkill;
    }
}
