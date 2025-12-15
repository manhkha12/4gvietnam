package com.example.do_phu_song4g.demo.usecase.dto;


import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoSanhNhaMangDTO {
    private String tenNhaMang;
    private Long soTram;
    private BigDecimal thiPhan;
    private Double tocDoDownload;
    private Double tocDoUpload;
    private Integer doTre;
}