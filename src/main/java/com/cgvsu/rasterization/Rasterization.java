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

    public static void drawLineDDA(final PixelDrawer pd,  int x1,  int y1,
                                    int x2,  int y2, final int color) {

        if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
            if (x1 > x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;

                temp = y1;
                y1 = y2;
                y2 = temp;
            }

            int dx = x2 - x1 + 1;
            int dy = y2 - y1 + 1;

            double stepY = dy / (double) dx;
            double y = y1;

            for (int x = x1; x <= x2; x++) {
                pd.putPixel(x, (int) y, color);
                y += stepY;
            }
        } else {
            if (y1 > y2){
                int temp = x1;
                x1 = x2;
                x2 = temp;

                temp = y1;
                y1 = y2;
                y2 = temp;
            }

            int dx = x2 - x1 + 1;
            int dy = y2 - y1 + 1;
            double stepX = dx / (double) dy;
            double x = x1;

            for (int y = y1; y <= y2 ; y++) {
                pd.putPixel((int) x, y, color);
                x += stepX;
            }
        }
    }
}
