package me.deathlesz.sdlc.morse;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class MorseAlphabet {

    public static final MorseAlphabet ITU = new MorseAlphabet(new String[][]{
            {"A", ".-"}, {"B", "-..."}, {"C", "-.-."}, {"D", "-.."}, {"E", "."},
            {"F", "..-."}, {"G", "--."}, {"H", "...."}, {"I", ".."}, {"J", ".---"},
            {"K", "-.-"}, {"L", ".-.."}, {"M", "--"}, {"N", "-."}, {"O", "---"},
            {"P", ".--."}, {"Q", "--.-"}, {"R", ".-."}, {"S", "..."}, {"T", "-"},
            {"U", "..-"}, {"V", "...-"}, {"W", ".--"}, {"X", "-..-"}, {"Y", "-.--"},
            {"Z", "--.."},
            {"0", "-----"}, {"1", ".----"}, {"2", "..---"}, {"3", "...--"},
            {"4", "....-"}, {"5", "....."}, {"6", "-...."}, {"7", "--..."},
            {"8", "---.."}, {"9", "----."},
            {".", ".-.-.-"}, {",", "--..--"}, {"?", "..--.."}, {"!", "-.-.--"},
            {"'", ".----."}, {"\"", ".-..-."}, {"(", "-.--."}, {")", "-.--.-"},
            {"&", ".-..."}, {":", "---..."}, {";", "-.-.-."}, {"=", "-...-"},
            {"+", ".-.-."}, {"-", "-....-"}, {"_", "..--.-"}, {"$", "...-..-"},
            {"@", ".--.-."}});

    private final Map<String, Character> codes;

    private MorseAlphabet(String[][] entries) {
        var codes = new LinkedHashMap<String, Character>(entries.length);
        for (var entry : entries) {
            var predecessor = codes.put(entry[1], entry[0].charAt(0));
            if (predecessor != null) {
                throw new IllegalArgumentException("Дублирующийся код Морзе: " + entry[1]);
            }
        }
        this.codes = Collections.unmodifiableMap(codes);
    }

    public static MorseAlphabet of(String[][] entries) {
        return new MorseAlphabet(entries);
    }

    public boolean contains(String code) {
        return codes.containsKey(code);
    }

    public Character decode(String code) {
        return codes.get(code);
    }

    public Map<String, Character> codes() {
        return codes;
    }
}
