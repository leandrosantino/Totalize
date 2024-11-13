package com.totalize.views.utils;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GlobalScannerListener {

    private final StringBuilder barCodeBuilder = new StringBuilder();
    private final Consumer<String> callback;
    private final Supplier<Boolean> canExecute;

    public GlobalScannerListener(Consumer<String> callback, Supplier<Boolean> canExecute) {
        this.callback = callback;
        this.canExecute = canExecute;
        setupGlobalKeyListener();
    }

    private void setupGlobalKeyListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                char pressedKey = e.getKeyChar();
                if (pressedKey != KeyEvent.VK_ENTER) {
                    if (Character.isLetterOrDigit(pressedKey) || Character.isWhitespace(pressedKey)) {
                        barCodeBuilder.append(pressedKey);
                    }
                    return false;
                }

                String barcode = barCodeBuilder.toString();
                barCodeBuilder.setLength(0);

                if (!barcode.isEmpty() && canExecute.get()) {
                    callback.accept(barcode);
                }
            }
            return false;
        });
    }
}