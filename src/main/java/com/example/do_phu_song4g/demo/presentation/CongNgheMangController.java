package com.example.do_phu_song4g.demo.presentation;

import com.example.do_phu_song4g.demo.usecase.CongNgheMangService;
import com.example.do_phu_song4g.demo.usecase.dto.CongNgheMangDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cong-nghe-mang")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CongNgheMangController {
    private final CongNgheMangService congNgheMangService;

    @GetMapping
    public ResponseEntity<List<CongNgheMangDTO>> getAllCongNghe() {
        return ResponseEntity.ok(congNgheMangService.getAllCongNghe());
    }
}