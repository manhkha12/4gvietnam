package com.example.do_phu_song4g.demo.usecase.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangKyGoiCuocDTO {
    private Integer maDangKy;
    private Integer maGoiCuoc;
    private Integer userId;
    private String hoTen;
        private BigDecimal giaCuoc;
    private String tenGoiCuoc;
    private String tenNhaMang;
    private String tenKhachHang;
    private String soDienThoai;
    private String email;
    private LocalDateTime ngayDangKy;
    private LocalDateTime ngayHetHan;
    private String trangThai;
}