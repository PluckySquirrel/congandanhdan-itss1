package vn.edu.hust.soict.japango.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hust.soict.japango.dto.conversion.InputDTO;
import vn.edu.hust.soict.japango.dto.conversion.OutputDTO;
import vn.edu.hust.soict.japango.dto.conversion.TranslateInputDTO;
import vn.edu.hust.soict.japango.service.ConversionService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ConversionController {
    private final ConversionService conversionService;

    @PostMapping("/express-intent")
    public ResponseEntity<OutputDTO> expressIntent(@RequestBody InputDTO inputDTO) {
        return ResponseEntity.ok(conversionService.expressIntent(inputDTO));
    }

    @PostMapping("/easy-japanese")
    public ResponseEntity<OutputDTO> toEasyJapanese(@RequestBody InputDTO inputDTO) {
        return ResponseEntity.ok(conversionService.toEasyJapanese(inputDTO));
    }
}
