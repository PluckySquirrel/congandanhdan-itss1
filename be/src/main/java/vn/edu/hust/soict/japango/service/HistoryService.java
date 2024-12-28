package vn.edu.hust.soict.japango.service;

import org.springframework.data.domain.Page;
import vn.edu.hust.soict.japango.dto.history.DeleteHistoryResponseDTO;
import vn.edu.hust.soict.japango.dto.history.EditOutputRequestDTO;
import vn.edu.hust.soict.japango.dto.history.GetHistoryRequestDTO;
import vn.edu.hust.soict.japango.dto.history.HistoryDTO;

public interface HistoryService {
    Page<HistoryDTO> getHistory(GetHistoryRequestDTO request);
    DeleteHistoryResponseDTO deleteHistory();
    void deleteItem(String uuid);
    void likeItem(String uuid);
    void unlikeItem(String uuid);
    void editOutput(String uuid, EditOutputRequestDTO request);
}
