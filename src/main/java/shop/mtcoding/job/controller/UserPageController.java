package shop.mtcoding.job.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
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
import shop.mtcoding.job.dto.userSkill.UserMatchingDto;
import shop.mtcoding.job.handler.exception.CustomException;
import shop.mtcoding.job.model.apply.ApplyRepository;
import shop.mtcoding.job.model.applyResume.ApplyResumeRepository;
import shop.mtcoding.job.model.bookmark.BookmarkRepository;
import shop.mtcoding.job.model.recruitmentPost.RecruitmentPostRepository;
import shop.mtcoding.job.model.user.User;
import shop.mtcoding.job.model.userSkill.UserSkillRepository;

@RequiredArgsConstructor
@RestController
public class UserPageController {
    private final HttpSession session;

    private final ApplyRepository applyRepository;

    private final ApplyResumeRepository applyResumeRepository;

    private final RecruitmentPostRepository recruitmentPostRepository;

    private final UserSkillRepository userSkillRepository;

    private final BookmarkRepository bookmarkRepository;

    @GetMapping("/myapply")
    public ResponseEntity<?> mypage() {
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
    public ResponseEntity<?> mymatching(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("회원 인증이 되지 않았습니다. 로그인을 해주세요.", HttpStatus.UNAUTHORIZED);
        }
        if (principal != null) {
            List<UserMatchingDto> userMatchingDto = userSkillRepository.userMatching(principal.getId());
            model.addAttribute("userMatching", userMatchingDto);
        }

        List<RecruitmentPostListRespDto> posts = recruitmentPostRepository.findByPost();
        // d-day 계산
        for (RecruitmentPostListRespDto post : posts) {
            post.calculateDiffDays(); // D-Day 계산
        }

        model.addAttribute("Posts", posts);

        Map<Integer, String> skillMap = new HashMap<>();
        skillMap.put(1, "Java");
        skillMap.put(2, "HTML");
        skillMap.put(3, "JavaScript");
        skillMap.put(4, "VueJS");
        skillMap.put(5, "CSS");
        skillMap.put(6, "Node.js");
        skillMap.put(7, "React");
        skillMap.put(8, "ReactJS");
        skillMap.put(9, "Typescript");
        skillMap.put(10, "Zustand");
        skillMap.put(11, "AWS");
        model.addAttribute("skillMap", skillMap);
        model.addAttribute("userSkillDtos", userSkillRepository.findByUserId(principal.getId()));

        return new ResponseEntity<>(new ResponseDto<>(1, "매칭서비스 완료", null), HttpStatus.OK);
    }

    @GetMapping("/mybookmark")
    public @ResponseBody ResponseEntity mybookmark() {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("회원 인증이 되지 않았습니다. 로그인을 해주세요.", HttpStatus.UNAUTHORIZED);
        }
//            List<BookmarkReqDto> bookmarkDto = bookmarkRepository.findByUserId(principal.getId());
//            model.addAttribute("bookmarkDto", bookmarkDto);

        List<UserPageBookmarkDto> posts = bookmarkRepository.BookmarkJoinRecruitOfUserPage(principal.getId());
        // d-day 계산
//        for (RecruitmentPostListRespDto post : posts) {
//            post.calculateDiffDays(); // D-Day 계산
//        }

//        model.addAttribute("Posts", posts);

        return new ResponseEntity<>(new ResponseDto<>(1, "북마크 목록", posts), HttpStatus.OK);
    }
}
