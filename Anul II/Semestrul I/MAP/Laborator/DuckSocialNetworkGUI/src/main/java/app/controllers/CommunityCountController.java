package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import core.AppService;

public class CommunityCountController {

    @FXML
    private Label lblCount;

    private AppService appService;

    public void setService(AppService service) {
        this.appService = service;
        int nr = appService.getNumarComunitati();
        lblCount.setText("Număr comunități: " + nr);
    }
}
