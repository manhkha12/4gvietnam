package com.example.do_phu_song4g.demo.domain.models;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "GOI_CUOC")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoiCuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_goi_cuoc")
    private Integer maGoiCuoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nha_mang", nullable = false)
    private NhaMang nhaMang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_cong_nghe", nullable = false)
    private CongNgheMang congNgheMang;

    @Column(name = "ten_goi_cuoc", nullable = false)
    private String tenGoiCuoc;

    @Column(name = "gia_cuoc", precision = 12, scale = 2)
    private BigDecimal giaCuoc;

    @Column(name = "dung_luong_data")
    private Integer dungLuongData;

    @Column(name = "thoi_han_ngay")
    private Integer thoiHanNgay;

    @Column(name = "mo_ta_chi_tiet", columnDefinition = "TEXT")
    private String moTaChiTiet;

    @Column(name = "ngay_ap_dung")
    private LocalDate ngayApDung;

    @Column(name = "ngay_het_han")
    private LocalDate ngayHetHan;

    @Column(name = "trang_thai")
    @Convert(converter = TrangThaiConverter.class)
    private TrangThai trangThai;

    public enum TrangThai {
        DANG_AP_DUNG("Đang áp dụng"),
        HET_HAN("Hết hạn"),
        NGUNG_CUNG_CAP("Ngừng cung cấp");

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
    }}