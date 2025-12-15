package com.example.do_phu_song4g.demo.domain.models;

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
@Table(name = "CONG_NGHE_MANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CongNgheMang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_cong_nghe")
    private Integer maCongNghe;

    @Column(name = "ten_cong_nghe", unique = true, nullable = false)
    private String tenCongNghe;

    @Column(name = "the_he", nullable = false)
    @Convert(converter = TheHeConverter.class)
    private TheHe theHe;

    @Column(name = "tan_so_mhz")
    private Integer tanSoMhz;

    @Column(name = "bang_thong_mhz")
    private Integer bangThongMhz;

    @Column(name = "toc_do_ly_thuyet")
    private Integer tocDoLyThuyet;

    @Column(name = "nam_trien_khai")
    private Integer namTrienKhai;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    public enum TheHe {
        _2G("2G"),
        _3G("3G"),
        _4G("4G"),
        _5G("5G");

        private final String value;
        TheHe(String value) { this.value = value; }
        public String getValue() { return value; }
        
        public static TheHe fromValue(String value) {
            for (TheHe t : TheHe.values()) {
                if (t.value.equals(value)) return t;
            }
            throw new IllegalArgumentException("Unknown TheHe: " + value);
        }
    }

    @Converter
    public static class TheHeConverter implements AttributeConverter<TheHe, String> {
        @Override
        public String convertToDatabaseColumn(TheHe attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public TheHe convertToEntityAttribute(String dbData) {
            return dbData == null || dbData.isEmpty() ? null : TheHe.fromValue(dbData);
        }
    }
}