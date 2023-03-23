package shop.mtcoding.job.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.dto.recruitmentPost.RecruitmentPostRespDto.RecruitmentPostListRespDto;
import shop.mtcoding.job.service.MainService;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MainService mainService;

    @GetMapping({ "/", "/main" })
    public @ResponseBody ResponseEntity<?> main() {

        List<RecruitmentPostListRespDto> posts = mainService.게시글목록보기();

        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 목록", posts), HttpStatus.OK);
    }
}