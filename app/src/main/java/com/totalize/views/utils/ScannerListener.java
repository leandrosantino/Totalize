package com.totalize.views.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class ScannerListener implements KeyListener {

    String barCode = "";

    private final Consumer<String> callback;

    public ScannerListener(Consumer<String> callback) {
        this.callback = callback;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String pressedKey = KeyEvent.getKeyText(e.getKeyCode());
        if (!pressedKey.equals("Enter")) {
            this.barCode = this.barCode.concat(pressedKey);
            return;
        }

        this.callback.accept(this.barCode);
        this.barCode = "";
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
