package vn.edu.hust.soict.japango.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.Normalizer;

@Slf4j
public class StringUtils {
    public static String replaceArg(String string, String argKey, String argValue) {
        return string.replaceAll("\\{" + argKey + "}", argValue);
    }

    private static String normalizeText(String text) {
        // Normalize the text to Unicode NFKC form (common for Japanese text processing)
        return Normalizer.normalize(text, Normalizer.Form.NFKC);
    }

    private static int lcsLength(String a, String b) {
        // Calculate Longest Common Subsequence (LCS) length
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    private static int levenshteinDistance(String a, String b) {
        // Calculate Levenshtein Distance
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j],
                            Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[m][n];
    }

    public static double similarityScore(String sentence1, String sentence2) {
        // Normalize text
        sentence1 = normalizeText(sentence1);
        sentence2 = normalizeText(sentence2);

        // Calculate LCS and normalize its score
        int lcs = lcsLength(sentence1, sentence2);
        int maxLength = Math.max(sentence1.length(), sentence2.length());
        double lcsScore = (double) lcs / maxLength;

        // Calculate Levenshtein Distance and normalize its score
        int levenshtein = levenshteinDistance(sentence1, sentence2);
        double levenshteinScore = 1.0 - ((double) levenshtein / maxLength);

        // Combine scores (weighted average)
        double w1 = 0.6;
        double w2 = 1 - w1;
        double score = (w1 * lcsScore) + (w2 * levenshteinScore);
        log.info("Similarity score between '{}' and '{}': {}", sentence1, sentence2, score);
        return score;
    }
}
