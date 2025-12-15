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
@Table(name = "VUNG_PHU_SONG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VungPhuSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_vung_phu")
    private Integer maVungPhu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_tram", nullable = false)
    private TramPhatSong tramPhatSong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_khu_vuc", nullable = false)
    private KhuVuc khuVuc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_cong_nghe", nullable = false)
    private CongNgheMang congNgheMang;

    @Column(name = "ty_le_phu_song", precision = 5, scale = 2)
    private BigDecimal tyLePhuSong;

    @Column(name = "dan_so_duoc_phu")
    private Integer danSoDuocPhu;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "chat_luong_song")
    @Convert(converter = ChatLuongSongConverter.class)
    private ChatLuongSong chatLuongSong;

    public enum ChatLuongSong {
        RAT_TOT("Rất tốt"),
        TOT("Tốt"),
        TRUNG_BINH("Trung bình"),
        YEU("Yếu");

        private final String value;
        ChatLuongSong(String value) { this.value = value; }
        public String getValue() { return value; }
        
        public static ChatLuongSong fromValue(String value) {
            for (ChatLuongSong c : ChatLuongSong.values()) {
                if (c.value.equals(value)) return c;
            }
            throw new IllegalArgumentException("Unknown ChatLuongSong: " + value);
        }
    }

    @Converter
    public static class ChatLuongSongConverter implements AttributeConverter<ChatLuongSong, String> {
        @Override
        public String convertToDatabaseColumn(ChatLuongSong attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public ChatLuongSong convertToEntityAttribute(String dbData) {
            return dbData == null || dbData.isEmpty() ? null : ChatLuongSong.fromValue(dbData);
        }
    }
}