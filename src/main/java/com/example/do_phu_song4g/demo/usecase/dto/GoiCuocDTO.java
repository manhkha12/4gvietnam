package com.example.do_phu_song4g.demo.usecase.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoiCuocDTO {
    private Integer maGoiCuoc;
    private Integer maNhaMang;
    // private Integer userId;
    private String tenNhaMang;
    private Integer maCongNghe;
    private String tenCongNghe;
    private String theHe;
    private String tenGoiCuoc;
    private BigDecimal giaCuoc;
    private Integer dungLuongData;
    private Integer thoiHanNgay;
    private String moTaChiTiet;
    private LocalDate ngayApDung;
    private LocalDate ngayHetHan;
    private String trangThai;
}