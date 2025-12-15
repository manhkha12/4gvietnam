package com.example.do_phu_song4g.demo.usecase.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongKePhuSongDTO {
    private String tenNhaMang;
    private String tenCongNghe;
    private Long soLuongTram;
    private BigDecimal tyLePhuSongTrungBinh;
    private Long tongDanSoDuocPhu;
}
