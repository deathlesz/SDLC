package me.deathlesz.sdlc.morse;

import java.util.ArrayList;
import java.util.List;

public final class MorseDecoder {

    private final MorseAlphabet alphabet;

    public MorseDecoder() {
        this(MorseAlphabet.ITU);
    }

    public MorseDecoder(MorseAlphabet alphabet) {
        this.alphabet = alphabet;
    }

    public String decode(String input) throws MorseDecodeException {
        if (input == null) {
            throw new MorseDecodeException("Ввод пуст: введите хотя бы один код Морзе");
        }
        var trimmed = input.trim();
        if (trimmed.isEmpty() || trimmed.replaceAll("[\\s/]+", "").isEmpty()) {
            throw new MorseDecodeException("Ввод пуст: введите хотя бы один код Морзе");
        }

        validateCharacters(trimmed);

        var words = splitWords(trimmed);
        if (words.isEmpty()) {
            throw new MorseDecodeException("Ввод пуст: введите хотя бы один код Морзе");
        }

        var result = new StringBuilder();
        for (var i = 0; i < words.size(); i++) {
            if (i > 0) {
                result.append(' ');
            }
            result.append(decodeWord(words.get(i)));
        }
        return result.toString();
    }

    private void validateCharacters(String input) throws MorseDecodeException {
        for (var i = 0; i < input.length(); i++) {
            var ch = input.charAt(i);
            if (ch != '.' && ch != '-' && ch != '/' && !Character.isWhitespace(ch)) {
                throw new MorseDecodeException(
                        "Недопустимый символ «" + ch + "»: разрешены только точки «.», тире «-», пробелы и «/»");
            }
        }
    }

    private String decodeWord(String word) throws MorseDecodeException {
        var letters = word.split("\\s+");
        var result = new StringBuilder(letters.length);
        for (var code : letters) {
            if (code.isBlank()) {
                continue;
            }
            result.append(decodeLetter(code));
        }
        return result.toString();
    }

    private char decodeLetter(String code) throws MorseDecodeException {
        var letter = alphabet.decode(code);
        if (letter == null) {
            throw new MorseDecodeException("Код Морзе «" + code + "» не найден в алфавите");
        }
        return letter;
    }

    private List<String> splitWords(String input) {
        var words = new ArrayList<String>();
        for (var word : input.split("\\s*/\\s*|\\s{2,}")) {
            if (!word.isBlank()) {
                words.add(word.trim());
            }
        }
        return words;
    }
}
