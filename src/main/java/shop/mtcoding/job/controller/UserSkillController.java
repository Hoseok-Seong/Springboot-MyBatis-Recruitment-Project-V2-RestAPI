package shop.mtcoding.job.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.handler.exception.CustomException;
import shop.mtcoding.job.model.userSkill.UserSkillRepository;

@RequiredArgsConstructor
@Controller
public class UserSkillController {
    private final UserSkillRepository userSkillRepository;

    @PostMapping("/user/skill")
    public String skill(int userId, @RequestParam("skill") List<Integer> skill) {
        try {
            for (Integer checkSkill : skill) {
                int result = userSkillRepository.insert(userId, checkSkill);
                if (result != 1) {
                    throw new CustomException("실패");
                }
            }
        } catch (Exception e) {
            throw new CustomException("skill inset 실패");
        }
        return "redirect:/";
    }
}
