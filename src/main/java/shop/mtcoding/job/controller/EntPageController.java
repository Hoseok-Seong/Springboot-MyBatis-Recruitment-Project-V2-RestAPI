package shop.mtcoding.job.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.config.aop.EntId;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.dto.entPage.EntPageMyApplicantRespDto;
import shop.mtcoding.job.dto.entPage.EntPageMyBookmarkRespDto;
import shop.mtcoding.job.dto.entPage.EntPageMyRecommendRespDto;
import shop.mtcoding.job.model.apply.ApplyRepository;
import shop.mtcoding.job.model.bookmark.BookmarkRepository;
import shop.mtcoding.job.model.recruitmentSkill.RecruitmentSkillRepository;

@RequiredArgsConstructor
@Controller
public class EntPageController {

    private final ApplyRepository applyRepository;

    private final RecruitmentSkillRepository recruitmentSkillRepository;

    private final BookmarkRepository bookmarkRepository;

    @GetMapping("/myapplicants")
    public @ResponseBody ResponseEntity<?> myapplicant(@EntId Integer principalId) {
        List<EntPageMyApplicantRespDto> myApplicantRespDtos = applyRepository
                .findByEnterpriseIdJoinApplyResume(principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "인증 성공", myApplicantRespDtos), HttpStatus.OK);
    }

    @GetMapping("/myrecommends")
    public @ResponseBody ResponseEntity<?> myrecommend(@EntId Integer principalId) {
        List<EntPageMyRecommendRespDto> myrecommendRespDto = recruitmentSkillRepository
                .enterpriseMatching(principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "인증 성공", myrecommendRespDto), HttpStatus.OK);
    }

    @GetMapping("/mybookmarksEnt")
    public @ResponseBody ResponseEntity<?> mybookmark(@EntId Integer principalId) {
        List<EntPageMyBookmarkRespDto> mybookmarkEntRespDto = bookmarkRepository
                .findByEnterpriseId(principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "인증 성공", mybookmarkEntRespDto), HttpStatus.OK);
    }
}
