package com.example.do_phu_song4g.demo.domain.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dang_ky_goi_cuoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangKyGoiCuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_dang_ky")
    private Integer maDangKy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)  // THÊM: Liên kết với User
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_goi_cuoc", nullable = false)
    private GoiCuoc goiCuoc;

    @Column(name = "ngay_dang_ky", nullable = false)
    private LocalDateTime ngayDangKy;

    @Column(name = "ngay_het_han")
    private LocalDateTime ngayHetHan;

    @Column(name = "trang_thai")
    @Convert(converter = TrangThaiConverter.class)
    private TrangThai trangThai;

    public enum TrangThai {
        DANG_HOAT_DONG("Đang hoạt động"),
        HET_HAN("Hết hạn"),
        HUY("Hủy");

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
}