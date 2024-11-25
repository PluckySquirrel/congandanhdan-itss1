package vn.edu.hust.soict.japango.service;

import vn.edu.hust.soict.japango.dto.conversion.InputDTO;
import vn.edu.hust.soict.japango.dto.conversion.OutputDTO;

public interface ConversionService {
    OutputDTO expressIntent(InputDTO inputDTO);
}
