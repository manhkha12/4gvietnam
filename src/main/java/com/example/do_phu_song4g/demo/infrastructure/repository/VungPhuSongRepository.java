package com.example.do_phu_song4g.demo.infrastructure.repository;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.do_phu_song4g.demo.domain.models.VungPhuSong;

@Repository
public interface VungPhuSongRepository extends JpaRepository<VungPhuSong, Integer> {
    List<VungPhuSong> findByKhuVuc_MaKhuVuc(Integer maKhuVuc);
    
    @Query("SELECT vp FROM VungPhuSong vp WHERE vp.congNgheMang.theHe = '5G'")
    List<VungPhuSong> findAll5GCoverage();
    
    @Query("SELECT vp FROM VungPhuSong vp WHERE vp.tyLePhuSong < :threshold")
    List<VungPhuSong> findLowCoverageAreas(@Param("threshold") BigDecimal threshold);
}

