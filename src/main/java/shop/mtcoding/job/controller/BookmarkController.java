package shop.mtcoding.job.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.job.config.aop.UserId;
import shop.mtcoding.job.dto.ResponseDto;
import shop.mtcoding.job.service.BookmarkService;

@RequiredArgsConstructor
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/bookmarks/{id}")
    public @ResponseBody ResponseEntity<?> bookmark(@PathVariable int id, @UserId Integer principalId) {
        int bookmarkId = bookmarkService.북마크하기(id, principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "북마크 성공", bookmarkId), HttpStatus.OK);
    }

    @DeleteMapping("/bookmarks/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id, @UserId Integer principalId) {
        bookmarkService.북마크삭제(id, principalId);
        return new ResponseEntity<>(new ResponseDto<>(1, "북마크 삭제 성공", null), HttpStatus.OK);
    }
}