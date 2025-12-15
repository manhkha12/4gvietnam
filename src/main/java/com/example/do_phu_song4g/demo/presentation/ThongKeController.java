package com.example.do_phu_song4g.demo.presentation;

import com.example.do_phu_song4g.demo.usecase.ThongKeService;
import com.example.do_phu_song4g.demo.usecase.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/thong-ke")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ThongKeController {
    private final ThongKeService thongKeService;

    @GetMapping("/tong-quan")
    public ResponseEntity<ThongKeTongQuanDTO> getThongKeTongQuan() {
        return ResponseEntity.ok(thongKeService.getThongKeTongQuan());
    }

    @GetMapping("/5g")
    public ResponseEntity<ThongKe5GDTO> getThongKe5G() {
        return ResponseEntity.ok(thongKeService.getThongKe5G());
    }

    @GetMapping("/top-tinh-phu-song")
    public ResponseEntity<List<TopTinhPhuSongDTO>> getTopTinhPhuSong(
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(thongKeService.getTopTinhPhuSong(limit));
    }

    @GetMapping("/heatmap")
    public ResponseEntity<List<HeatmapDataDTO>> getHeatmapData(
            @RequestParam(required = false) String congNghe) {
        return ResponseEntity.ok(thongKeService.getHeatmapData(congNghe));
    }

    @GetMapping("/so-sanh-nha-mang")
    public ResponseEntity<List<SoSanhNhaMangDTO>> soSanhNhaMang(
            @RequestParam(defaultValue = "5G") String congNghe) {
        return ResponseEntity.ok(thongKeService.soSanhNhaMang(congNghe));
    }

    @GetMapping("/khu-vuc-can-mo-rong")
    public ResponseEntity<List<Map<String, Object>>> getKhuVucCanMoRong(
            @RequestParam(defaultValue = "80") BigDecimal tyLe) {
        return ResponseEntity.ok(thongKeService.getKhuVucCanMoRong(tyLe));
    }

    @GetMapping("/phu-song")
    public ResponseEntity<List<ThongKePhuSongDTO>> getThongKePhuSong() {
        return ResponseEntity.ok(thongKeService.getThongKePhuSongTheoNhaMang());
    }
}