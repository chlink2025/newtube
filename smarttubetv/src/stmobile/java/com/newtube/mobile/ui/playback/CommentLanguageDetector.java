package com.newtube.mobile.ui.playback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * NEWTUBE(comment-translate): a lightweight "does this comment need translating?" check.
 *
 * <p>The TV comments response carries no source language. Verified 2026-11-08: the TV
 * commentRenderer has no language field (with hl=en or hl=ja), and the newer WEB
 * commentEntityPayload only carries {@code translateButtonStyle} and a
 * {@code translateButtonEntityKey} that is present on every comment, English and Japanese alike;
 * the tri-state button state is identical too. The official client therefore decides locally.
 * This is a port of the Greasy Fork reference script's heuristic: writing system first, then
 * Latin-script stopword scoring. When the answer is uncertain the comment is treated as foreign;
 * the caller hides the entry afterwards when the endpoint returns the original text unchanged.</p>
 */
final class CommentLanguageDetector {
    private static final Pattern URL = Pattern.compile("https?://\\S+");
    private static final Pattern MENTION = Pattern.compile("[@#][\\w.-]{2,40}");
    private static final String VIETNAMESE_CHARS =
            "ăâđêôơưạảấầẩẫậắằẳẵặẹẻẽếềểễệỉịọỏốồổỗộớờởỡợụủứừửữựỳỵỷỹ";
    private static final Pattern UKRAINIAN = Pattern.compile("[іїєґІЇЄҐ]");
    private static final Pattern RUSSIAN = Pattern.compile("[ыэъёЫЭЪЁ]");

    private static final Map<String, Set<String>> STOPWORDS = new HashMap<>();

    static {
        stopwords("en", "the and is are was were you this that with for have has not but just my so it its im dont thats very really love great song music thanks thank amazing best who what when");
        stopwords("es", "que de la el los las y en es un una por con para muy pero como esta está qué mi te se lo del al más gracias canción esto eres soy tu");
        stopwords("fr", "le la les des du est et une un que qui pour pas dans avec très je tu il elle nous vous sur mais merci chanson cest suis cette ce");
        stopwords("de", "der die das und ist nicht ich du ein eine mit für auf den dem zu es wie aber auch sehr danke lied einfach sind war");
        stopwords("it", "il lo la gli le di che è e un una per con non sono molto anche come questa grazie canzone bellissima ma mi ti");
        stopwords("pt", "o a os as de que e é um uma para com não muito mas como essa obrigado música você ele ela está são");
        stopwords("id", "yang dan di ini itu untuk dengan tidak saya aku kamu dari ada lagu banget sekali juga ya aja nya");
        stopwords("tr", "ve bir bu çok için ama ben sen de da ne güzel şarkı mı değil bence harika");
        stopwords("pl", "i w nie to jest się na z że jak ale bardzo dla piosenka dzięki super");
        stopwords("nl", "de het een en is van dat niet ik je met voor maar ook zo heel mooi lied dit");
    }

    private CommentLanguageDetector() {
    }

    /** True when the comment is in the target language or has no letters to translate. */
    static boolean isSameLanguage(String text, String targetLanguage) {
        String detected = detect(text);
        if ("none".equals(detected)) {
            return true;
        }
        String target = familyOf(targetLanguage);
        if (target.isEmpty()) {
            return false;
        }
        String family = familyOf(detected);
        return family.equals(target) || ("cyr".equals(detected) && "ru".equals(target));
    }

    /** A coarse language/script tag: ja, ko, zh, ru, uk, cyr, arab, he, th, el, hi, vi, en..., latin, none. */
    static String detect(String text) {
        String cleaned = cleanText(text);
        int letters = 0;
        EnumMap<Character.UnicodeScript, Integer> scripts =
                new EnumMap<>(Character.UnicodeScript.class);
        for (int i = 0; i < cleaned.length(); ) {
            int codePoint = cleaned.codePointAt(i);
            i += Character.charCount(codePoint);
            if (!Character.isLetter(codePoint)) {
                continue;
            }
            letters++;
            Character.UnicodeScript script = Character.UnicodeScript.of(codePoint);
            Integer count = scripts.get(script);
            scripts.put(script, count == null ? 1 : count + 1);
        }
        if (letters == 0) {
            return "none";
        }

        int kana = count(scripts, Character.UnicodeScript.HIRAGANA)
                + count(scripts, Character.UnicodeScript.KATAKANA);
        if ((double) kana / letters > 0.08) {
            return "ja";
        }
        if (ratio(scripts, Character.UnicodeScript.HANGUL, letters) > 0.08) {
            return "ko";
        }
        if (ratio(scripts, Character.UnicodeScript.THAI, letters) > 0.08) {
            return "th";
        }
        if (ratio(scripts, Character.UnicodeScript.GREEK, letters) > 0.08) {
            return "el";
        }
        if (ratio(scripts, Character.UnicodeScript.DEVANAGARI, letters) > 0.08) {
            return "hi";
        }
        if (ratio(scripts, Character.UnicodeScript.HEBREW, letters) > 0.08) {
            return "he";
        }
        if (ratio(scripts, Character.UnicodeScript.ARABIC, letters) > 0.08) {
            return "arab";
        }
        if (ratio(scripts, Character.UnicodeScript.CYRILLIC, letters) > 0.08) {
            if (UKRAINIAN.matcher(cleaned).find()) {
                return "uk";
            }
            if (RUSSIAN.matcher(cleaned).find()) {
                return "ru";
            }
            return "cyr";
        }
        if (ratio(scripts, Character.UnicodeScript.HAN, letters) > 0.3) {
            return "zh";
        }
        return latinLanguage(cleaned);
    }

    private static String latinLanguage(String text) {
        List<String> words = new ArrayList<>();
        for (String word : text.toLowerCase(Locale.ROOT).split("[^\\p{L}]+")) {
            if (!word.isEmpty()) {
                words.add(word);
            }
        }
        if (words.isEmpty()) {
            return "latin";
        }

        int vietnamese = 0;
        for (int i = 0; i < text.length(); i++) {
            if (VIETNAMESE_CHARS.indexOf(Character.toLowerCase(text.charAt(i))) >= 0) {
                vietnamese++;
            }
        }
        if (vietnamese >= 2 || (vietnamese >= 1 && words.size() <= 3)) {
            return "vi";
        }

        Map<String, Integer> scores = new HashMap<>();
        for (String word : words) {
            for (Map.Entry<String, Set<String>> entry : STOPWORDS.entrySet()) {
                if (entry.getValue().contains(word)) {
                    Integer score = scores.get(entry.getKey());
                    scores.put(entry.getKey(), score == null ? 1 : score + 1);
                }
            }
        }
        String best = null;
        int bestScore = 0;
        int secondScore = 0;
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            int score = entry.getValue();
            if (score > bestScore) {
                secondScore = bestScore;
                bestScore = score;
                best = entry.getKey();
            } else if (score > secondScore) {
                secondScore = score;
            }
        }
        if (best != null && bestScore > secondScore && (bestScore >= 2 || words.size() <= 3)) {
            return best;
        }
        return "latin";
    }

    private static String cleanText(String text) {
        if (text == null) {
            return "";
        }
        return MENTION.matcher(URL.matcher(text).replaceAll(" ")).replaceAll(" ");
    }

    private static String familyOf(String language) {
        if (language == null) {
            return "";
        }
        String base = language.toLowerCase(Locale.ROOT);
        int dash = base.indexOf('-');
        if (dash >= 0) {
            base = base.substring(0, dash);
        }
        switch (base) {
            case "iw":
                return "he";
            case "in":
                return "id";
            case "bg":
            case "sr":
            case "be":
                return "cyr";
            case "fa":
            case "ur":
            case "ar":
                return "arab";
            default:
                return base;
        }
    }

    private static int count(Map<Character.UnicodeScript, Integer> scripts,
                             Character.UnicodeScript script) {
        Integer value = scripts.get(script);
        return value == null ? 0 : value;
    }

    private static double ratio(Map<Character.UnicodeScript, Integer> scripts,
                                Character.UnicodeScript script, int letters) {
        return (double) count(scripts, script) / letters;
    }

    private static void stopwords(String language, String words) {
        Set<String> set = new HashSet<>();
        Collections.addAll(set, words.split(" "));
        STOPWORDS.put(language, set);
    }
}
