package vn.edu.hust.soict.japango.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.common.enums.ActionType;
import vn.edu.hust.soict.japango.common.utils.SecurityUtils;
import vn.edu.hust.soict.japango.dto.conversion.InputDTO;
import vn.edu.hust.soict.japango.dto.conversion.OutputDTO;
import vn.edu.hust.soict.japango.dto.conversion.TranslateInputDTO;
import vn.edu.hust.soict.japango.entity.History;
import vn.edu.hust.soict.japango.repository.HistoryRepository;
import vn.edu.hust.soict.japango.service.ConversionService;
import vn.edu.hust.soict.japango.service.LanguageModelService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversionServiceImpl implements ConversionService {
    private final LanguageModelService languageModelService;
    private final HistoryRepository historyRepository;
    private final SecurityUtils securityUtils;

    @Override
    public OutputDTO expressIntent(InputDTO inputDTO) {
//        String text = """
//                次の文を話し手の意図や希望がより明確になるように言い換えてください。:「%s」
//                それは「締め切りは金曜日なので、終わったら知らせてください」という文だったら、「締め切りは金曜日ですが、私はできるだけ早く課題を確認し、フィードバックを提供したいと考えています。そのため、課題が終わり次第、すぐにご連絡いただけるとありがたいです。」を出力してください、
//                また、%s、それ以外の文であってもその感じで書き直してください。言い換えた文の一つだけを書いて、他のことは書かないでください。N3以下のレベルの簡単な言葉を使ってください。
//                """
        String text = """
                Please rephrase the following Japanese sentences into other Japanese sentences AS LONG AS POSSIBLE so that the speaker's intent or desire becomes as clear as possible (like what they want next, what they expect and why, ...): '%s'
                If the sentence is '締め切りは金曜日なので、終わったら知らせてください' rephrase it as '締め切りは金曜日ですが、私はできるだけ早く課題を確認し、フィードバックを提供したいと考えています。そのため、課題が終わり次第、すぐにご連絡いただけるとありがたいです。'
                %s
                If the sentence is not of the above cases, also rewrite it in the same manner. Write only the rephrased sentence and do not include anything else. Use simple words that are at or below the N3 level 
                """
                .formatted(inputDTO.getInput(), getTrainingSentences());
        String output = languageModelService.generateContent(text);

        String uuid = Optional.ofNullable(securityUtils.getUserId()).map(userId -> {
            History history = History.builder()
                    .userId(userId)
                    .action(ActionType.INTENT_EXPRESSION)
                    .input(inputDTO.getInput())
                    .output(output)
                    .build();
            historyRepository.save(history);
            return history.getUuid();
        }).orElse(null);

        return OutputDTO.builder().output(output).uuid(uuid).build();
    }

    private String getTrainingSentences() {
        Map<String, String> map1 = Map.of(
                "すみません、ちょっと…", "すみません、ちょっと都合が悪いです。",
                "田中さんは？", "田中さんはどこにいますか",
                "この資料、明日までに見ていただければと思います。", "この資料、明日までに見ていただければ助かると思います。",
                "私でよければ", "私でよければ手伝いますよ。",
                "それはちょっと", "それはちょっと難しいかもしれませんね。",
                "これについては", "これについては少し考えさせてください。",
                "「この映画、どうだった？」「うーん、でも、なんか…」", "「この映画、どうだった？」「うーん、でも、なんか最後がよく分からなかった」",
                "とりあえず、コーヒーでも飲みに行こうか…", "とりあえず、コーヒーでも飲みに行こうか…そのあと考えよう。",
                "「最近のプロジェクトはどう？」「まぁ、そんな感じで…」", "「最近のプロジェクトはどう？」「まぁ、そんな感じで…順調だけど、いろいろ大変。」",
                "「この資料、完成しましたか？」「一応…」", "「この資料、完成しましたか？」「一応形にはなっています」"
        );
        Map<String, String> map2 = Map.of(
                "もうちょっとだけ…", "もうちょっとだけ時間をください。",
                "まぁ、それはそれで…", "まぁ、それはそれでいいかもしれない。",
                "どういうか…", "どういうか…ちょっと複雑で説明が難しいですね。",
                "なんとかなるかな…", "なんとかなるかな…少し不安だけど。",
                "「この案について、どう思いますか？」「いちおう考えてみます…」", "「この案について、どう思いますか？」「いちおう考えてみます…ただ、他にも調べてみたいです。」"
        );
        Map<String, String> sentences = new HashMap<>();
        sentences.putAll(map1);
        sentences.putAll(map2);

        StringBuilder sb = new StringBuilder();
//        sentences.forEach((key, value) -> sb
//                .append("「").append(key).append("」")
//                .append("という文だったら")
//                .append("「").append(value).append("」")
//                .append("を出力して、\n"));
        sentences.forEach((key, value) -> sb
                .append("If the sentence is ")
                .append("「").append(key).append("」")
                .append(" rephrase it as ")
                .append("「").append(value).append("」")
                .append("\n"));

        return sb.toString();
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

        String uuid = Optional.ofNullable(securityUtils.getUserId()).map(userId -> {
            History history = History.builder()
                    .userId(userId)
                    .action(ActionType.EASY_JAPANESE_MODE)
                    .input(inputDTO.getInput())
                    .output(output)
                    .build();
            historyRepository.save(history);
            return history.getUuid();
        }).orElse(null);

        return OutputDTO.builder().output(output).uuid(uuid).build();
    }

    @Override
    public OutputDTO translate(TranslateInputDTO inputDTO) {
        String text = """
                次の文を%sの元の意味に近い形で、スムーズで自然な%sに翻訳してください。翻訳した文だけ書いてください。
                文:
                「%s」
                """
                .formatted(inputDTO.getSourceLanguage().getInJapanese(), inputDTO.getTargetLanguage().getInJapanese(), inputDTO.getInput());
        String output = languageModelService.generateContent(text);

        String uuid = Optional.ofNullable(securityUtils.getUserId()).map(userId -> {
            History history = History.builder()
                    .userId(userId)
                    .action(ActionType.TRANSLATION)
                    .sourceLanguage(inputDTO.getSourceLanguage())
                    .targetLanguage(inputDTO.getTargetLanguage())
                    .input(inputDTO.getInput())
                    .output(output)
                    .build();
            historyRepository.save(history);
            return history.getUuid();
        }).orElse(null);

        return OutputDTO.builder().output(output).uuid(uuid).build();
    }
}
