package com.example.do_phu_song4g.demo.usecase.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String soDienThoai;
    private String matKhau;
    private String hoTen;
    private String email;
    private String diaChi;
}