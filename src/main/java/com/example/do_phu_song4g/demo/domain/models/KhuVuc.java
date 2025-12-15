package com.example.do_phu_song4g.demo.domain.models;

import java.math.BigDecimal;

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
@Table(name = "KHU_VUC")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhuVuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_khu_vuc")
    private Integer maKhuVuc;

    @Column(name = "ten_tinh_thanh", nullable = false)
    private String tenTinhThanh;

    @Column(name = "ten_quan_huyen")
    private String tenQuanHuyen;

    @Column(name = "ten_phuong_xa")
    private String tenPhuongXa;

    @Column(name = "ma_hanh_chinh", unique = true)
    private String maHanhChinh;

    @Column(name = "dien_tich", precision = 10, scale = 2)
    private BigDecimal dienTich;

    @Column(name = "dan_so")
    private Integer danSo;

    @Column(name = "loai_khu_vuc")
    @Convert(converter = LoaiKhuVucConverter.class)
    private LoaiKhuVuc loaiKhuVuc;

    @Column(name = "mat_do_dan_so")
    private Integer matDoDanSo;

    public enum LoaiKhuVuc {
        THANH_THI("Thành thị"),
        NONG_THON("Nông thôn"),
        VUNG_SAU_VUNG_XA("Vùng sâu vùng xa");

        private final String value;
        LoaiKhuVuc(String value) { this.value = value; }
        public String getValue() { return value; }
        
        public static LoaiKhuVuc fromValue(String value) {
            for (LoaiKhuVuc l : LoaiKhuVuc.values()) {
                if (l.value.equals(value)) return l;
            }
            throw new IllegalArgumentException("Unknown LoaiKhuVuc: " + value);
        }
    }

    @Converter
    public static class LoaiKhuVucConverter implements AttributeConverter<LoaiKhuVuc, String> {
        @Override
        public String convertToDatabaseColumn(LoaiKhuVuc attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public LoaiKhuVuc convertToEntityAttribute(String dbData) {
            return dbData == null || dbData.isEmpty() ? null : LoaiKhuVuc.fromValue(dbData);
        }
    }
}