package vn.edu.hust.soict.japango.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.dto.conversion.InputDTO;
import vn.edu.hust.soict.japango.dto.conversion.OutputDTO;
import vn.edu.hust.soict.japango.dto.conversion.TranslateInputDTO;
import vn.edu.hust.soict.japango.service.ConversionService;
import vn.edu.hust.soict.japango.service.LanguageModelService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversionServiceImpl implements ConversionService {
    private final LanguageModelService languageModelService;

    @Override
    public OutputDTO expressIntent(InputDTO inputDTO) {
        String text = """
                次の文を話し手の意図や希望がより明確になるように言い換えてください。例えば、「締め切りは金曜日なので、終わったら知らせてください」という文を、より具体的に「締め切りは金曜日ですが、私はできるだけ早く課題を確認し、フィードバックを提供したいと考えています。そのため、課題が終わり次第、すぐにご連絡いただけるとありがたいです。」のように書き直してください。言い換えた文だけを書いて、他のことは書かないでください。N3以下のレベルの簡単な言葉を使ってください。
                文:
                「%s」
                """
                .formatted(inputDTO.getInput());
        String result = languageModelService.generateContent(text);
        return OutputDTO.builder().output(result).build();
    }

    @Override
    public OutputDTO toEasyJapanese(InputDTO inputDTO) {
        String text = """
                次の文をN3以下の初級の簡単な言葉を使って、元の意味に近い形で書き直してください。
                文:
                「%s」
                """
                .formatted(inputDTO.getInput());
        String result = languageModelService.generateContent(text);
        return OutputDTO.builder().output(result).build();
    }

    @Override
    public OutputDTO translate(TranslateInputDTO inputDTO) {
        String text = """
                次の文を日本語の元の意味に近い形で、スムーズで自然な%sに翻訳してください。
                文:
                「%s」
                """
                .formatted(inputDTO.getTargetLanguage().getInJapanese(), inputDTO.getInput());
        String result = languageModelService.generateContent(text);
        return OutputDTO.builder().output(result).build();
    }
}
