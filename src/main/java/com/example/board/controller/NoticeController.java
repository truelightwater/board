package com.example.board.controller;

import com.example.board.model.NoticeModel;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiIgnore
@RestController
public class NoticeController {

    @GetMapping("/api/notice")
    public List<NoticeModel> notice() {
        List<NoticeModel> noticeList = new ArrayList<>();

        noticeList.add(NoticeModel.builder()
                .id(1L)
                .title("첫번째 공지사항입니다.")
                .regDate(LocalDateTime.now())
                .build());

        noticeList.add(NoticeModel.builder()
                .id(2L)
                .title("두번째 공지사항입니다.")
                .regDate(LocalDateTime.now())
                .build());

        return noticeList;
    }

    @GetMapping("/api/notice/count")
    public int noticeCount() {
        return 2;
    }

/*    @PostMapping("/api/notice")
    public NoticeModel addNotice(@RequestParam String title, @RequestParam String content) {
        return NoticeModel.builder()
                .id(1L)
                .title(title)
                .content(content)
                .regDate(LocalDateTime.now())
                .build();
    }*/

    @PostMapping("/api/notice")
    public NoticeModel addNotice(@RequestBody NoticeModel noticeModel) {
        noticeModel.setId(3L);
        noticeModel.setRegDate(LocalDateTime.now());
        return noticeModel;
    }
}
