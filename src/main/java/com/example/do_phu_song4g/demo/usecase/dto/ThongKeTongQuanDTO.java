package com.example.do_phu_song4g.demo.usecase.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongKeTongQuanDTO {
    private Long tongTram5G;
    private BigDecimal tyLePhuSong5G;
    private Integer soNhaMang;
    private Double tocDoTrungBinh;
    private Long tongDanSo;
    private Long danSoDuocPhu5G;
}