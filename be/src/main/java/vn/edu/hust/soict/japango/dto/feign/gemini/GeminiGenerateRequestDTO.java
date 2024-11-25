package vn.edu.hust.soict.japango.dto.feign.gemini;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiGenerateRequestDTO {
    private List<Content> contents;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Content {
        private List<Part> parts;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Part {
        private String text;
    }

    public static GeminiGenerateRequestDTO ofText(String text) {
        return GeminiGenerateRequestDTO.builder()
                .contents(List.of(Content.builder()
                        .parts(List.of(Part.builder().text(text).build()))
                        .build()))
                .build();
    }
}
