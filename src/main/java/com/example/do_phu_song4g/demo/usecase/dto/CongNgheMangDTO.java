package com.example.do_phu_song4g.demo.usecase.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CongNgheMangDTO {
    private Integer maCongNghe;
    private String tenCongNghe;
    private String theHe;
    private Integer tanSoMhz;
    private Integer bangThongMhz;
    private Integer tocDoLyThuyet;
    private Integer namTrienKhai;
    private String moTa;
}