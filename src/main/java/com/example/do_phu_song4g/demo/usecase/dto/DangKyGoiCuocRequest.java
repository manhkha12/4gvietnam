package com.example.do_phu_song4g.demo.usecase.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangKyGoiCuocRequest {
    private Integer userId;       // THÊM: ID user đăng ký
    private Integer maGoiCuoc;
}