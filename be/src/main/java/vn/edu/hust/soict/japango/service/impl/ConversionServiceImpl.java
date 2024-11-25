package vn.edu.hust.soict.japango.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.common.enums.Prompt;
import vn.edu.hust.soict.japango.dto.conversion.InputDTO;
import vn.edu.hust.soict.japango.dto.conversion.OutputDTO;
import vn.edu.hust.soict.japango.exception.CustomExceptions;
import vn.edu.hust.soict.japango.service.ConversionService;
import vn.edu.hust.soict.japango.service.LanguageModelService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversionServiceImpl implements ConversionService {
    private final LanguageModelService languageModelService;

    @Override
    public OutputDTO expressIntent(InputDTO inputDTO) {
        if (inputDTO == null || inputDTO.getInput() == null || inputDTO.getInput().isEmpty()) {
            throw CustomExceptions.INPUT_IS_EMPTY_EXCEPTION;
        }

        String text = Prompt.EXPRESS_INTENT_PROMPT.apply(inputDTO.getInput());
        String result = languageModelService.generateContent(text);
        return OutputDTO.builder().output(result).build();
    }
}
