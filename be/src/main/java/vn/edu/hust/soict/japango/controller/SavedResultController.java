package vn.edu.hust.soict.japango.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hust.soict.japango.dto.saved_result.SavedResultDTO;
import vn.edu.hust.soict.japango.service.SavedResultService;

@RestController
@RequestMapping("/api/v1/saved")
@RequiredArgsConstructor
public class SavedResultController {
    private final SavedResultService savedResultService;

    @GetMapping
    public ResponseEntity<Page<SavedResultDTO>> getSavedResults(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "${app.request.default-page-size}") int size
    ) {
        return ResponseEntity.ok(savedResultService.getSavedResults(page, size));
    }
}
