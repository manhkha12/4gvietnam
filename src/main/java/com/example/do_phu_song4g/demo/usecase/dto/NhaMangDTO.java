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
public class NhaMangDTO {
    private Integer maNhaMang;
    private String tenNhaMang;
    private String tenVietTat;
    private Integer tongSoTram;
    private BigDecimal thiPhan;
    private String website;
    private String hotline;
    private String trangThai;
}