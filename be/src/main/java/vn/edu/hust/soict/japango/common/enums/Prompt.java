package vn.edu.hust.soict.japango.common.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Prompt {
    EXPRESS_INTENT_PROMPT(
            """
                    次の文を話し手の意図や希望がより明確になるように言い換えてください。例えば、「締め切りは金曜日なので、終わったら知らせてください」という文を、より具体的に「締め切りは金曜日ですが、私はできるだけ早く課題を確認し、フィードバックを提供したいと考えています。そのため、課題が終わり次第、すぐにご連絡いただけるとありがたいです。」のように書き直してください。言い換えた文だけを書いて、他のことは書かないでください。N3以下のレベルの簡単な言葉を使ってください。
                    文:
                    「%s」
                    """),
    EASY_JAPANESE_PROMPT(
            """
                    次の文をN3以下の初級の簡単な言葉を使って、元の意味に近い形で書き直してください。
                    文:
                    「%s」
                    """
    );
    private final String value;

    public String apply(String text) {
        return value.formatted(text);
    }
}
