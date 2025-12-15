package com.example.do_phu_song4g.demo.usecase;

import com.example.do_phu_song4g.demo.domain.models.KhuVuc;
import com.example.do_phu_song4g.demo.infrastructure.repository.KhuVucRepository;
import com.example.do_phu_song4g.demo.infrastructure.repository.VungPhuSongRepository;
import com.example.do_phu_song4g.demo.usecase.dto.KhuVucDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KhuVucService {
    private final KhuVucRepository khuVucRepository;
    private final VungPhuSongRepository vungPhuSongRepository;

    public Page<KhuVucDTO> getAllKhuVuc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return khuVucRepository.findAll(pageable).map(this::convertToDTO);
    }

    public List<KhuVucDTO> getKhuVucByTinh(String tinhThanh) {
        return khuVucRepository.findByTenTinhThanh(tinhThanh).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public KhuVucDTO createKhuVuc(KhuVucDTO dto) {
        KhuVuc khuVuc = KhuVuc.builder()
                .tenTinhThanh(dto.getTenTinhThanh())
                .tenQuanHuyen(dto.getTenQuanHuyen())
                .tenPhuongXa(dto.getTenPhuongXa())
                .maHanhChinh(dto.getMaHanhChinh())
                .dienTich(dto.getDienTich())
                .danSo(dto.getDanSo())
                .loaiKhuVuc(KhuVuc.LoaiKhuVuc.fromValue(dto.getLoaiKhuVuc()))
                .matDoDanSo(dto.getMatDoDanSo())
                .build();
        return convertToDTO(khuVucRepository.save(khuVuc));
    }

    private KhuVucDTO convertToDTO(KhuVuc kv) {
        return KhuVucDTO.builder()
                .maKhuVuc(kv.getMaKhuVuc())
                .tenTinhThanh(kv.getTenTinhThanh())
                .tenQuanHuyen(kv.getTenQuanHuyen())
                .tenPhuongXa(kv.getTenPhuongXa())
                .maHanhChinh(kv.getMaHanhChinh())
                .dienTich(kv.getDienTich())
                .danSo(kv.getDanSo())
                .loaiKhuVuc(kv.getLoaiKhuVuc() != null ? kv.getLoaiKhuVuc().getValue() : null)
                .matDoDanSo(kv.getMatDoDanSo())
                .build();
    }
}
