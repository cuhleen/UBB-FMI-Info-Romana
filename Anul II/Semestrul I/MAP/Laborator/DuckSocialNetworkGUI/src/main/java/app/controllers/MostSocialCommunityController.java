package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import core.AppService;
import core.model.User;

import java.util.List;

public class MostSocialCommunityController {

    @FXML
    private ListView<String> listUsers;

    private AppService appService;

    public void setService(AppService service) {
        this.appService = service;

        List<User> comp = appService.getCeaMaiSociabilaComunitate();

        for (User u : comp) {
            listUsers.getItems().add(u.getId() + " - " + u.getUsername());
        }
    }
}
