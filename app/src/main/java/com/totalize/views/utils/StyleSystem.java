package com.totalize.views.utils;

import java.awt.Color;

public class StyleSystem {

    public static class Colors extends Color {

        public static final Colors PRIMARY = new Colors(255, 191, 0);
        public static final Colors ERROR = new Colors(255, 64, 0);

        public Colors(int r, int g, int b, int a) {
            super(r, g, b, a);
        }

        public Colors(int r, int g, int b) {
            super(r, g, b);
        }

        @Override
        public Color darker() {
            double FACTOR = 0.85;
            return new Color(Math.max((int) (getRed() * FACTOR), 0),
                    Math.max((int) (getGreen() * FACTOR), 0),
                    Math.max((int) (getBlue() * FACTOR), 0),
                    getAlpha());
        }

    }
}