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
public class ThongKe5GDTO {
    private Long tongDanSo;
    private Long danSoDuocPhu5G;
    private BigDecimal tyLePhu5G;
    private Long soTram5G;
}