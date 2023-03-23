package shop.mtcoding.job.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.dto.resume.SaveResumeDto;
import shop.mtcoding.job.dto.resume.UpdateResumeDto;
import shop.mtcoding.job.handler.exception.CustomApiException;
import shop.mtcoding.job.handler.exception.CustomException;
import shop.mtcoding.job.model.resume.Resume;
import shop.mtcoding.job.model.resume.ResumeRepository;
import shop.mtcoding.job.model.user.User;
import shop.mtcoding.job.service.ResumeService;

@RequiredArgsConstructor
@RestController
public class ResumeController {
    private final HttpSession session;

    private final ResumeService resumeService;

    private final ResumeRepository resumeRepository;

    @GetMapping("/resumes")
    public @ResponseBody ResponseEntity<?> resumeList() {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("회원 인증이 되지 않았습니다. 로그인을 해주세요.", HttpStatus.UNAUTHORIZED);
        }
        List<Resume> resumeList = resumeRepository.findByUserId(principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 목록 보기 완료", resumeList), HttpStatus.OK);
    }

    @PostMapping("/resume")
    public @ResponseBody ResponseEntity<?> save(@RequestBody SaveResumeDto saveResumeDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("회원 인증이 실패했습니다", HttpStatus.UNAUTHORIZED);
        }

        resumeService.이력서쓰기(saveResumeDto, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 쓰기 성공", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/resume/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("회원 인증이 실패했습니다", HttpStatus.UNAUTHORIZED);
        }

        resumeService.이력서삭제(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 삭제 성공", null), HttpStatus.OK);

    }

    @PutMapping("/resume/{id}")
    public @ResponseBody ResponseEntity<?> update(@PathVariable int id,
            @RequestBody UpdateResumeDto updateResumeDto) throws Exception {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("회원 인증이 실패했습니다", HttpStatus.UNAUTHORIZED);
        }

        resumeService.이력서수정(id, updateResumeDto, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 수정 성공", null), HttpStatus.OK);
    }
}
