package vn.edu.hust.soict.japango.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hust.soict.japango.dto.conversion.HistoryDTO;
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
    public ResponseEntity<OutputDTO> expressIntent(@RequestBody @Valid InputDTO inputDTO) {
        return ResponseEntity.ok(conversionService.expressIntent(inputDTO));
    }

    @PostMapping("/easy-japanese")
    public ResponseEntity<OutputDTO> toEasyJapanese(@RequestBody @Valid InputDTO inputDTO) {
        return ResponseEntity.ok(conversionService.toEasyJapanese(inputDTO));
    }

    @PostMapping("/translate")
    public ResponseEntity<OutputDTO> translate(@RequestBody @Valid TranslateInputDTO inputDTO) {
        return ResponseEntity.ok(conversionService.translate(inputDTO));
    }

    @GetMapping("/history")
    public ResponseEntity<Page<HistoryDTO>> getHistory(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "${app.request.default-page-size}") int size
    ) {
        return ResponseEntity.ok(conversionService.getHistory(page, size));
    }
}
