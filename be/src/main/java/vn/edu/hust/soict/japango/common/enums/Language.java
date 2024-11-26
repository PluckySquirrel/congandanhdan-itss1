package vn.edu.hust.soict.japango.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {
    JAPANESE("ja", "日本語"),
    ENGLISH("en", "英語"),
    VIETNAMESE("vi", "ベトナム語");

    private final String code;
    private final String inJapanese;
}
