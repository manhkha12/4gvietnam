
package com.example.do_phu_song4g.demo.usecase;

import com.example.do_phu_song4g.demo.domain.models.*;
import com.example.do_phu_song4g.demo.infrastructure.repository.*;
import com.example.do_phu_song4g.demo.usecase.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoiCuocService {
    private final GoiCuocRepository goiCuocRepository;
    private final DangKyGoiCuocRepository dangKyGoiCuocRepository;
    private final NhaMangRepository nhaMangRepository;
    private final CongNgheMangRepository congNgheMangRepository;
    private final UserRepository userRepository;

    // Lấy tất cả gói cước
    public List<GoiCuocDTO> getAllGoiCuoc() {
        return goiCuocRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy gói cước đang áp dụng
    public List<GoiCuocDTO> getActiveGoiCuoc() {
        return goiCuocRepository.findActiveGoiCuoc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy gói cước theo nhà mạng
    public List<GoiCuocDTO> getGoiCuocByNhaMang(Integer maNhaMang) {
        return goiCuocRepository.findByNhaMang_MaNhaMang(maNhaMang).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy gói cước đang áp dụng theo nhà mạng
    public List<GoiCuocDTO> getActiveGoiCuocByNhaMang(Integer maNhaMang) {
        return goiCuocRepository.findActiveByNhaMang(maNhaMang).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy chi tiết 1 gói cước
    public GoiCuocDTO getGoiCuocById(Integer id) {
        return goiCuocRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy gói cước"));
    }

    // Tạo gói cước mới
    @Transactional
    public GoiCuocDTO createGoiCuoc(CreateGoiCuocRequest request) {
        NhaMang nhaMang = nhaMangRepository.findById(request.getMaNhaMang())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà mạng"));
        
        CongNgheMang congNghe = congNgheMangRepository.findById(request.getMaCongNghe())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công nghệ"));

        GoiCuoc goiCuoc = GoiCuoc.builder()
                .nhaMang(nhaMang)
                .congNgheMang(congNghe)
                .tenGoiCuoc(request.getTenGoiCuoc())
                .giaCuoc(request.getGiaCuoc())
                .dungLuongData(request.getDungLuongData())
                .thoiHanNgay(request.getThoiHanNgay())
                .moTaChiTiet(request.getMoTaChiTiet())
                .ngayApDung(request.getNgayApDung())
                .ngayHetHan(request.getNgayHetHan())
                .trangThai(GoiCuoc.TrangThai.DANG_AP_DUNG)
                .build();

        return convertToDTO(goiCuocRepository.save(goiCuoc));
    }

    // Cập nhật gói cước
    @Transactional
    public GoiCuocDTO updateGoiCuoc(Integer id, CreateGoiCuocRequest request) {
        GoiCuoc goiCuoc = goiCuocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy gói cước"));

        goiCuoc.setTenGoiCuoc(request.getTenGoiCuoc());
        goiCuoc.setGiaCuoc(request.getGiaCuoc());
        goiCuoc.setDungLuongData(request.getDungLuongData());
        goiCuoc.setThoiHanNgay(request.getThoiHanNgay());
        goiCuoc.setMoTaChiTiet(request.getMoTaChiTiet());
        goiCuoc.setNgayApDung(request.getNgayApDung());
        goiCuoc.setNgayHetHan(request.getNgayHetHan());

        return convertToDTO(goiCuocRepository.save(goiCuoc));
    }

    // Xóa gói cước
    @Transactional
    public void deleteGoiCuoc(Integer id) {
        goiCuocRepository.deleteById(id);
    }

    // Đăng ký gói cước
    @Transactional
    public DangKyGoiCuocDTO dangKyGoiCuoc(DangKyGoiCuocRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        GoiCuoc goiCuoc = goiCuocRepository.findById(request.getMaGoiCuoc())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy gói cước"));

        // Kiểm tra đã đăng ký chưa
        boolean daDangKy = dangKyGoiCuocRepository.findByUser_UserId(user.getUserId())
                .stream()
                .anyMatch(dk -> dk.getGoiCuoc().getMaGoiCuoc().equals(goiCuoc.getMaGoiCuoc()) 
                             && dk.getTrangThai() == DangKyGoiCuoc.TrangThai.DANG_HOAT_DONG);

        if (daDangKy) {
            throw new RuntimeException("Bạn đã đăng ký gói cước này rồi");
        }

        LocalDateTime ngayDangKy = LocalDateTime.now();
        LocalDateTime ngayHetHan = ngayDangKy.plusDays(goiCuoc.getThoiHanNgay());

        DangKyGoiCuoc dangKy = DangKyGoiCuoc.builder()
                .user(user)
                .goiCuoc(goiCuoc)
                .ngayDangKy(ngayDangKy)
                .ngayHetHan(ngayHetHan)
                .trangThai(DangKyGoiCuoc.TrangThai.DANG_HOAT_DONG)
                .build();

        return convertDangKyToDTO(dangKyGoiCuocRepository.save(dangKy));
    }

    // Lấy lịch sử đăng ký theo userId
    public List<DangKyGoiCuocDTO> getLichSuDangKyByUser(Integer userId) {
        return dangKyGoiCuocRepository.findByUser_UserId(userId).stream()
                .map(this::convertDangKyToDTO)
                .collect(Collectors.toList());
    }

    // Hủy đăng ký
    @Transactional
    public void huyDangKy(Integer maDangKy, Integer userId) {
        DangKyGoiCuoc dangKy = dangKyGoiCuocRepository.findById(maDangKy)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đăng ký"));

        if (!dangKy.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Bạn không có quyền hủy đăng ký này");
        }

        dangKy.setTrangThai(DangKyGoiCuoc.TrangThai.HUY);
        dangKyGoiCuocRepository.save(dangKy);
    }

    // Gia hạn gói cước
    @Transactional
    public DangKyGoiCuocDTO giaHanGoiCuoc(Integer maDangKy, Integer userId) {
        DangKyGoiCuoc dangKy = dangKyGoiCuocRepository.findById(maDangKy)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đăng ký"));

        if (!dangKy.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Bạn không có quyền gia hạn đăng ký này");
        }

        LocalDateTime ngayHetHanMoi = dangKy.getNgayHetHan()
                .plusDays(dangKy.getGoiCuoc().getThoiHanNgay());
        
        dangKy.setNgayHetHan(ngayHetHanMoi);
        dangKy.setTrangThai(DangKyGoiCuoc.TrangThai.DANG_HOAT_DONG);

        return convertDangKyToDTO(dangKyGoiCuocRepository.save(dangKy));
    }

    // Thống kê số lượng đăng ký (THÊM METHOD NÀY)
    public Long getSoLuongDangKy(Integer maGoiCuoc) {
        return dangKyGoiCuocRepository.countByGoiCuoc(maGoiCuoc);
    }

    // THÊM: Convert GoiCuoc to DTO
    private GoiCuocDTO convertToDTO(GoiCuoc gc) {
        return GoiCuocDTO.builder()
                .maGoiCuoc(gc.getMaGoiCuoc())
                .maNhaMang(gc.getNhaMang().getMaNhaMang())
                .tenNhaMang(gc.getNhaMang().getTenNhaMang())
                .maCongNghe(gc.getCongNgheMang().getMaCongNghe())
                .tenCongNghe(gc.getCongNgheMang().getTenCongNghe())
                .theHe(gc.getCongNgheMang().getTheHe().getValue())
                .tenGoiCuoc(gc.getTenGoiCuoc())
                .giaCuoc(gc.getGiaCuoc())
                .dungLuongData(gc.getDungLuongData())
                .thoiHanNgay(gc.getThoiHanNgay())
                .moTaChiTiet(gc.getMoTaChiTiet())
                .ngayApDung(gc.getNgayApDung())
                .ngayHetHan(gc.getNgayHetHan())
                .trangThai(gc.getTrangThai().getValue())
                .build();
    }

    // THÊM: Convert DangKyGoiCuoc to DTO
    private DangKyGoiCuocDTO convertDangKyToDTO(DangKyGoiCuoc dk) {
        return DangKyGoiCuocDTO.builder()
                .maDangKy(dk.getMaDangKy())
                .userId(dk.getUser().getUserId())
                .soDienThoai(dk.getUser().getSoDienThoai())
                .hoTen(dk.getUser().getHoTen())
                .maGoiCuoc(dk.getGoiCuoc().getMaGoiCuoc())
                .tenGoiCuoc(dk.getGoiCuoc().getTenGoiCuoc())
                .tenNhaMang(dk.getGoiCuoc().getNhaMang().getTenNhaMang())
                .giaCuoc(dk.getGoiCuoc().getGiaCuoc())
                .ngayDangKy(dk.getNgayDangKy())
                .ngayHetHan(dk.getNgayHetHan())
                .trangThai(dk.getTrangThai().getValue())
                .build();
    }
}