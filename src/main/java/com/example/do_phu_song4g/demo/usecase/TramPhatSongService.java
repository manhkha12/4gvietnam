package com.example.do_phu_song4g.demo.usecase;

import com.example.do_phu_song4g.demo.domain.models.*;
import com.example.do_phu_song4g.demo.infrastructure.repository.CongNgheMangRepository;
import com.example.do_phu_song4g.demo.infrastructure.repository.NhaMangRepository;
import com.example.do_phu_song4g.demo.infrastructure.repository.TramPhatSongRepository;
import com.example.do_phu_song4g.demo.usecase.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TramPhatSongService {
    private final TramPhatSongRepository tramPhatSongRepository;
    private final NhaMangRepository nhaMangRepository;
    private final CongNgheMangRepository congNgheMangRepository;

    public Page<TramPhatSongDTO> getAllTram(int page, int size, String search, 
                                             Integer maNhaMang, Integer maCongNghe, String tinhThanh) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayKichHoat").descending());
        Page<TramPhatSong> tramPage;
        
        if (maNhaMang != null) {
            tramPage = tramPhatSongRepository.findByNhaMang_MaNhaMang(maNhaMang, pageable);
        } else if (tinhThanh != null && !tinhThanh.isEmpty()) {
            tramPage = tramPhatSongRepository.findByTinhThanh(tinhThanh, pageable);
        } else {
            tramPage = tramPhatSongRepository.findAll(pageable);
        }
        
        return tramPage.map(this::convertToDTO);
    }

    public TramPhatSongDTO getTramById(Integer id) {
        return tramPhatSongRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy trạm"));
    }

    @Transactional
    public TramPhatSongDTO createTram(CreateTramRequest request) {
        NhaMang nhaMang = nhaMangRepository.findById(request.getMaNhaMang())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà mạng"));
        
        CongNgheMang congNghe = congNgheMangRepository.findById(request.getMaCongNghe())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công nghệ"));

        TramPhatSong tram = TramPhatSong.builder()
                .maTramNhaMang(request.getMaTramNhaMang())
                .tenTram(request.getTenTram())
                .nhaMang(nhaMang)
                .congNgheMang(congNghe)
                .kinhDo(request.getKinhDo())
                .viDo(request.getViDo())
                .diaChi(request.getDiaChi())
                .tinhThanh(request.getTinhThanh())
                .quanHuyen(request.getQuanHuyen())
                .phuongXa(request.getPhuongXa())
                .congSuatPhat(request.getCongSuatPhat())
                .banKinhPhuSong(request.getBanKinhPhuSong())
                .ngayLapDat(request.getNgayLapDat())
                .ngayKichHoat(request.getNgayKichHoat())
                .trangThai(TramPhatSong.TrangThai.HOAT_DONG)
                .build();

        return convertToDTO(tramPhatSongRepository.save(tram));
    }

    @Transactional
    public TramPhatSongDTO updateTram(Integer id, CreateTramRequest request) {
        TramPhatSong tram = tramPhatSongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy trạm"));

        tram.setTenTram(request.getTenTram());
        tram.setKinhDo(request.getKinhDo());
        tram.setViDo(request.getViDo());
        tram.setDiaChi(request.getDiaChi());
        tram.setTinhThanh(request.getTinhThanh());
        tram.setQuanHuyen(request.getQuanHuyen());
        tram.setPhuongXa(request.getPhuongXa());
        tram.setBanKinhPhuSong(request.getBanKinhPhuSong());

        return convertToDTO(tramPhatSongRepository.save(tram));
    }

    @Transactional
    public void deleteTram(Integer id) {
        tramPhatSongRepository.deleteById(id);
    }

    public List<TramPhatSongDTO> getTramGanViTri(Double lat, Double lng, Integer banKinh) {
        // Tìm trạm trong bán kính (đơn giản hóa, thực tế cần dùng spatial query)
        return tramPhatSongRepository.findAll().stream()
                .filter(tram -> {
                    double distance = calculateDistance(
                        lat, lng, 
                        tram.getViDo().doubleValue(), 
                        tram.getKinhDo().doubleValue()
                    );
                    return distance <= banKinh;
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371000; // Bán kính trái đất (m)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    private TramPhatSongDTO convertToDTO(TramPhatSong tram) {
        return TramPhatSongDTO.builder()
                .maTram(tram.getMaTram())
                .maTramNhaMang(tram.getMaTramNhaMang())
                .tenTram(tram.getTenTram())
                .maNhaMang(tram.getNhaMang().getMaNhaMang())
                .tenNhaMang(tram.getNhaMang().getTenNhaMang())
                .maCongNghe(tram.getCongNgheMang().getMaCongNghe())
                .tenCongNghe(tram.getCongNgheMang().getTenCongNghe())
                .theHe(tram.getCongNgheMang().getTheHe().getValue())
                .kinhDo(tram.getKinhDo())
                .viDo(tram.getViDo())
                .diaChi(tram.getDiaChi())
                .tinhThanh(tram.getTinhThanh())
                .quanHuyen(tram.getQuanHuyen())
                .phuongXa(tram.getPhuongXa())
                .congSuatPhat(tram.getCongSuatPhat())
                .banKinhPhuSong(tram.getBanKinhPhuSong())
                .ngayLapDat(tram.getNgayLapDat())
                .ngayKichHoat(tram.getNgayKichHoat())
                .trangThai(tram.getTrangThai().getValue())
                .build();
    }
}