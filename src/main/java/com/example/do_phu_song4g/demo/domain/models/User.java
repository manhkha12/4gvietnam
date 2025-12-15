package com.example.do_phu_song4g.demo.domain.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "so_dien_thoai", unique = true, nullable = false, length = 15)
    private String soDienThoai;

    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "ngay_sinh")
    private LocalDateTime ngaySinh;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao;

    @Column(name = "trang_thai")
    @Convert(converter = TrangThaiConverter.class)
    private TrangThai trangThai;

    @Column(name = "role")
    @Convert(converter = RoleConverter.class)
    private Role role;

    public enum TrangThai {
        ACTIVE("Hoạt động"),
        INACTIVE("Ngừng hoạt động"),
        LOCKED("Bị khóa");

        private final String value;
        TrangThai(String value) { this.value = value; }
        public String getValue() { return value; }
        
        public static TrangThai fromValue(String value) {
            for (TrangThai t : TrangThai.values()) {
                if (t.value.equals(value)) return t;
            }
            throw new IllegalArgumentException("Unknown TrangThai: " + value);
        }
    }

    public enum Role {
        USER("Người dùng"),
        ADMIN("Quản trị viên");

        private final String value;
        Role(String value) { this.value = value; }
        public String getValue() { return value; }
        
        public static Role fromValue(String value) {
            for (Role r : Role.values()) {
                if (r.value.equals(value)) return r;
            }
            throw new IllegalArgumentException("Unknown Role: " + value);
        }
    }

    @Converter
    public static class TrangThaiConverter implements AttributeConverter<TrangThai, String> {
        @Override
        public String convertToDatabaseColumn(TrangThai attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public TrangThai convertToEntityAttribute(String dbData) {
            return dbData == null || dbData.isEmpty() ? null : TrangThai.fromValue(dbData);
        }
    }

    @Converter
    public static class RoleConverter implements AttributeConverter<Role, String> {
        @Override
        public String convertToDatabaseColumn(Role attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public Role convertToEntityAttribute(String dbData) {
            return dbData == null || dbData.isEmpty() ? null : Role.fromValue(dbData);
        }
    }
}