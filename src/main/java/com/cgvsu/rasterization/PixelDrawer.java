package com.cgvsu.rasterization;

import javafx.scene.paint.Color;

public interface PixelDrawer {
    void putPixel(int x, int y, Color color);
}
