package shop.mtcoding.job.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.config.aop.UserId;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.dto.resume.SaveResumeDto;
import shop.mtcoding.job.dto.resume.UpdateResumeDto;
import shop.mtcoding.job.model.resume.Resume;
import shop.mtcoding.job.model.resume.ResumeRepository;
import shop.mtcoding.job.service.ResumeService;

@RequiredArgsConstructor
@Controller
public class ResumeController {
    private final ResumeService resumeService;

    private final ResumeRepository resumeRepository;

    @GetMapping("/resumes")
    public @ResponseBody ResponseEntity<?> resumeList(@UserId int principalId) {
        List<Resume> resumeList = resumeRepository.findByUserId(principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 목록 보기 완료", resumeList), HttpStatus.OK);
    }

    @PostMapping("/resume")
    public @ResponseBody ResponseEntity<?> save(@RequestBody SaveResumeDto saveResumeDto, @UserId int principalId) {
        resumeService.이력서쓰기(saveResumeDto, principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 쓰기 성공", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/resume/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id, @UserId int principalId) {
        resumeService.이력서삭제(id, principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 삭제 성공", null), HttpStatus.OK);
    }

    @PutMapping("/resume/{id}")
    public @ResponseBody ResponseEntity<?> update(@PathVariable int id,
            @RequestBody UpdateResumeDto updateResumeDto, @UserId int principalId) throws Exception {
        resumeService.이력서수정(id, updateResumeDto, principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 수정 성공", null), HttpStatus.OK);
    }
}
