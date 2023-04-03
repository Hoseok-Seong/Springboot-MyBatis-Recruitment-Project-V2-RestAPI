package shop.mtcoding.job.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.config.aop.UserId;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.dto.userPage.UserPageApplyDto;
import shop.mtcoding.job.dto.userPage.UserPageBookmarkDto;
import shop.mtcoding.job.dto.userPage.UserPageMatchingDto;
import shop.mtcoding.job.model.apply.ApplyRepository;
import shop.mtcoding.job.model.bookmark.BookmarkRepository;
import shop.mtcoding.job.model.userSkill.UserSkillRepository;
import shop.mtcoding.job.util.Convert;

@RequiredArgsConstructor
@Controller
public class UserPageController {
    private final ApplyRepository applyRepository;

    private final UserSkillRepository userSkillRepository;

    private final BookmarkRepository bookmarkRepository;

    @GetMapping("/myapplications")
    public @ResponseBody ResponseEntity<?> mypage(@UserId Integer principalId) {
        List<UserPageApplyDto> userPageDtos = applyRepository.findAllApply(principalId);
        for (UserPageApplyDto post : userPageDtos) {
            post.getRecruitmentList().calculateDiffDays(); // D-Day 계산
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "성공", userPageDtos), HttpStatus.OK);
    }

    @GetMapping("/mymatches")
    public @ResponseBody ResponseEntity<?> mymatching(@UserId Integer principalId) throws Exception {
        List<UserPageMatchingDto> posts = userSkillRepository.userJoinRecruitmentWithMatching(principalId);

        for (UserPageMatchingDto post : posts) {
            List<String> skills = Convert.skillMapping(post.getUserMatching().getUserSkillDto().getSkill());
            post.getUserMatching().getUserSkillDto().setSkillString(skills);
            post.getRecruitment().calculateDiffDays();
        }

        return new ResponseEntity<>(new ResponseDto<>(1, "성공", posts), HttpStatus.OK);
    }

    @GetMapping("/mybookmarks")
    public @ResponseBody ResponseEntity<?> mybookmark(@UserId Integer principalId) {
        List<UserPageBookmarkDto> posts = bookmarkRepository.BookmarkJoinRecruitOfUserPage(principalId);
        // d-day 계산
        for (UserPageBookmarkDto post : posts) {
            post.getRecruitmentList().calculateDiffDays();
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "성공", posts), HttpStatus.OK);
    }
}
