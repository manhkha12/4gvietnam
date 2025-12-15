package com.example.do_phu_song4g.demo.usecase.dto;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatmapDataDTO {
    private String tinhThanh;
    private String quanHuyen;
    private BigDecimal tyLePhuSong;
    private BigDecimal lat;
    private BigDecimal lng;
    private String color;
    private Integer intensity;
}
