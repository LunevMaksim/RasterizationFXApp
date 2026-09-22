package com.cgvsu.rasterization;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class FXPixelDrawer implements PixelDrawer{
    private PixelWriter pw;

    public FXPixelDrawer(PixelWriter pw) {
        this.pw = pw;
    }

    @Override
    public void putPixel(int x, int y, Color color) {
        pw.setColor(x, y, color);
    }
}
