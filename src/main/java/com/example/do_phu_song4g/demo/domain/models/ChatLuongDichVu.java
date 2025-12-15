package com.example.do_phu_song4g.demo.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "CHAT_LUONG_DICH_VU")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatLuongDichVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_do_luong")
    private Integer maDoLuong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_vung_phu", nullable = false)
    private VungPhuSong vungPhuSong;

    @Column(name = "thoi_gian_do", nullable = false)
    private LocalDateTime thoiGianDo;

    @Column(name = "toc_do_download", precision = 10, scale = 2)
    private BigDecimal tocDoDownload;

    @Column(name = "toc_do_upload", precision = 10, scale = 2)
    private BigDecimal tocDoUpload;

    @Column(name = "do_tre")
    private Integer doTre;

    @Column(name = "ty_le_mat_goi", precision = 5, scale = 2)
    private BigDecimal tyLeMatGoi;

    @Column(name = "diem_chat_luong")
    private Integer diemChatLuong;

    @Column(name = "nguon_du_lieu", length = 100)
    private String nguonDuLieu;

    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;
}
