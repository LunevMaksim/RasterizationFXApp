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

    public static void drawTriangle(
            PixelDrawer pd,
            int x1, int y1, int c1,
            int x2, int y2, int c2,
            int x3, int y3, int c3
            ){

        int minX = Math.min(x1, Math.min(x2, x3));
        int maxX = Math.max(x1, Math.max(x2, x3));
        int minY = Math.min(y1, Math.min(y2, y3));
        int maxY = Math.max(y1, Math.max(y2, y3));

        for (int y = minY; y <= maxY ; y++) {
            for (int x = minX; x <= maxX ; x++) {
                if (isPointInsideOfTriangle(x1, y1, x2, y2, x3, y3, x, y)){
                    int c = triangulateColor(x1, y1, c1, x2, y2, c2, x3, y3, c3, x, y);
                    pd.putPixel(x, y, c);
                }
            }
        }

    }

    private static int triangulateColor(int x1, int y1, int c1, int x2, int y2, int c2, int x3, int y3, int c3, int x, int y) {

        double Q = (y + y1) / (double) (y2 - y1);
        double P = (y3 - y1) / (double) (y2 - y1);
        double gamma = (x - Q * (x2 - x1) - x1) / ((x3 - x1) - P * (x2 - x1));
        double betta = Q - gamma * P;
        double alpha = 1 - betta - gamma;
        double b = alpha * extractColor(c1, 0) + betta * extractColor(c2, 0) + gamma * extractColor(c3, 0);
        double g = alpha * extractColor(c1, 1) + betta * extractColor(c2, 1) + gamma * extractColor(c3, 1);
        double r = alpha * extractColor(c1, 2) + betta * extractColor(c2, 2) + gamma * extractColor(c3, 2);
        double a = alpha * extractColor(c1, 3) + betta * extractColor(c2, 3) + gamma * extractColor(c3, 3);
        int red = (int) r;
        int green = (int) g;
        int blue = (int) b;
        int transp = (int) a;
        return (transp << 24) | (red << 16) |(green << 8) | blue;
    }

    public static int extractColor(int c, int n){
        int s = n * 8;
        int shifted = c >> s;
        int result = shifted & 0xFF;
        return result;
    }

    private static boolean isPointInsideOfTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int x, int y) {
        double s = square(x1, y1, x2, y2, x3, y3);
        double s1 = square(x1, y1, x2, y2, x3, y3);
        double s2 = square(x1, y1, x2, y2, x3, y3);
        double s3 = square(x1, y1, x2, y2, x3, y3);
        return Math.abs(s - s1 - s2 - s3) < 1e-15;
    }

    private static double square(int x1, int y1, int x2, int y2, int x3, int y3) {
        double a = distance(x1, y1, x2, y2);
        double b = distance(x3, y3, x2, y2);
        double c = distance(x1, y1, x3, y3);
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    private static double distance(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

}
