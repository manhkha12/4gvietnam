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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NHA_MANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhaMang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_nha_mang")
    private Integer maNhaMang;

    @Column(name = "ten_nha_mang", unique = true, nullable = false, length = 100)
    private String tenNhaMang;

    @Column(name = "ten_viet_tat", length = 20)
    private String tenVietTat;

    @Column(name = "ngay_thanh_lap")
    private LocalDate ngayThanhLap;

    @Column(name = "tong_so_tram")
    private Integer tongSoTram;

    @Column(name = "thi_phan", precision = 5, scale = 2)
    private BigDecimal thiPhan;

    @Column(name = "website")
    private String website;

    @Column(name = "hotline", length = 20)
    private String hotline;


    @Column(name = "trang_thai")
    @Convert(converter = TrangThaiConverter.class)

    private TrangThai trangThai;

   public enum TrangThai {
    HOAT_DONG("Hoạt động"),
    TAM_NGUNG("Tạm ngừng"),
    NGUNG_HOAT_DONG("Ngừng hoạt động");

    private final String value;
    TrangThai(String value) { this.value = value; }
    public String getValue() { return value; }
    
    public static TrangThai fromValue(String value) {
        for (TrangThai status : TrangThai.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}

@Converter(autoApply = true)
public static class TrangThaiConverter implements AttributeConverter<TrangThai, String> {
    @Override
    public String convertToDatabaseColumn(TrangThai attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public TrangThai convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TrangThai.fromValue(dbData);
    }
}
}
