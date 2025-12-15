package com.example.do_phu_song4g.demo.usecase;


import com.example.do_phu_song4g.demo.domain.models.CongNgheMang;
import com.example.do_phu_song4g.demo.infrastructure.repository.CongNgheMangRepository;
import com.example.do_phu_song4g.demo.usecase.dto.CongNgheMangDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CongNgheMangService {
    private final CongNgheMangRepository congNgheMangRepository;

    public List<CongNgheMangDTO> getAllCongNghe() {
        return congNgheMangRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CongNgheMangDTO convertToDTO(CongNgheMang cn) {
        return CongNgheMangDTO.builder()
                .maCongNghe(cn.getMaCongNghe())
                .tenCongNghe(cn.getTenCongNghe())
                .theHe(cn.getTheHe().getValue())
                .tanSoMhz(cn.getTanSoMhz())
                .bangThongMhz(cn.getBangThongMhz())
                .tocDoLyThuyet(cn.getTocDoLyThuyet())
                .namTrienKhai(cn.getNamTrienKhai())
                .moTa(cn.getMoTa())
                .build();
    }
}