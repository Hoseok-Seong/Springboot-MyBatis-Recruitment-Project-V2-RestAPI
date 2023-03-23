package shop.mtcoding.job.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.dto.userPage.UserPageApplyDto;
import shop.mtcoding.job.dto.userPage.UserPageBookmarkDto;
import shop.mtcoding.job.dto.userPage.UserPageMatchingDto;
import shop.mtcoding.job.handler.exception.CustomException;
import shop.mtcoding.job.model.apply.ApplyRepository;
import shop.mtcoding.job.model.bookmark.BookmarkRepository;
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
        for (UserPageApplyDto post : userPageDtos) {
            post.getRecruitmentList().calculateDiffDays(); // D-Day 계산
        }

        return new ResponseEntity<>(new ResponseDto<>(1, "UserPage", userPageDtos), HttpStatus.OK);
    }

    @GetMapping("/mymatching")
    public @ResponseBody ResponseEntity<?> mymatching() throws Exception {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("회원 인증이 되지 않았습니다. 로그인을 해주세요.", HttpStatus.UNAUTHORIZED);
        }

        List<UserPageMatchingDto> posts = userSkillRepository.userJoinRecruitmentWithMatching(principal.getId());

        for (UserPageMatchingDto post : posts) {
            List<String> skills = Convert.ete_test(post.getUserMatching().getUserSkillDto().getSkill());
            post.getUserMatching().getUserSkillDto().setSkillString(skills);
            post.getRecruitment().calculateDiffDays();
        }

        return new ResponseEntity<>(new ResponseDto<>(1, "매칭서비스 완료", posts), HttpStatus.OK);
    }

    @GetMapping("/mybookmark")
    public @ResponseBody ResponseEntity<?> mybookmark() {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("회원 인증이 되지 않았습니다. 로그인을 해주세요.", HttpStatus.UNAUTHORIZED);
        }
        List<UserPageBookmarkDto> posts = bookmarkRepository.BookmarkJoinRecruitOfUserPage(principal.getId());
        // d-day 계산
        for (UserPageBookmarkDto post : posts) {
            post.getRecruitmentList().calculateDiffDays(); // D-Day 계산
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "북마크 목록", posts), HttpStatus.OK);
    }
}
