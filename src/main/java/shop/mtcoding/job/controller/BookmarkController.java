package shop.mtcoding.job.controller;

import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.handler.exception.CustomApiException;
import shop.mtcoding.job.model.user.User;
import shop.mtcoding.job.service.BookmarkService;

@RequiredArgsConstructor
@RestController
public class BookmarkController {

    private final HttpSession session;

    private final BookmarkService bookmarkService;

    @PostMapping("/bookmark/{id}")
    public @ResponseBody ResponseEntity<?> bookmark(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");

        if (principal == null) {
            throw new CustomApiException("개인회원으로 로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
        }

        int bookmarkId = bookmarkService.북마크하기(id, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "북마크 성공", bookmarkId), HttpStatus.OK);
    }

    @DeleteMapping("/bookmark/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");

        if (principal == null) {
            throw new CustomApiException("회원 인증이 실패했습니다", HttpStatus.UNAUTHORIZED);
        }

        bookmarkService.북마크삭제(id, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "북마크 삭제 성공", null), HttpStatus.OK);
    }
}