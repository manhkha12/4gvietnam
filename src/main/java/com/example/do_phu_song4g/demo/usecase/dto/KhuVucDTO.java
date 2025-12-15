package com.example.do_phu_song4g.demo.usecase.dto;


import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhuVucDTO {
    private Integer maKhuVuc;
    private String tenTinhThanh;
    private String tenQuanHuyen;
    private String tenPhuongXa;
    private String maHanhChinh;
    private BigDecimal dienTich;
    private Integer danSo;
    private String loaiKhuVuc;
    private Integer matDoDanSo;
    private BigDecimal tyLePhuSong;
}
