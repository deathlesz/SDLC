package me.deathlesz.sdlc.morse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MorseDecoderTest {

    private final MorseDecoder decoder = new MorseDecoder();

    @Test
    void decodesSingleLetter() throws Exception {
        assertEquals("E", decoder.decode("."));
        assertEquals("S", decoder.decode("..."));
        assertEquals("O", decoder.decode("---"));
    }

    @Test
    void decodesWord() throws Exception {
        assertEquals("HELLO", decoder.decode(".... . .-.. .-.. ---"));
        assertEquals("WORLD", decoder.decode(".-- --- .-. .-.. -.."));
    }

    @Test
    void decodesDigits() throws Exception {
        assertEquals("123", decoder.decode(".---- ..--- ...--"));
        assertEquals("7", decoder.decode("--..."));
    }

    @Test
    void decodesPunctuation() throws Exception {
        assertEquals(".,?", decoder.decode(".-.-.- --..-- ..--.."));
        assertEquals("!", decoder.decode("-.-.--"));
    }

    @Test
    void separatesWordsBySlash() throws Exception {
        assertEquals("HI LO", decoder.decode(".... .. / .-.. ---"));
    }

    @Test
    void separatesWordsByMultipleSpaces() throws Exception {
        assertEquals("HI LO", decoder.decode(".... ..   .-.. ---"));
    }

    @Test
    void acceptsTabsNewlinesAndExtraWhitespace() throws Exception {
        assertEquals("HI LO", decoder.decode("  ....\t.. /\n.-..\t---  "));
    }

    @Test
    void trimsSurroundingWhitespace() throws Exception {
        assertEquals("SOS", decoder.decode("  ... --- ...  "));
    }

    @Test
    void acceptsSpacesAroundSlash() throws Exception {
        assertEquals("A B", decoder.decode(".- / -..."));
    }

    @Test
    void rejectsEmptyInput() {
        assertThrows(MorseDecodeException.class, () -> decoder.decode(""));
        assertThrows(MorseDecodeException.class, () -> decoder.decode("   \t\n "));
    }

    @Test
    void rejectsInputOfOnlySeparators() {
        assertThrows(MorseDecodeException.class, () -> decoder.decode("/"));
        assertThrows(MorseDecodeException.class, () -> decoder.decode(" / / "));
    }

    @Test
    void rejectsForeignCharacters() {
        var e = assertThrows(MorseDecodeException.class, () -> decoder.decode(".... A"));
        assertTrue(e.getMessage().contains("A"));
        assertThrows(MorseDecodeException.class, () -> decoder.decode(".... *"));
        assertThrows(MorseDecodeException.class, () -> decoder.decode("ABCD"));
    }

    @Test
    void rejectsUnknownSequences() {
        var e = assertThrows(MorseDecodeException.class, () -> decoder.decode("......."));
        assertTrue(e.getMessage().contains("......."));
        assertThrows(MorseDecodeException.class, () -> decoder.decode(".... ..--..--"));
    }

    @Test
    void alphabetHasNoDuplicateCodes() {
        var codes = MorseAlphabet.ITU.codes();
        assertEquals(codes.size(), codes.keySet().stream().distinct().count());
    }
}