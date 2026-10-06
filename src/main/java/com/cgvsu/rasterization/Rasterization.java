package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rasterization {

    // Всё делаем через PixelWriter
    public static void drawRectangle(
            final PixelDrawer pd,
            final int x, final int y,
            final int width, final int height,
            final int color)
    {
        for (int row = y; row < y + height; ++row)
            for (int col = x; col < x + width; ++col)
                pd.putPixel(col, row, color);
    }
}
