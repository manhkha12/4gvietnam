package com.example.do_phu_song4g.demo.presentation;


import com.example.do_phu_song4g.demo.usecase.GoiCuocService;
import com.example.do_phu_song4g.demo.usecase.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goi-cuoc")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GoiCuocController {
    private final GoiCuocService goiCuocService;

    // GET /api/goi-cuoc - Lấy tất cả
    @GetMapping
    public ResponseEntity<List<GoiCuocDTO>> getAllGoiCuoc() {
        return ResponseEntity.ok(goiCuocService.getAllGoiCuoc());
    }

    // GET /api/goi-cuoc/active - Lấy gói đang áp dụng
    @GetMapping("/active")
    public ResponseEntity<List<GoiCuocDTO>> getActiveGoiCuoc() {
        return ResponseEntity.ok(goiCuocService.getActiveGoiCuoc());
    }

    // GET /api/goi-cuoc/nha-mang/{id} - Theo nhà mạng
    @GetMapping("/nha-mang/{maNhaMang}")
    public ResponseEntity<List<GoiCuocDTO>> getByNhaMang(@PathVariable Integer maNhaMang) {
        return ResponseEntity.ok(goiCuocService.getGoiCuocByNhaMang(maNhaMang));
    }

    // GET /api/goi-cuoc/nha-mang/{id}/active - Theo nhà mạng (active)
    @GetMapping("/nha-mang/{maNhaMang}/active")
    public ResponseEntity<List<GoiCuocDTO>> getActiveByNhaMang(@PathVariable Integer maNhaMang) {
        return ResponseEntity.ok(goiCuocService.getActiveGoiCuocByNhaMang(maNhaMang));
    }

    // GET /api/goi-cuoc/{id} - Chi tiết
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GoiCuocDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(goiCuocService.getGoiCuocById(id)));
    }

    // POST /api/goi-cuoc - Tạo mới
    @PostMapping
    public ResponseEntity<ApiResponse<GoiCuocDTO>> create(@RequestBody CreateGoiCuocRequest request) {
        GoiCuocDTO created = goiCuocService.createGoiCuoc(request);
        return ResponseEntity.ok(ApiResponse.success("Tạo gói cước thành công", created));
    }

    // PUT /api/goi-cuoc/{id} - Cập nhật
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GoiCuocDTO>> update(
            @PathVariable Integer id,
            @RequestBody CreateGoiCuocRequest request) {
        GoiCuocDTO updated = goiCuocService.updateGoiCuoc(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", updated));
    }

    // DELETE /api/goi-cuoc/{id} - Xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        goiCuocService.deleteGoiCuoc(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa thành công", null));
    }

    // POST /api/goi-cuoc/dang-ky - Đăng ký gói (cần userId)
    @PostMapping("/dang-ky")
    public ResponseEntity<ApiResponse<DangKyGoiCuocDTO>> dangKy(
            @RequestBody DangKyGoiCuocRequest request) {
        DangKyGoiCuocDTO result = goiCuocService.dangKyGoiCuoc(request);
        return ResponseEntity.ok(ApiResponse.success("Đăng ký thành công", result));
    }

    // GET /api/goi-cuoc/my-subscriptions/{userId} - Lịch sử của user
    @GetMapping("/my-subscriptions/{userId}")
    public ResponseEntity<List<DangKyGoiCuocDTO>> getMySubscriptions(@PathVariable Integer userId) {
        return ResponseEntity.ok(goiCuocService.getLichSuDangKyByUser(userId));
    }

    // DELETE /api/goi-cuoc/huy/{maDangKy} - Hủy đăng ký
    @DeleteMapping("/huy/{maDangKy}")
    public ResponseEntity<ApiResponse<Void>> huyDangKy(
            @PathVariable Integer maDangKy,
            @RequestParam Integer userId) {
        goiCuocService.huyDangKy(maDangKy, userId);
        return ResponseEntity.ok(ApiResponse.success("Hủy đăng ký thành công", null));
    }

    // POST /api/goi-cuoc/gia-han/{maDangKy} - Gia hạn
    @PostMapping("/gia-han/{maDangKy}")
    public ResponseEntity<ApiResponse<DangKyGoiCuocDTO>> giaHan(
            @PathVariable Integer maDangKy,
            @RequestParam Integer userId) {
        DangKyGoiCuocDTO result = goiCuocService.giaHanGoiCuoc(maDangKy, userId);
        return ResponseEntity.ok(ApiResponse.success("Gia hạn thành công", result));
    }

    // GET /api/goi-cuoc/{id}/thong-ke - Thống kê đăng ký
    @GetMapping("/{id}/thong-ke")
    public ResponseEntity<Long> getThongKe(@PathVariable Integer id) {
        return ResponseEntity.ok(goiCuocService.getSoLuongDangKy(id));
    }
}