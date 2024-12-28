package vn.edu.hust.soict.japango.service;

import vn.edu.hust.soict.japango.dto.conversion.InputDTO;
import vn.edu.hust.soict.japango.dto.conversion.OutputDTO;
import vn.edu.hust.soict.japango.dto.conversion.TranslateInputDTO;

public interface ConversionService {
    OutputDTO expressIntent(InputDTO inputDTO);
    OutputDTO toEasyJapanese(InputDTO inputDTO);
    OutputDTO translate(TranslateInputDTO inputDTO);
}
