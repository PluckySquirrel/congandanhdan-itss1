package vn.edu.hust.soict.japango.service;

import org.springframework.data.domain.Page;
import vn.edu.hust.soict.japango.dto.history.DeleteHistoryResponse;
import vn.edu.hust.soict.japango.dto.history.GetHistoryRequest;
import vn.edu.hust.soict.japango.dto.history.HistoryDTO;

public interface HistoryService {
    Page<HistoryDTO> getHistory(GetHistoryRequest request);
    DeleteHistoryResponse deleteHistory();
    void likeItem(String uuid);
    void unlikeItem(String uuid);
}
