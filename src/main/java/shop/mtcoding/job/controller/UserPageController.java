package shop.mtcoding.job.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentPostRespDto.RecruitmentPostListRespDto;
import shop.mtcoding.job.dto.userPage.UserPageApplyDto;
import shop.mtcoding.job.dto.userPage.UserPageBookmarkDto;
import shop.mtcoding.job.dto.userPage.UserPageMatchingDto;
import shop.mtcoding.job.dto.userSkill.UserMatchingDto;
import shop.mtcoding.job.handler.exception.CustomException;
import shop.mtcoding.job.model.apply.ApplyRepository;
import shop.mtcoding.job.model.applyResume.ApplyResumeRepository;
import shop.mtcoding.job.model.bookmark.BookmarkRepository;
import shop.mtcoding.job.model.recruitmentPost.RecruitmentPostRepository;
import shop.mtcoding.job.model.user.User;
import shop.mtcoding.job.model.userSkill.UserSkillRepository;
import shop.mtcoding.job.util.Convert;

@RequiredArgsConstructor
@RestController
public class UserPageController {
    private final HttpSession session;

    private final ApplyRepository applyRepository;

    private final UserSkillRepository userSkillRepository;

    private final BookmarkRepository bookmarkRepository;

    @GetMapping("/myapply")
    public @ResponseBody ResponseEntity<?> mypage() {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("회원 인증이 되지 않았습니다. 로그인을 해주세요.", HttpStatus.UNAUTHORIZED);
        }

        List<UserPageApplyDto> userPageDtos = applyRepository.findAllApply(principal.getId());
//        // d-day 계산
//        for (RecruitmentPostListRespDto post : posts) {
//            post.calculateDiffDays(); // D-Day 계산
//        }

        return new ResponseEntity<>(new ResponseDto<>(1, "UserPage" ,userPageDtos), HttpStatus.OK);
    }

    @GetMapping("/mymatching")
    public @ResponseBody ResponseEntity<?> mymatching(Model model) throws Exception {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("회원 인증이 되지 않았습니다. 로그인을 해주세요.", HttpStatus.UNAUTHORIZED);
        }

        List<UserPageMatchingDto> posts = userSkillRepository.userJoinRecruitmentWithMatching(principal.getId());
        List<String> skills = Convert.ete_test(posts.get(0).getUserMatching().getUserSkillDto().getSkill());
        posts.get(0).getUserMatching().getUserSkillDto().setSkillString(skills);


        // d-day 계산
//        for (RecruitmentPostListRespDto post : posts) {
//            post.calculateDiffDays(); // D-Day 계산
//        }

        return new ResponseEntity<>(new ResponseDto<>(1, "매칭서비스 완료", posts), HttpStatus.OK);
    }

    @GetMapping("/mybookmark")
    public @ResponseBody ResponseEntity mybookmark() {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("회원 인증이 되지 않았습니다. 로그인을 해주세요.", HttpStatus.UNAUTHORIZED);
        }
        List<UserPageBookmarkDto> posts = bookmarkRepository.BookmarkJoinRecruitOfUserPage(principal.getId());
        // d-day 계산
//        for (RecruitmentPostListRespDto post : posts) {
//            post.calculateDiffDays(); // D-Day 계산
//        }

        return new ResponseEntity<>(new ResponseDto<>(1, "북마크 목록", posts), HttpStatus.OK);
    }
}
