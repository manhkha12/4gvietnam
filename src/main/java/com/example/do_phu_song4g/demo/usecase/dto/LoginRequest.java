package com.example.do_phu_song4g.demo.usecase.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    private String soDienThoai;
    private String matKhau;
}
