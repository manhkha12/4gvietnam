package com.example.do_phu_song4g.demo.infrastructure.repository;

import com.example.do_phu_song4g.demo.domain.models.CongNgheMang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CongNgheMangRepository extends JpaRepository<CongNgheMang, Integer> {
    List<CongNgheMang> findByTheHeOrderByNamTrienKhaiDesc(CongNgheMang.TheHe theHe);
}