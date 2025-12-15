package com.example.do_phu_song4g.demo.presentation;

import com.example.do_phu_song4g.demo.usecase.TramPhatSongService;
import com.example.do_phu_song4g.demo.usecase.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tram-phat-song")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TramPhatSongController {
    private final TramPhatSongService tramPhatSongService;

    @GetMapping
    public ResponseEntity<Page<TramPhatSongDTO>> getAllTram(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer nhaMang,
            @RequestParam(required = false) Integer congNghe,
            @RequestParam(required = false) String tinhThanh) {
        
        Page<TramPhatSongDTO> result = tramPhatSongService.getAllTram(
                page, size, search, nhaMang, congNghe, tinhThanh);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TramPhatSongDTO>> getTramById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(tramPhatSongService.getTramById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TramPhatSongDTO>> createTram(@RequestBody CreateTramRequest request) {
        TramPhatSongDTO created = tramPhatSongService.createTram(request);
        return ResponseEntity.ok(ApiResponse.success("Tạo trạm thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TramPhatSongDTO>> updateTram(
            @PathVariable Integer id, 
            @RequestBody CreateTramRequest request) {
        TramPhatSongDTO updated = tramPhatSongService.updateTram(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTram(@PathVariable Integer id) {
        tramPhatSongService.deleteTram(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa thành công", null));
    }

    @GetMapping("/gan-vi-tri")
    public ResponseEntity<List<TramPhatSongDTO>> getTramGanViTri(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "5000") Integer banKinh) {
        return ResponseEntity.ok(tramPhatSongService.getTramGanViTri(lat, lng, banKinh));
    }
}