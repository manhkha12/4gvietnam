package com.example.do_phu_song4g.demo.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.do_phu_song4g.demo.usecase.NhaMangService;
import com.example.do_phu_song4g.demo.usecase.dto.NhaMangDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/nha-mang")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NhaMangController {
    private final NhaMangService nhaMangService;

    @GetMapping
    public ResponseEntity<List<NhaMangDTO>> getAllNhaMang() {
        return ResponseEntity.ok(nhaMangService.getAllNhaMang());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhaMangDTO> getNhaMangById(@PathVariable Integer id) {
        return ResponseEntity.ok(nhaMangService.getNhaMangById(id));
    }

    @PostMapping
    public ResponseEntity<NhaMangDTO> createNhaMang(@RequestBody NhaMangDTO dto) {
        return ResponseEntity.ok(nhaMangService.createNhaMang(dto));
    }
}
