package vn.edu.hust.soict.japango.service.impl;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.soict.japango.service.FileService;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService implements FileService {
    private final Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile file) {
        try {
            log.info("Uploading image: {}", file.getOriginalFilename());
            Map response = cloudinary.uploader().upload(file.getBytes(), Map.of());
            log.info("Cloudinary response: {}", response);

            return response;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to upload image: " + file.getOriginalFilename(), ex);
        }
    }
}
