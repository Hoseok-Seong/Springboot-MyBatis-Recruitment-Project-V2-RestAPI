package shop.mtcoding.job.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserSkillController {

    private final UserService userService;

    @PostMapping("/user/skill")
    public ResponseEntity<?> skill(Integer userId, @RequestParam("skill") List<Integer> skill) {

        userService.유저스킬추가(userId, skill);

        return new ResponseEntity<>(new ResponseDto<>(1, "유저스킬 추가", null), HttpStatus.OK);
    }
}
