package me.deathlesz.sdlc.morse;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MorseModelTest {

    @Test
    void notifiesListenersOnSuccessfulDecode() {
        var model = new MorseModel();
        var callCount = new AtomicInteger();
        model.addListener(m -> callCount.incrementAndGet());

        model.setInput("... --- ...");

        assertEquals(1, callCount.get());
        assertEquals("... --- ...", model.getLastInput());
        assertEquals("SOS", model.getDecodedText());
        assertNull(model.getError());
        assertTrue(model.isDecoded());
    }

    @Test
    void notifiesListenersOnError() {
        var model = new MorseModel();
        var callCount = new AtomicInteger();
        model.addListener(m -> callCount.incrementAndGet());

        model.setInput(".... ???");

        assertEquals(1, callCount.get());
        assertEquals(".... ???", model.getLastInput());
        assertEquals("", model.getDecodedText());
        assertNotNull(model.getError());
        assertFalse(model.isDecoded());
    }

    @Test
    void handlesNullAndEmptyInputGracefully() {
        var model = new MorseModel();
        model.setInput(null);

        assertEquals("", model.getLastInput());
        assertEquals("", model.getDecodedText());
        assertNotNull(model.getError());
        assertFalse(model.isDecoded());
    }

    @Test
    void canRemoveListener() {
        var model = new MorseModel();
        var callCount = new AtomicInteger();
        MorseModel.Listener listener = m -> callCount.incrementAndGet();

        model.addListener(listener);
        model.setInput(".-");
        assertEquals(1, callCount.get());

        model.removeListener(listener);
        model.setInput("-...");
        assertEquals(1, callCount.get());
    }
}