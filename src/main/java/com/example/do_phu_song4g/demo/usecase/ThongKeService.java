package com.example.do_phu_song4g.demo.usecase;

import com.example.do_phu_song4g.demo.domain.models.*;
import com.example.do_phu_song4g.demo.infrastructure.repository.*;
import com.example.do_phu_song4g.demo.usecase.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThongKeService {
    private final VungPhuSongRepository vungPhuSongRepository;
    private final KhuVucRepository khuVucRepository;
    private final TramPhatSongRepository tramPhatSongRepository;
    private final NhaMangRepository nhaMangRepository;
    private final ChatLuongDichVuRepository chatLuongDichVuRepository;

    // 1. Thống kê tổng quan
    public ThongKeTongQuanDTO getThongKeTongQuan() {
        ThongKe5GDTO thongKe5G = getThongKe5G();
        // FIX: Đổi Math.toInt() thành (int)
        Integer soNhaMang = (int) nhaMangRepository.count();
        Double tocDoTB = chatLuongDichVuRepository.findAll().stream()
                .mapToDouble(cl -> cl.getTocDoDownload().doubleValue())
                .average()
                .orElse(0.0);

        return ThongKeTongQuanDTO.builder()
                .tongTram5G(thongKe5G.getSoTram5G())
                .tyLePhuSong5G(thongKe5G.getTyLePhu5G())
                .soNhaMang(soNhaMang)
                .tocDoTrungBinh(tocDoTB)
                .tongDanSo(thongKe5G.getTongDanSo())
                .danSoDuocPhu5G(thongKe5G.getDanSoDuocPhu5G())
                .build();
    }

    // 2. Thống kê 5G
    public ThongKe5GDTO getThongKe5G() {
        Long tongDanSo = khuVucRepository.findAll().stream()
                .mapToLong(kv -> kv.getDanSo() != null ? kv.getDanSo() : 0)
                .sum();

        List<VungPhuSong> vungPhu5G = vungPhuSongRepository.findAll5GCoverage();
        
        Long danSoPhu5G = vungPhu5G.stream()
                .mapToLong(vp -> vp.getDanSoDuocPhu() != null ? vp.getDanSoDuocPhu() : 0)
                .sum();

        Long soTram5G = tramPhatSongRepository.findAllActive5GStations().stream().count();

        BigDecimal tyLePhu = tongDanSo > 0 
                ? BigDecimal.valueOf(danSoPhu5G)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(tongDanSo), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return ThongKe5GDTO.builder()
                .tongDanSo(tongDanSo)
                .danSoDuocPhu5G(danSoPhu5G)
                .tyLePhu5G(tyLePhu)
                .soTram5G(soTram5G)
                .build();
    }

    // 3. Top tỉnh có độ phủ cao
    public List<TopTinhPhuSongDTO> getTopTinhPhuSong(Integer limit) {
        Map<String, List<VungPhuSong>> vungPhuByTinh = vungPhuSongRepository.findAll().stream()
                .collect(Collectors.groupingBy(vp -> vp.getKhuVuc().getTenTinhThanh()));

        return vungPhuByTinh.entrySet().stream()
                .map(entry -> {
                    String tinhThanh = entry.getKey();
                    List<VungPhuSong> vungPhuList = entry.getValue();

                    BigDecimal tyLeTB = vungPhuList.stream()
                            .map(VungPhuSong::getTyLePhuSong)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .divide(BigDecimal.valueOf(vungPhuList.size()), 2, RoundingMode.HALF_UP);

                    Long soTram = vungPhuList.stream()
                            .map(vp -> vp.getTramPhatSong().getMaTram())
                            .distinct()
                            .count();

                    Long danSo = vungPhuList.stream()
                            .mapToLong(vp -> vp.getDanSoDuocPhu() != null ? vp.getDanSoDuocPhu() : 0)
                            .sum();

                    return TopTinhPhuSongDTO.builder()
                            .tinhThanh(tinhThanh)
                            .tyLePhu(tyLeTB)
                            .soTram(soTram)
                            .danSoDuocPhu(danSo)
                            .build();
                })
                .sorted((a, b) -> b.getTyLePhu().compareTo(a.getTyLePhu()))
                .limit(limit != null ? limit : 10)
                .collect(Collectors.toList());
    }

    // 4. Dữ liệu heatmap
    public List<HeatmapDataDTO> getHeatmapData(String congNghe) {
        List<VungPhuSong> vungPhuList = congNghe != null && congNghe.equals("5G")
                ? vungPhuSongRepository.findAll5GCoverage()
                : vungPhuSongRepository.findAll();

        return vungPhuList.stream()
                .map(vp -> {
                    KhuVuc khuVuc = vp.getKhuVuc();
                    BigDecimal tyLe = vp.getTyLePhuSong() != null ? vp.getTyLePhuSong() : BigDecimal.ZERO;
                    
                    String color = getColorByPercentage(tyLe.doubleValue());
                    
                    return HeatmapDataDTO.builder()
                            .tinhThanh(khuVuc.getTenTinhThanh())
                            .quanHuyen(khuVuc.getTenQuanHuyen())
                            .tyLePhuSong(tyLe)
                            .lat(BigDecimal.ZERO)
                            .lng(BigDecimal.ZERO)
                            .color(color)
                            .intensity(tyLe.intValue())
                            .build();
                })
                .collect(Collectors.toList());
    }

    // 5. So sánh nhà mạng
public List<SoSanhNhaMangDTO> soSanhNhaMang(String congNghe) {
    return nhaMangRepository.findAll().stream()
            .map(nhaMang -> {
                // FIX: Không dùng countByNhaMangAndCongNghe nữa
                // Thay bằng filter trong Java
                List<TramPhatSong> allTram = tramPhatSongRepository
                        .findByNhaMang_MaNhaMang(nhaMang.getMaNhaMang());
                
                Long soTram = allTram.stream()
                        .filter(tram -> {
                            if (congNghe == null || congNghe.isEmpty()) {
                                return true; // Đếm tất cả
                            }
                            String theHe = tram.getCongNgheMang().getTheHe().getValue();
                            return theHe.equals(congNghe);
                        })
                        .count();

                Double tocDoDownload = chatLuongDichVuRepository
                        .getAverageDownloadSpeed(nhaMang.getMaNhaMang());

                return SoSanhNhaMangDTO.builder()
                        .tenNhaMang(nhaMang.getTenNhaMang())
                        .soTram(soTram)
                        .thiPhan(nhaMang.getThiPhan())
                        .tocDoDownload(tocDoDownload != null ? tocDoDownload : 0.0)
                        .tocDoUpload(0.0)
                        .doTre(0)
                        .build();
            })
            .sorted((a, b) -> Long.compare(b.getSoTram(), a.getSoTram()))
            .collect(Collectors.toList());
}


    // 6. Thống kê phủ sóng theo nhà mạng (THÊM METHOD NÀY)
    public List<ThongKePhuSongDTO> getThongKePhuSongTheoNhaMang() {
        List<VungPhuSong> allCoverage = vungPhuSongRepository.findAll();
        
        Map<String, Map<String, List<VungPhuSong>>> grouped = allCoverage.stream()
                .collect(Collectors.groupingBy(
                    vp -> vp.getTramPhatSong().getNhaMang().getTenNhaMang(),
                    Collectors.groupingBy(vp -> vp.getCongNgheMang().getTenCongNghe())
                ));

        List<ThongKePhuSongDTO> result = new ArrayList<>();
        
        grouped.forEach((nhaMang, congNgheMap) -> {
            congNgheMap.forEach((congNghe, vungPhuList) -> {
                Long soTram = vungPhuList.stream()
                        .map(vp -> vp.getTramPhatSong().getMaTram())
                        .distinct()
                        .count();

                BigDecimal tyLeTB = vungPhuList.stream()
                        .map(VungPhuSong::getTyLePhuSong)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.valueOf(vungPhuList.size()), 2, RoundingMode.HALF_UP);

                Long tongDanSo = vungPhuList.stream()
                        .mapToLong(vp -> vp.getDanSoDuocPhu() != null ? vp.getDanSoDuocPhu() : 0)
                        .sum();

                result.add(ThongKePhuSongDTO.builder()
                        .tenNhaMang(nhaMang)
                        .tenCongNghe(congNghe)
                        .soLuongTram(soTram)
                        .tyLePhuSongTrungBinh(tyLeTB)
                        .tongDanSoDuocPhu(tongDanSo)
                        .build());
            });
        });

        return result;
    }

    // 7. Khu vực cần mở rộng
    public List<Map<String, Object>> getKhuVucCanMoRong(BigDecimal tyLeToiThieu) {
        return khuVucRepository.findAll().stream()
                .map(khuVuc -> {
                    List<VungPhuSong> vungPhu = vungPhuSongRepository
                            .findByKhuVuc_MaKhuVuc(khuVuc.getMaKhuVuc());

                    BigDecimal tyLeTrungBinh = vungPhu.isEmpty() 
                            ? BigDecimal.ZERO
                            : vungPhu.stream()
                                .map(VungPhuSong::getTyLePhuSong)
                                .filter(Objects::nonNull)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(BigDecimal.valueOf(vungPhu.size()), 2, RoundingMode.HALF_UP);

                    Map<String, Object> result = new HashMap<>();
                    result.put("tenTinhThanh", khuVuc.getTenTinhThanh());
                    result.put("tenQuanHuyen", khuVuc.getTenQuanHuyen());
                    result.put("tenPhuongXa", khuVuc.getTenPhuongXa());
                    result.put("danSo", khuVuc.getDanSo());
                    result.put("tyLePhuSongHienTai", tyLeTrungBinh);
                    result.put("soTramHienCo", vungPhu.size());
                    result.put("loaiKhuVuc", khuVuc.getLoaiKhuVuc().getValue());
                    
                    return result;
                })
                .filter(map -> ((BigDecimal) map.get("tyLePhuSongHienTai")).compareTo(tyLeToiThieu) < 0)
                .sorted((a, b) -> ((Integer) b.get("danSo")).compareTo((Integer) a.get("danSo")))
                .collect(Collectors.toList());
    }

    private String getColorByPercentage(double percentage) {
        if (percentage >= 80) return "#00FF00";
        if (percentage >= 50) return "#90EE90";
        if (percentage >= 30) return "#FFFF00";
        return "#FF0000";
    }
}