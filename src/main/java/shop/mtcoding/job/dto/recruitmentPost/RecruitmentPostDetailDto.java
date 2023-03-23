package shop.mtcoding.job.dto.recruitmentPost;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.job.dto.bookmark.BookmartRespDto;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentPostRespDto.RecruitmentPostDetailRespDto;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentPostRespDto.RecruitmentPostSkillRespDto;
import shop.mtcoding.job.model.resume.Resume;

@Getter
@Setter
public class RecruitmentPostDetailDto {
    private BookmartRespDto bookmartRespDto;
    private RecruitmentPostDetailRespDto recruitmentPostDto;
    private List<RecruitmentPostSkillRespDto> recruitmentPostSkillRespDtos;
    private List<Resume> resumeDtos;

    public RecruitmentPostDetailDto(BookmartRespDto bookmartRespDto, RecruitmentPostDetailRespDto recruitmentPostDto,
            List<RecruitmentPostSkillRespDto> recruitmentPostSkillRespDtos, List<Resume> resumeDtos) {

        this.bookmartRespDto = bookmartRespDto;
        this.recruitmentPostDto = recruitmentPostDto;
        this.recruitmentPostSkillRespDtos = recruitmentPostSkillRespDtos;
        this.resumeDtos = resumeDtos;
    }
}
