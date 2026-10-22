package me.deathlesz.sdlc.morse;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class MorseModel {

    public interface Listener {
        void modelChanged(MorseModel model);
    }

    private final MorseDecoder decoder;
    private final CopyOnWriteArrayList<Listener> listeners = new CopyOnWriteArrayList<>();

    private String lastInput = "";
    private String decodedText = "";
    private String error = null;

    public MorseModel() {
        this(new MorseDecoder());
    }

    public MorseModel(MorseDecoder decoder) {
        this.decoder = decoder;
    }

    public void setInput(String input) {
        lastInput = input == null ? "" : input.trim();
        try {
            decodedText = decoder.decode(lastInput);
            error = null;
        } catch (MorseDecodeException e) {
            decodedText = "";
            error = e.getMessage();
        }
        notifyListeners();
    }

    public String getLastInput() {
        return lastInput;
    }

    public String getDecodedText() {
        return decodedText;
    }

    public String getError() {
        return error;
    }

    public boolean isDecoded() {
        return error == null && !decodedText.isEmpty();
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (var listener : listeners) {
            listener.modelChanged(this);
        }
    }
}
