package vn.edu.hust.soict.japango.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.common.enums.ActionType;
import vn.edu.hust.soict.japango.common.utils.SecurityUtils;
import vn.edu.hust.soict.japango.dto.conversion.HistoryDTO;
import vn.edu.hust.soict.japango.dto.conversion.InputDTO;
import vn.edu.hust.soict.japango.dto.conversion.OutputDTO;
import vn.edu.hust.soict.japango.dto.conversion.TranslateInputDTO;
import vn.edu.hust.soict.japango.entity.History;
import vn.edu.hust.soict.japango.exception.CustomExceptions;
import vn.edu.hust.soict.japango.repository.HistoryRepository;
import vn.edu.hust.soict.japango.service.ConversionService;
import vn.edu.hust.soict.japango.service.LanguageModelService;
import vn.edu.hust.soict.japango.service.mapper.HistoryMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversionServiceImpl implements ConversionService {
    private final LanguageModelService languageModelService;
    private final HistoryRepository historyRepository;
    private final SecurityUtils securityUtils;
    private final HistoryMapper historyMapper;

    @Override
    public OutputDTO expressIntent(InputDTO inputDTO) {
        String text = """
                次の文を話し手の意図や希望がより明確になるように言い換えてください。例えば、「締め切りは金曜日なので、終わったら知らせてください」という文を、より具体的に「締め切りは金曜日ですが、私はできるだけ早く課題を確認し、フィードバックを提供したいと考えています。そのため、課題が終わり次第、すぐにご連絡いただけるとありがたいです。」のように書き直してください。言い換えた文だけを書いて、他のことは書かないでください。N3以下のレベルの簡単な言葉を使ってください。
                文:
                「%s」
                """
                .formatted(inputDTO.getInput());
        String output = languageModelService.generateContent(text);

        Optional.ofNullable(securityUtils.getUserId()).ifPresent(userId -> {
            History history = History.builder()
                    .userId(userId)
                    .action(ActionType.INTENT_EXPRESSION)
                    .input(inputDTO.getInput())
                    .output(output)
                    .build();
            historyRepository.save(history);
        });

        return OutputDTO.builder().output(output).build();
    }

    @Override
    public OutputDTO toEasyJapanese(InputDTO inputDTO) {
        String text = """
                次の文をN3以下の初級の簡単な言葉を使って、元の意味に近い形で書き直してください。書き直した文だけ書いてください。
                文:
                「%s」
                """
                .formatted(inputDTO.getInput());
        String output = languageModelService.generateContent(text);

        Optional.ofNullable(securityUtils.getUserId()).ifPresent(userId -> {
            History history = History.builder()
                    .userId(userId)
                    .action(ActionType.EASY_JAPANESE_MODE)
                    .input(inputDTO.getInput())
                    .output(output)
                    .build();
            historyRepository.save(history);
        });

        return OutputDTO.builder().output(output).build();
    }

    @Override
    public OutputDTO translate(TranslateInputDTO inputDTO) {
        String text = """
                次の文を日本語の元の意味に近い形で、スムーズで自然な%sに翻訳してください。翻訳した文だけ書いてください。
                文:
                「%s」
                """
                .formatted(inputDTO.getTargetLanguage().getInJapanese(), inputDTO.getInput());
        String output = languageModelService.generateContent(text);

        Optional.ofNullable(securityUtils.getUserId()).ifPresent(userId -> {
            History history = History.builder()
                    .userId(userId)
                    .action(ActionType.TRANSLATION)
                    .targetLanguage(inputDTO.getTargetLanguage())
                    .input(inputDTO.getInput())
                    .output(output)
                    .build();
            historyRepository.save(history);
        });

        return OutputDTO.builder().output(output).build();
    }

    @Override
    public Page<HistoryDTO> getHistory(int page, int size) {
        Long userId;
        if ((userId = securityUtils.getUserId()) == null) {
            throw CustomExceptions.LOGIN_REQUIRED_EXCEPTION;
        }
        return historyRepository
                .findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size))
                .map(historyMapper::toDTO);
    }
}
