package com.example.do_phu_song4g.demo.usecase.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGoiCuocRequest {
    private Integer maNhaMang;
    private Integer maCongNghe;
    private String tenGoiCuoc;
    private BigDecimal giaCuoc;
    private Integer dungLuongData;
    private Integer thoiHanNgay;
    private String moTaChiTiet;
    private LocalDate ngayApDung;
    private LocalDate ngayHetHan;
}
