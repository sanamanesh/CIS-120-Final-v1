package org.cis120.twentyfourtyeight;

import java.awt.*;

public class Tile {
    private int value;
    private Color color;

    public Tile() {
        this.value = 0;
        color = new Color(204, 192, 179);
    }

    public Tile(int value) {
        this.value = value;
        updateColor();
    }

    public int getValue() {
        return this.value;
    }

    public Color getColor() {
        return this.color;
    }

    public void updateValue(int value) {
        this.value = value;
        updateColor();
    }

    public void updateColor() {
        if (this.value == 0) {
            color = new Color(204, 192, 179);
        } else if (this.value == 2) {
            color = new Color(238, 228, 218);
        } else if (this.value == 4) {
            color = new Color(237, 224, 200);
        } else if (this.value == 8) {
            color = new Color(242, 177, 121);
        } else if (this.value == 16) {
            color = new Color(245, 149, 99);
        } else if (this.value == 32) {
            color = new Color(246, 124, 95);
        } else if (this.value == 64) {
            color = new Color(246, 94, 59);
        } else if (this.value == 128) {
            color = new Color(237, 207, 114);
        } else if (this.value == 256) {
            color = new Color(237, 204, 97);
        } else if (this.value == 512) {
            color = new Color(237, 200, 80);
        } else if (this.value == 1024) {
            color = new Color(237, 197, 63);
        } else {
            color = new Color(237, 194, 46);
        }
    }
}
