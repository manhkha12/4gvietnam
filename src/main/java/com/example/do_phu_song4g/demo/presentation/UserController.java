package com.example.do_phu_song4g.demo.presentation;

import com.example.do_phu_song4g.demo.usecase.UserService;
import com.example.do_phu_song4g.demo.usecase.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    // POST /api/auth/register - Đăng ký
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return ResponseEntity.ok(ApiResponse.success(response.getMessage(), response));
    }

    // POST /api/auth/login - Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response.getMessage(), response));
    }

    // GET /api/auth/me/{userId} - Lấy thông tin user
    @GetMapping("/me/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getMe(@PathVariable Integer userId) {
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    // PUT /api/auth/update/{userId} - Cập nhật thông tin
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable Integer userId,
            @RequestBody RegisterRequest request) {
        UserDTO updated = userService.updateUser(userId, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", updated));
    }

    // POST /api/auth/change-password - Đổi mật khẩu
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @RequestParam Integer userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        userService.changePassword(userId, oldPassword, newPassword);
        return ResponseEntity.ok(ApiResponse.success("Đổi mật khẩu thành công", null));
    }
}