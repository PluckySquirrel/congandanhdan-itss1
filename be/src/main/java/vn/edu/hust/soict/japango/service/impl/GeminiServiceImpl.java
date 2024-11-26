package vn.edu.hust.soict.japango.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.dto.feign.gemini.GeminiGenerateRequestDTO;
import vn.edu.hust.soict.japango.dto.feign.gemini.GeminiGenerateResponseDTO;
import vn.edu.hust.soict.japango.service.feign.GeminiFeignClient;
import vn.edu.hust.soict.japango.service.LanguageModelService;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiServiceImpl implements LanguageModelService {
    private final GeminiFeignClient geminiFeignClient;

    @Value("${gemini.api-key}")
    private String apiKey;

    @Override
    public String generateContent(String text) {
        ResponseEntity<GeminiGenerateResponseDTO> response = geminiFeignClient.generate(GeminiGenerateRequestDTO.ofText(text), apiKey);
        GeminiGenerateResponseDTO result = response.getBody();
        return String.join("\n", result.getTexts()).trim();
    }
}
