package com.newtube.mobile.ui.playback;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * NEWTUBE(comment-translate): the local "is this comment already in the target language?" check.
 */
public class CommentLanguageDetectorTest {
    @Test
    public void detectsWritingSystems() {
        assertEquals("ja", CommentLanguageDetector.detect("また良い曲作りましたなAyase氏"));
        assertEquals("zh", CommentLanguageDetector.detect("绫濑又创作了一首很棒的歌"));
        assertEquals("ko", CommentLanguageDetector.detect("안녕하세요"));
        assertEquals("ru", CommentLanguageDetector.detect("Это очень хорошая песня"));
        assertEquals("uk", CommentLanguageDetector.detect("Це дуже гарна пісня"));
        assertEquals("arab", CommentLanguageDetector.detect("هذه أغنية رائعة"));
        assertEquals("he", CommentLanguageDetector.detect("זה שיר נהדר"));
        assertEquals("hi", CommentLanguageDetector.detect("यह एक अच्छा गाना है"));
        assertEquals("th", CommentLanguageDetector.detect("เพลงนี้ดีมาก"));
        assertEquals("el", CommentLanguageDetector.detect("Αυτό είναι ένα καλό τραγούδι"));
        assertEquals("vi", CommentLanguageDetector.detect("Tuyệt vời, bài hát này rất hay"));
    }

    @Test
    public void detectsCommonLatinLanguages() {
        assertEquals("en", CommentLanguageDetector.detect("This is a great song and I love it"));
        assertEquals("es", CommentLanguageDetector.detect("Esta canción es muy buena, gracias"));
        assertEquals("fr", CommentLanguageDetector.detect("Merci beaucoup, cette chanson est très belle"));
        assertEquals("de", CommentLanguageDetector.detect("Danke, das Lied ist sehr schön"));
        assertEquals("it", CommentLanguageDetector.detect("Questo è un bel brano, grazie"));
        assertEquals("pt", CommentLanguageDetector.detect("Esta música é muito boa, obrigado"));
    }

    @Test
    public void treatsLetterlessTextAsNothingToTranslate() {
        assertEquals("none", CommentLanguageDetector.detect("🎉🎉🎉"));
        assertEquals("none", CommentLanguageDetector.detect("https://youtu.be/x @someone #topic"));
        assertEquals("none", CommentLanguageDetector.detect("1234 !!"));
        assertTrue(CommentLanguageDetector.isSameLanguage("🎉🎉🎉", "en"));
    }

    @Test
    public void sameLanguageDecision() {
        assertTrue(CommentLanguageDetector.isSameLanguage("This is a great song", "en"));
        assertFalse(CommentLanguageDetector.isSameLanguage("This is a great song", "de"));
        assertTrue(CommentLanguageDetector.isSameLanguage("また良い曲作りました", "ja"));
        assertFalse(CommentLanguageDetector.isSameLanguage("また良い曲作りました", "en"));
        assertTrue(CommentLanguageDetector.isSameLanguage("绫濑又创作了一首很棒的歌", "zh"));
        assertFalse(CommentLanguageDetector.isSameLanguage("绫濑又创作了一首很棒的歌", "en"));
        assertTrue(CommentLanguageDetector.isSameLanguage("안녕하세요", "ko"));
        assertFalse(CommentLanguageDetector.isSameLanguage("안녕하세요", "en"));
        assertTrue(CommentLanguageDetector.isSameLanguage("Merci beaucoup, cette chanson est très belle", "fr"));
        assertFalse(CommentLanguageDetector.isSameLanguage("Merci beaucoup, cette chanson est très belle", "en"));
        assertTrue(CommentLanguageDetector.isSameLanguage("Це дуже гарна пісня", "uk"));
        assertFalse(CommentLanguageDetector.isSameLanguage("Це дуже гарна пісня", "ru"));
    }

    @Test
    public void uncertainLatinIsTreatedAsForeign() {
        // No stopword match: let the endpoint decide, then the caller hides the entry if the
        // translation comes back unchanged.
        assertEquals("latin", CommentLanguageDetector.detect("Nice!"));
        assertFalse(CommentLanguageDetector.isSameLanguage("Nice!", "en"));
    }
}
