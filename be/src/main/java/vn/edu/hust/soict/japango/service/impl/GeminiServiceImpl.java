package vn.edu.hust.soict.japango.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.dto.feign.gemini.GeminiGenerateRequestDTO;
import vn.edu.hust.soict.japango.dto.feign.gemini.GeminiGenerateResponseDTO;
import vn.edu.hust.soict.japango.service.LanguageModelService;
import vn.edu.hust.soict.japango.service.feign.GeminiFeignClient;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

@Service
@Getter
@Setter
@Slf4j
public class GeminiServiceImpl implements LanguageModelService {
    @Autowired
    private GeminiFeignClient geminiFeignClient;

    @Value("${gemini.api-keys}")
    private String[] apiKeys;

    private Queue<String> keyQueue;
    private String currentKey;

    private int retryLimit = 10;

    @PostConstruct
    public void init() {
        keyQueue = new LinkedList<>();
        keyQueue.addAll(Arrays.asList(apiKeys));
        currentKey = keyQueue.peek();
    }

    private void changeKey() {
        keyQueue.offer(currentKey);
        keyQueue.poll();
        currentKey = keyQueue.peek();
    }

    @Override
    public String generateContent(String text) {
        try {
            ResponseEntity<GeminiGenerateResponseDTO> response = geminiFeignClient.generate(GeminiGenerateRequestDTO.ofText(text), currentKey);
            GeminiGenerateResponseDTO result = response.getBody();
            retryLimit = 10;
            return String.join("\n", result.getTexts()).trim();
        } catch (Exception ex) {
            log.warn("Could not get response from Gemini. Trying another api key...");
            if (retryLimit == 0)
                throw new RuntimeException(ex);
            retryLimit--;
            changeKey();
            return generateContent(text);
        }
    }
}
