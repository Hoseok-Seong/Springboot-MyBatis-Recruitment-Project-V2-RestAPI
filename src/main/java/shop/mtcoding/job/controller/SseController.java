package shop.mtcoding.job.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.dto.apply.ApplyRespDto.NotifyListRespDto;
import shop.mtcoding.job.model.apply.ApplyRepository;
import shop.mtcoding.job.model.user.User;

@RequiredArgsConstructor
@Controller
public class SseController {
    private final ApplyRepository applyRepository;

    private final HttpSession session;

    @GetMapping(value = "/notify", produces = "text/event-stream")
    public ResponseEntity<?> notify(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return ResponseEntity.badRequest().build();
        }

        SseEmitter emitter = new SseEmitter();
        CompletableFuture.runAsync(() -> {
            try {
                List<NotifyListRespDto> notifies = applyRepository.findNotifyByUserId(principal.getId());
                for (NotifyListRespDto notify : notifies) {
                    if (notify.getNotify()) {
                        emitter.send("마이페이지-지원현황에서 결과를 확인해주세요");
                        applyRepository.updateNotifyById(principal.getId(), false);
                    }
                }
            } catch (Exception e) {
                emitter.completeWithError(e);
            } finally {
                emitter.complete();
            }
        });
        return new ResponseEntity<>(new ResponseDto<>(1, "인증 성공", emitter), HttpStatus.OK);
    }
}
