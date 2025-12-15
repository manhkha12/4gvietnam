package com.example.do_phu_song4g.demo.infrastructure.repository;

import com.example.do_phu_song4g.demo.domain.models.DangKyGoiCuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DangKyGoiCuocRepository extends JpaRepository<DangKyGoiCuoc, Integer> {
    List<DangKyGoiCuoc> findByUser_SoDienThoai(String soDienThoai);
   List<DangKyGoiCuoc> findByUser_UserId(Integer userId);
    List<DangKyGoiCuoc> findByGoiCuoc_MaGoiCuoc(Integer maGoiCuoc);
    
    @Query("SELECT COUNT(dk) FROM DangKyGoiCuoc dk WHERE dk.goiCuoc.maGoiCuoc = :maGoiCuoc")
    Long countByGoiCuoc(Integer maGoiCuoc);
}
