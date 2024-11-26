package vn.edu.hust.soict.japango.dto.feign.gemini;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiGenerateResponseDTO {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Candidate {
        private Content content;
        private String finishReason;
        private Double avgLogprobs;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Content {
        private List<Part> parts;
        private String role;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Part {
        private String text;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UsageMetadata {
        private Integer promptTokenCount;
        private Integer candidatesTokenCount;
        private Integer totalTokenCount;
    }

    public List<String> getTexts() {
        List<String> texts = new ArrayList<>();
        for (Candidate candidate: candidates) {
            if (candidate.content == null) continue;
            if (candidate.content.parts == null) continue;
            texts.addAll(candidate.content.parts.stream().map(Part::getText).toList());
        }
        return texts;
    }
}
