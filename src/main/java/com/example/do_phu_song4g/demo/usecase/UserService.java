package com.example.do_phu_song4g.demo.usecase;

import com.example.do_phu_song4g.demo.domain.models.User;
import com.example.do_phu_song4g.demo.infrastructure.repository.UserRepository;
import com.example.do_phu_song4g.demo.usecase.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    // Đăng ký
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Kiểm tra số điện thoại đã tồn tại
        if (userRepository.existsBySoDienThoai(request.getSoDienThoai())) {
            throw new RuntimeException("Số điện thoại đã được đăng ký");
        }

        // Kiểm tra email đã tồn tại
        if (request.getEmail() != null && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã được đăng ký");
        }

        // Tạo user mới (trong thực tế nên mã hóa password với BCrypt)
        User user = User.builder()
                .soDienThoai(request.getSoDienThoai())
                .matKhau(request.getMatKhau()) // TODO: Hash password
                .hoTen(request.getHoTen())
                .email(request.getEmail())
                .diaChi(request.getDiaChi())
                .ngayTao(LocalDateTime.now())
                .trangThai(User.TrangThai.ACTIVE)
                .role(User.Role.USER)
                .build();

        User saved = userRepository.save(user);

        return AuthResponse.builder()
                .token("mock-token-" + saved.getUserId()) // TODO: Generate JWT
                .user(convertToDTO(saved))
                .message("Đăng ký thành công")
                .build();
    }

    // Đăng nhập
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findBySoDienThoai(request.getSoDienThoai())
                .orElseThrow(() -> new RuntimeException("Số điện thoại không tồn tại"));

        // Kiểm tra password (trong thực tế dùng BCrypt)
        if (!user.getMatKhau().equals(request.getMatKhau())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        // Kiểm tra trạng thái
        if (user.getTrangThai() != User.TrangThai.ACTIVE) {
            throw new RuntimeException("Tài khoản đã bị khóa hoặc ngừng hoạt động");
        }

        return AuthResponse.builder()
                .token("mock-token-" + user.getUserId()) // TODO: Generate JWT
                .user(convertToDTO(user))
                .message("Đăng nhập thành công")
                .build();
    }

    // Lấy thông tin user
    public UserDTO getUserById(Integer userId) {
        return userRepository.findById(userId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    // Cập nhật thông tin
    @Transactional
    public UserDTO updateUser(Integer userId, RegisterRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        user.setHoTen(request.getHoTen());
        user.setEmail(request.getEmail());
        user.setDiaChi(request.getDiaChi());

        return convertToDTO(userRepository.save(user));
    }

    // Đổi mật khẩu
    @Transactional
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        if (!user.getMatKhau().equals(oldPassword)) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }

        user.setMatKhau(newPassword); // TODO: Hash password
        userRepository.save(user);
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .soDienThoai(user.getSoDienThoai())
                .hoTen(user.getHoTen())
                .email(user.getEmail())
                .diaChi(user.getDiaChi())
                .ngaySinh(user.getNgaySinh())
                .gioiTinh(user.getGioiTinh())
                .avatarUrl(user.getAvatarUrl())
                .ngayTao(user.getNgayTao())
                .trangThai(user.getTrangThai().getValue())
                .role(user.getRole().getValue())
                .build();
    }
}