package vn.edu.hust.soict.japango.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hust.soict.japango.dto.saved_result.DeleteSavedResultsResponseDTO;
import vn.edu.hust.soict.japango.dto.saved_result.GetSavedResultsRequestDTO;
import vn.edu.hust.soict.japango.dto.saved_result.SavedResultDTO;
import vn.edu.hust.soict.japango.service.SavedResultService;

@RestController
@RequestMapping("/api/v1/saved")
@RequiredArgsConstructor
public class SavedResultController {
    private final SavedResultService savedResultService;

    @GetMapping
    public ResponseEntity<Page<SavedResultDTO>> getSavedResults(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "${app.request.default-page-size}") int size
    ) {
        return ResponseEntity.ok(savedResultService.getSavedResults(GetSavedResultsRequestDTO.builder()
                .keyword(keyword)
                .page(page)
                .size(size)
                .build())
        );
    }

    @DeleteMapping
    public ResponseEntity<DeleteSavedResultsResponseDTO> deleteSavedResults() {
        return ResponseEntity.ok(savedResultService.deleteSavedResults());
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteSavedResult(@PathVariable String uuid) {
        savedResultService.deleteSavedResult(uuid);
        return ResponseEntity.ok(null);
    }
}
