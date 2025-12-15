package com.example.do_phu_song4g.demo.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.do_phu_song4g.demo.domain.models.ChatLuongDichVu;

@Repository
public interface ChatLuongDichVuRepository extends JpaRepository<ChatLuongDichVu, Integer> {
    List<ChatLuongDichVu> findByVungPhuSong_MaVungPhu(Integer maVungPhu);
    
    @Query("SELECT cl FROM ChatLuongDichVu cl WHERE cl.thoiGianDo BETWEEN :start AND :end")
    List<ChatLuongDichVu> findByDateRange(@Param("start") LocalDateTime start, 
                                          @Param("end") LocalDateTime end);
    
    @Query("SELECT AVG(cl.tocDoDownload) FROM ChatLuongDichVu cl WHERE cl.vungPhuSong.tramPhatSong.nhaMang.maNhaMang = :maNhaMang")
    Double getAverageDownloadSpeed(@Param("maNhaMang") Integer maNhaMang);

    @Query("SELECT AVG(cl.tocDoUpload) FROM ChatLuongDichVu cl " +
           "WHERE cl.vungPhuSong.tramPhatSong.nhaMang.maNhaMang = :maNhaMang")
    Double getAverageUploadSpeed(@Param("maNhaMang") Integer maNhaMang);
    
    @Query("SELECT AVG(cl.doTre) FROM ChatLuongDichVu cl " +
           "WHERE cl.vungPhuSong.tramPhatSong.nhaMang.maNhaMang = :maNhaMang")
    Double getAverageLatency(@Param("maNhaMang") Integer maNhaMang);
}