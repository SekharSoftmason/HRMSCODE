package com.example.DhanaBackEnd.Controller;

import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.DTO.NoticeDto;
import com.example.DhanaBackEnd.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/notice")
public class NoticeController {
        @Autowired
        private NoticeService noticeService;
        @PostMapping
        public ResponseGlobal<?> createNotice(@RequestBody NoticeDto dto) {
            return noticeService.createNotice(dto);
        }
        @GetMapping
        public ResponseGlobal<?> getAllNotices(@RequestHeader("Authorization") String token) {
            return noticeService.getAllNotices(token);
        }
        @GetMapping("/{id}")
        public ResponseGlobal<?> getNoticeById(@PathVariable Long id,@RequestHeader("Authorization") String token) {
            return noticeService.getNoticeById(id,token);
        }
        @PutMapping("/{id}")
        public ResponseGlobal<?> updateNotice(@PathVariable Long id, @RequestBody NoticeDto dto,@RequestHeader("Authorization") String token) {
            return noticeService.updateNotice(id, dto,token);
        }
        @DeleteMapping("/{id}")
        public ResponseGlobal<?> deleteNotice(@PathVariable Long id ,@RequestHeader("Authorization") String token) {
            return noticeService.deleteNotice(id,token);
        }
    }

