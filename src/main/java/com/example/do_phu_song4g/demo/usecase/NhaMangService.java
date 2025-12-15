package com.example.do_phu_song4g.demo.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.do_phu_song4g.demo.domain.models.NhaMang;
import com.example.do_phu_song4g.demo.infrastructure.repository.NhaMangRepository;
import com.example.do_phu_song4g.demo.usecase.dto.NhaMangDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NhaMangService {
    private final NhaMangRepository nhaMangRepository;

    public List<NhaMangDTO> getAllNhaMang() {
        return nhaMangRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NhaMangDTO getNhaMangById(Integer id) {
        return nhaMangRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà mạng"));
    }

    @Transactional
    public NhaMangDTO createNhaMang(NhaMangDTO dto) {
        NhaMang nhaMang = NhaMang.builder()
                .tenNhaMang(dto.getTenNhaMang())
                .tenVietTat(dto.getTenVietTat())
                .tongSoTram(dto.getTongSoTram())
                .thiPhan(dto.getThiPhan())
                .website(dto.getWebsite())
                .hotline(dto.getHotline())
                .trangThai(NhaMang.TrangThai.valueOf(dto.getTrangThai()))
                .build();
        return convertToDTO(nhaMangRepository.save(nhaMang));
    }

    private NhaMangDTO convertToDTO(NhaMang nhaMang) {
        return NhaMangDTO.builder()
                .maNhaMang(nhaMang.getMaNhaMang())
                .tenNhaMang(nhaMang.getTenNhaMang())
                .tenVietTat(nhaMang.getTenVietTat())
                .tongSoTram(nhaMang.getTongSoTram())
                .thiPhan(nhaMang.getThiPhan())
                .website(nhaMang.getWebsite())
                .hotline(nhaMang.getHotline())
                .trangThai(nhaMang.getTrangThai().name())
                .build();
    }
}