package shop.mtcoding.job.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.dto.bookmark.BookmarkEntReqDto;
import shop.mtcoding.job.dto.entPage.EntPageMyApplicantRespDto;
import shop.mtcoding.job.dto.recruitmentSkill.EnterpriseMatchingDto;
import shop.mtcoding.job.handler.exception.CustomException;
import shop.mtcoding.job.model.apply.ApplyRepository;
import shop.mtcoding.job.model.applyResume.ApplyResumeRepository;
import shop.mtcoding.job.model.bookmark.BookmarkRepository;
import shop.mtcoding.job.model.enterprise.Enterprise;
import shop.mtcoding.job.model.recruitmentSkill.RecruitmentSkillRepository;

@RequiredArgsConstructor
@Controller
public class EntPageController {
    private final HttpSession session;

    private final ApplyRepository applyRepository;

    private final RecruitmentSkillRepository recruitmentSkillRepository;

    private final BookmarkRepository bookmarkRepository;

    @GetMapping("/myapplicant")
    public @ResponseBody ResponseEntity<?> myapplicant() {
        Enterprise principalEnt = (Enterprise) session.getAttribute("principalEnt");
        if (principalEnt == null) {
            throw new CustomException("기업회원으로 로그인을 해주세요", HttpStatus.UNAUTHORIZED);
        }

        List<EntPageMyApplicantRespDto> myApplicantRespDtos = applyRepository
                .findByEnterpriseIdJoinApplyResume(principalEnt.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "인증 성공", myApplicantRespDtos), HttpStatus.OK);
    }

    @GetMapping("/myrecommend")
    public @ResponseBody ResponseEntity<?> myrecommend(Model model) {
        Enterprise principalEnt = (Enterprise) session.getAttribute("principalEnt");
        if (principalEnt == null) {
            throw new CustomException("기업회원으로 로그인을 해주세요", HttpStatus.UNAUTHORIZED);
        }

        if (principalEnt != null) {
            List<EnterpriseMatchingDto> enterpriseMatchingDto = recruitmentSkillRepository
                    .enterpriseMatching(principalEnt.getId());

            model.addAttribute("enterpriseMatching", enterpriseMatchingDto);
        }

        return "entpage/myrecommend";
    }

    @GetMapping("/mybookmarkEnt")
    public String mybookmark(Model model) {
        Enterprise principalEnt = (Enterprise) session.getAttribute("principalEnt");
        if (principalEnt == null) {
            throw new CustomException("기업회원으로 로그인을 해주세요", HttpStatus.UNAUTHORIZED);
        }
        if (principalEnt != null) {
            List<BookmarkEntReqDto> bookmarkEntReqDto = bookmarkRepository.findByEnterpriseId(principalEnt.getId());
            model.addAttribute("bookmarkDto", bookmarkEntReqDto);
        }

        return "entpage/mybookmarkEnt";
    }
}
