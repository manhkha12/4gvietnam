package com.example.do_phu_song4g.demo.usecase.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopTinhPhuSongDTO {
    private String tinhThanh;
    private BigDecimal tyLePhu;
    private Long soTram;
    private Long danSoDuocPhu;
    private Double tocDoTrungBinh;
}
