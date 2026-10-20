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

            int dx = x2 - x1;
            int dy = y2 - y1;

            double stepY = dy / (double) dx;
            double y = y1;

            for (int x = x1; x <= x2; x++) {
                pd.putPixel(x, (int) Math.round(y), color);
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
                pd.putPixel((int) Math.round(x), y, color);
                x += stepX;
            }

            pd.putPixel(x1, y1, 0xFFFF0000);
            pd.putPixel(x2, y2, 0xFFFF0000);
        }
    }

    public static void drawLineBrezenham(PixelDrawer pd, int x1, int y1, int x2, int y2, int color){
        boolean hor = Math.abs(x2 - x1) > Math.abs(y2 - y1);
        int dx = hor ? (x2 - x1) : (y2 - y1);
        int dy = hor ? (y2 - y1) : (x2 - x1);
        int stepY = dy >= 0 ? 1 : -1;
        int stepX = dx >= 0 ? 1 : -1;
        dy = Math.abs(dy);
        dx = Math.abs(dx);
        int x = hor ? x1 : y1;
        int y = hor ? y1 : x1;
        int d = dx - 2 * dy;
        for (int i = 0; i <= dx; i++) {
            if (hor){
                pd.putPixel(x, y, color);
            } else {
                pd.putPixel(y, x, color);
            }
            if (d <= 0){
                y += stepY;
                d += 2 * dx;
            }
            d += -2 * dy;
            x += stepX;
        }
    }
}
