package com.example.do_phu_song4g.demo.usecase.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTramRequest {
    private String maTramNhaMang;
    private String tenTram;
    private Integer maNhaMang;
    private Integer maCongNghe;
    private BigDecimal kinhDo;
    private BigDecimal viDo;
    private String diaChi;
    private String tinhThanh;
    private String quanHuyen;
    private String phuongXa;
    private Integer congSuatPhat;
    private Integer banKinhPhuSong;
    private LocalDate ngayLapDat;
    private LocalDate ngayKichHoat;
}