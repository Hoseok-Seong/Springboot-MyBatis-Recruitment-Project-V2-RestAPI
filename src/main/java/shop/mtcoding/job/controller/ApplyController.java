package shop.mtcoding.job.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.config.aop.EntId;
import shop.mtcoding.job.config.aop.UserId;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.dto.apply.ApplyReqDto.InsertApplyReqDto;
import shop.mtcoding.job.dto.apply.ApplyReqDto.UpdateApplicantResultReqDto;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentPostRespDto.RecruitmentPostDetailRespDto;
import shop.mtcoding.job.handler.exception.CustomApiException;
import shop.mtcoding.job.model.recruitmentPost.RecruitmentPostRepository;
import shop.mtcoding.job.service.ApplyService;
import shop.mtcoding.job.util.DateUtil;

@RequiredArgsConstructor
@Controller
public class ApplyController {
    private final ApplyService applyService;

    private final RecruitmentPostRepository recruitmentPostRepository;

    @PostMapping("/apply/{id}")
    public @ResponseBody ResponseEntity<?> insertApply(@RequestBody InsertApplyReqDto insertApplyReqDto,
            @PathVariable int id, @UserId int principalId) {

        RecruitmentPostDetailRespDto recruitmentPostDto = recruitmentPostRepository.findByIdWithEnterpriseId(
                principalId);

        // d-day 계산
        long diffDays = DateUtil.deadline(recruitmentPostDto.getDeadline());

        if (diffDays < 0) {
            throw new CustomApiException("이력서 제출기간이 지났습니다", HttpStatus.BAD_REQUEST);
        }

        applyService.이력서제출(insertApplyReqDto, principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 제출 성공", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/apply/{id}")
    public @ResponseBody ResponseEntity<?> deleteApply(@PathVariable int id, @UserId int principalId) {
        applyService.이력서제출취소(id, principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "지원서 삭제 성공", null), HttpStatus.OK);
    }

    @PutMapping("/apply/result/{id}")
    public @ResponseBody ResponseEntity<?> updateResult(
            @RequestBody UpdateApplicantResultReqDto updateApplicantResultReqDto, @PathVariable int id,
            @EntId int principalId) {
        applyService.합격불합격(id, updateApplicantResultReqDto, principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "처리 성공", null), HttpStatus.OK);
    }

}
