package com.example.do_phu_song4g.demo.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.do_phu_song4g.demo.domain.models.TramPhatSong;

@Repository
public interface TramPhatSongRepository extends JpaRepository<TramPhatSong, Integer> {
    List<TramPhatSong> findByNhaMang_MaNhaMang(Integer maNhaMang);
    List<TramPhatSong> findByTinhThanh(String tinhThanh);
    List<TramPhatSong> findByTrangThai(TramPhatSong.TrangThai trangThai);
    
    @Query("SELECT COUNT(t) FROM TramPhatSong t WHERE t.nhaMang.maNhaMang = :maNhaMang AND t.congNgheMang.theHe = :theHe")
    Long countByNhaMangAndCongNghe(@Param("maNhaMang") Integer maNhaMang, 
                                   @Param("theHe") String theHe);
    
    @Query("SELECT t FROM TramPhatSong t WHERE t.congNgheMang.theHe = '5G' AND t.trangThai = 'HOAT_DONG'")
    List<TramPhatSong> findAllActive5GStations();


Page<TramPhatSong> findByNhaMang_MaNhaMang(Integer maNhaMang, Pageable pageable);
    
    Page<TramPhatSong> findByTinhThanh(String tinhThanh, Pageable pageable);
    
    Page<TramPhatSong> findByCongNgheMang_MaCongNghe(Integer maCongNghe, Pageable pageable);
    
    @Query("SELECT t FROM TramPhatSong t WHERE " +
           "(:search IS NULL OR t.tenTram LIKE %:search% OR t.maTramNhaMang LIKE %:search%) AND " +
           "(:maNhaMang IS NULL OR t.nhaMang.maNhaMang = :maNhaMang) AND " +
           "(:maCongNghe IS NULL OR t.congNgheMang.maCongNghe = :maCongNghe)")
    Page<TramPhatSong> searchTram(@Param("search") String search,
                                  @Param("maNhaMang") Integer maNhaMang,
                                  @Param("maCongNghe") Integer maCongNghe,
                                  Pageable pageable);


}