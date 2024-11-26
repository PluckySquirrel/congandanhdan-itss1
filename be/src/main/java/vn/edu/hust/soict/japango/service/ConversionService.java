package vn.edu.hust.soict.japango.service;

import org.springframework.data.domain.Page;
import vn.edu.hust.soict.japango.dto.conversion.HistoryDTO;
import vn.edu.hust.soict.japango.dto.conversion.InputDTO;
import vn.edu.hust.soict.japango.dto.conversion.OutputDTO;
import vn.edu.hust.soict.japango.dto.conversion.TranslateInputDTO;

public interface ConversionService {
    OutputDTO expressIntent(InputDTO inputDTO);
    OutputDTO toEasyJapanese(InputDTO inputDTO);
    OutputDTO translate(TranslateInputDTO inputDTO);
    Page<HistoryDTO> getHistory(int page, int size);
}
