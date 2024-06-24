package com.example.pharmacy_api.controller;

import com.example.pharmacy_api.model.entity.ImageOrder;
import com.example.pharmacy_api.repository.ImageOrderRepository;
import com.example.pharmacy_api.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageOrderRepository imageOrderRepository;

    public byte[] downloadImage(String fileName){
        ImageOrder imageOrder = imageOrderRepository.findTopByName(fileName);
        return ImageUtils.decompressImage(imageOrder.getImageDate());
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> getImageByName(@PathVariable String fileName) throws IOException {
        byte[] imageData = downloadImage(fileName);
        return ResponseEntity.ok().contentType(MediaType.valueOf("image/png")).body(imageData);
    }
}
