package com.example.do_phu_song4g.demo.infrastructure.repository;

import com.example.do_phu_song4g.demo.domain.models.GoiCuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GoiCuocRepository extends JpaRepository<GoiCuoc, Integer> {
    List<GoiCuoc> findByNhaMang_MaNhaMang(Integer maNhaMang);
    
    List<GoiCuoc> findByCongNgheMang_MaCongNghe(Integer maCongNghe);
    
    @Query("SELECT gc FROM GoiCuoc gc WHERE gc.trangThai = " +
           "com.example.do_phu_song4g.demo.domain.models.GoiCuoc$TrangThai.DANG_AP_DUNG")
    List<GoiCuoc> findActiveGoiCuoc();
    
    @Query("SELECT gc FROM GoiCuoc gc WHERE gc.nhaMang.maNhaMang = :maNhaMang " +
           "AND gc.trangThai = com.example.do_phu_song4g.demo.domain.models.GoiCuoc$TrangThai.DANG_AP_DUNG")
    List<GoiCuoc> findActiveByNhaMang(Integer maNhaMang);
}

