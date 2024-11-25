package vn.edu.hust.soict.japango.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {
    JAPANESE("ja"),
    ENGLISH("en"),
    VIETNAMESE("vi");

    private final String code;
}
