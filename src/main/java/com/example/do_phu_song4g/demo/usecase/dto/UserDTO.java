package com.example.do_phu_song4g.demo.usecase.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer userId;
    private String soDienThoai;
    private String hoTen;
    private String email;
    private String diaChi;
    private LocalDateTime ngaySinh;
    private String gioiTinh;
    private String avatarUrl;
    private LocalDateTime ngayTao;
    private String trangThai;
    private String role;
}