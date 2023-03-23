package shop.mtcoding.job.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.model.user.User;
import shop.mtcoding.job.service.BookmarkService;

@RequiredArgsConstructor
@Controller
public class BookmarkController {
    private final HttpSession session;

    private final BookmarkService bookmarkService;

    @PostMapping("/bookmark/{id}")
    public ResponseEntity<?> bookmark(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");

        int bookmarkId = bookmarkService.북마크하기(id, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "북마크 성공", bookmarkId), HttpStatus.OK);
    }

    @DeleteMapping("/bookmark/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");

        bookmarkService.북마크삭제(id, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "북마크 삭제 성공", null), HttpStatus.OK);
    }
}