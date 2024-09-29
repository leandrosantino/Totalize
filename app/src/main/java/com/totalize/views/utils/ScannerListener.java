package com.totalize.views.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ScannerListener implements KeyListener {

    String barCode = "";

    @Override
    public void keyPressed(KeyEvent e) {
        String pressedKey = KeyEvent.getKeyText(e.getKeyCode());
        if (!pressedKey.equals("Enter")) {
            this.barCode = this.barCode.concat(pressedKey);
            return;
        }
        System.out.println(this.barCode);
        this.barCode = "";
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
