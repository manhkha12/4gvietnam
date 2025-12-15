package com.example.do_phu_song4g.demo.domain.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRAM_PHAT_SONG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TramPhatSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_tram")
    private Integer maTram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nha_mang", nullable = false)
    private NhaMang nhaMang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_cong_nghe", nullable = false)
    private CongNgheMang congNgheMang;

    @Column(name = "ma_tram_nha_mang", unique = true, nullable = false)
    private String maTramNhaMang;

    @Column(name = "ten_tram")
    private String tenTram;

    @Column(name = "kinh_do", nullable = false, precision = 10, scale = 7)
    private BigDecimal kinhDo;

    @Column(name = "vi_do", nullable = false, precision = 10, scale = 7)
    private BigDecimal viDo;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "tinh_thanh")
    private String tinhThanh;

    @Column(name = "quan_huyen")
    private String quanHuyen;

    @Column(name = "phuong_xa")
    private String phuongXa;

    @Column(name = "cong_suat_phat")
    private Integer congSuatPhat;

    @Column(name = "ban_kinh_phu_song")
    private Integer banKinhPhuSong;

    @Column(name = "ngay_lap_dat")
    private LocalDate ngayLapDat;

    @Column(name = "ngay_kich_hoat")
    private LocalDate ngayKichHoat;

    @Column(name = "trang_thai")
    @Convert(converter = TrangThaiConverter.class)
    private TrangThai trangThai;

    public enum TrangThai {
        HOAT_DONG("Hoạt động"),
        BAO_TRI("Bảo trì"),
        NGUNG_HOAT_DONG("Ngừng hoạt động");

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