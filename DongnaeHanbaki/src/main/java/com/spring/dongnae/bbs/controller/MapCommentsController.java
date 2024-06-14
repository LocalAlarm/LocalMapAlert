package com.spring.dongnae.bbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.bbs.MapCommentsService;
import com.spring.dongnae.bbs.MapCommentsVO;

@RestController
public class MapCommentsController {

    private final MapCommentsService mapCommentsService;

    @Autowired
    public MapCommentsController(MapCommentsService mapCommentsService) {
        this.mapCommentsService = mapCommentsService;
        System.out.println("====== MapCommentsController() 객체 생성 ======");
    }

    @PostMapping(value = "/insertComment", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> insertComment(@ModelAttribute MapCommentsVO mapCommentsVO) {
        try {
            int insertCount = mapCommentsService.insertComment(mapCommentsVO);
            if (insertCount > 0) {
                return ResponseEntity.ok("댓글이 성공적으로 등록되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 등록에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 등록 중 오류가 발생했습니다.");
        }
    }
}
