package com.cgvsu.rasterizationfxapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

import com.cgvsu.rasterization.*;
import javafx.scene.paint.Color;

public class RasterizationController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    // Описание интерфейса
    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        PixelDrawer pd = new FXPixelDrawer(canvas.getGraphicsContext2D().getPixelWriter());

        Rasterization.drawRectangle(pd, 200, 300, 200, 100, 0xFFD2691E);
        Rasterization.drawRectangle(pd, 250, 250, 50, 200, 0xFF00FFFF);
    }

}