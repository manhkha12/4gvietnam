package com.example.do_phu_song4g.demo.usecase.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;  // Có thể dùng JWT sau
    private UserDTO user;
    private String message;
}