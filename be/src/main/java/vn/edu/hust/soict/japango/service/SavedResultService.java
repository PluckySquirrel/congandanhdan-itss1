package vn.edu.hust.soict.japango.service;

import org.springframework.data.domain.Page;
import vn.edu.hust.soict.japango.dto.saved_result.DeleteSavedResultsResponse;
import vn.edu.hust.soict.japango.dto.saved_result.SavedResultDTO;

public interface SavedResultService {
    Page<SavedResultDTO> getSavedResults(int page, int size);
    DeleteSavedResultsResponse deleteSavedResults();
}
