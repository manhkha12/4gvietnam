package com.example.do_phu_song4g.demo.infrastructure.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.do_phu_song4g.demo.domain.models.NhaMang;

@Repository
public interface NhaMangRepository extends JpaRepository<NhaMang, Integer> {
    Optional<NhaMang> findByTenNhaMang(String tenNhaMang);
    List<NhaMang> findByTrangThai(NhaMang.TrangThai trangThai);
    
    @Query("SELECT nm FROM NhaMang nm ORDER BY nm.thiPhan DESC")
    List<NhaMang> findAllOrderByThiPhanDesc();
}