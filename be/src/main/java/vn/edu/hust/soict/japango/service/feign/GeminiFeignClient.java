package vn.edu.hust.soict.japango.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.hust.soict.japango.dto.feign.gemini.GeminiGenerateRequestDTO;
import vn.edu.hust.soict.japango.dto.feign.gemini.GeminiGenerateResponseDTO;

@FeignClient(name = "gemini", url = "${gemini.url}")
public interface GeminiFeignClient {
    @PostMapping("/models/${gemini.default-model}:generateContent")
    ResponseEntity<GeminiGenerateResponseDTO> generate(
            @RequestBody GeminiGenerateRequestDTO requestDTO,
            @RequestParam(name = "key") String apiKey
    );
}
