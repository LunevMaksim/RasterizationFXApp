package com.cgvsu.rasterizationfxapp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
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

        // Rasterization.drawRectangle(pd, 200, 300, 200, 100, 0xFFD2691E);
        // Rasterization.drawRectangle(pd, 250, 250, 50, 200, 0xFF00FFFF);

        // Rasterization.drawLineDDA(pd, 100, 100, 800, 200, 0xFF000000);

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                canvas.getGraphicsContext2D().clearRect(0, 0, 800, 600);
                // Rasterization.drawRectangle(pd, 0, 0, 800, 600, 0xFFFFFFFF);
                Rasterization.drawLineDDA(pd, 400, 300,
                        (int) mouseEvent.getX(), (int) mouseEvent.getY(), 0xFF000000);
            }
        });
    }
}