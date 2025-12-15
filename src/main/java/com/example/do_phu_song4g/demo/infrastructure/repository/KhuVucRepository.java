package com.example.do_phu_song4g.demo.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.do_phu_song4g.demo.domain.models.KhuVuc;

@Repository
public interface KhuVucRepository extends JpaRepository<KhuVuc, Integer> {
    List<KhuVuc> findByTenTinhThanh(String tinhThanh);
    List<KhuVuc> findByLoaiKhuVuc(KhuVuc.LoaiKhuVuc loaiKhuVuc);
    
    @Query("SELECT kv FROM KhuVuc kv ORDER BY kv.danSo DESC")
    List<KhuVuc> findAllOrderByDanSoDesc();
}
