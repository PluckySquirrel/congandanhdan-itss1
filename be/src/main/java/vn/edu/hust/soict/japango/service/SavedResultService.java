package vn.edu.hust.soict.japango.service;

import org.springframework.data.domain.Page;
import vn.edu.hust.soict.japango.dto.saved_result.DeleteSavedResultsResponseDTO;
import vn.edu.hust.soict.japango.dto.saved_result.GetSavedResultsRequestDTO;
import vn.edu.hust.soict.japango.dto.saved_result.SavedResultDTO;

public interface SavedResultService {
    Page<SavedResultDTO> getSavedResults(GetSavedResultsRequestDTO request);
    DeleteSavedResultsResponseDTO deleteSavedResults();
    void deleteSavedResult(String uuid);
}
