package com.example.do_phu_song4g.demo.presentation;

import com.example.do_phu_song4g.demo.usecase.KhuVucService;
import com.example.do_phu_song4g.demo.usecase.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/khu-vuc")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KhuVucController {
    private final KhuVucService khuVucService;

    @GetMapping
    public ResponseEntity<Page<KhuVucDTO>> getAllKhuVuc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(khuVucService.getAllKhuVuc(page, size));
    }

    @GetMapping("/tinh/{tinhThanh}")
    public ResponseEntity<List<KhuVucDTO>> getKhuVucByTinh(@PathVariable String tinhThanh) {
        return ResponseEntity.ok(khuVucService.getKhuVucByTinh(tinhThanh));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<KhuVucDTO>> createKhuVuc(@RequestBody KhuVucDTO dto) {
        KhuVucDTO created = khuVucService.createKhuVuc(dto);
        return ResponseEntity.ok(ApiResponse.success("Tạo khu vực thành công", created));
    }
}