package app.controllers;

import core.AppService;
import core.model.Person;
import core.model.Duck;
import core.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class AllUsersController {

    @FXML
    private ListView<User> listUsers;

    private AppService appService;

    public void setAppService(AppService appService) {
        this.appService = appService;
        loadUsers();
    }

    private void loadUsers() {
        var users = appService.getAllUsers();
        listUsers.getItems().setAll(users);

        listUsers.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(User u, boolean empty) {
                super.updateItem(u, empty);
                if (empty || u == null) setText(null);
                else setText(u.getUsername());
            }
        });

        listUsers.setOnMouseClicked(e -> {
            User selected = listUsers.getSelectionModel().getSelectedItem();
            if (selected != null) showUserCard(selected);
        });
    }

    private void showUserCard(User u) {
        BorderPane root = new BorderPane();
        HBox content = new HBox(20);
        content.setStyle("-fx-padding: 20; -fx-alignment: center-left;");

        ImageView iv = new ImageView();
        iv.setFitHeight(100);
        iv.setFitWidth(100);
        if (u instanceof Person) {
            iv.setImage(new Image(getClass().getResourceAsStream("/views/images/personPlaceholder.png")));
        } else if (u instanceof Duck) {
            iv.setImage(new Image(getClass().getResourceAsStream("/views/images/duckPlaceholder.png")));
        }
        content.getChildren().add(iv);

        VBox info = new VBox(10);

        Label lblUsername = new Label(u.getUsername());
        lblUsername.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        info.getChildren().add(lblUsername);

        if (u instanceof Person p) {
            HBox statsBox = new HBox(15);
            statsBox.setStyle("-fx-alignment: center-left;");

            VBox birthBox = new VBox(2);
            Label birthTitle = new Label("Data nasterii");
            birthTitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #000;");
            Label birthValue = new Label(p.getDataNasterii().toString());
            birthValue.setStyle("-fx-font-weight: bold;");
            birthBox.getChildren().addAll(birthTitle, birthValue);

            VBox occupationBox = new VBox(2);
            Label occupationTitle = new Label("Ocupatie");
            occupationTitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #000;");
            Label occupationValue = new Label(p.getOcupatie());
            occupationValue.setStyle("-fx-font-weight: bold;");
            occupationBox.getChildren().addAll(occupationTitle, occupationValue);

            VBox empathyBox = new VBox(2);
            Label empathyTitle = new Label("Nivel empatie");
            empathyTitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #000;");
            Label empathyValue = new Label(String.format("%.2f", p.getNivelEmpatie()));
            empathyValue.setStyle("-fx-font-weight: bold;");
            empathyBox.getChildren().addAll(empathyTitle, empathyValue);

            statsBox.getChildren().addAll(birthBox, occupationBox, empathyBox);
            info.getChildren().add(statsBox);

        } else if (u instanceof Duck d) {
            HBox statsBox = new HBox(15);
            statsBox.setStyle("-fx-alignment: center-left;");

            VBox typeBox = new VBox(2);
            Label typeTitle = new Label("Tip");
            typeTitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #000;");
            Label typeValue = new Label(d.getTip().toString());
            typeValue.setStyle("-fx-font-weight: bold;");
            typeBox.getChildren().addAll(typeTitle, typeValue);

            VBox speedBox = new VBox(2);
            Label speedTitle = new Label("Viteza");
            speedTitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #000;");
            Label speedValue = new Label(String.format("%.2f", d.getViteza()));
            speedValue.setStyle("-fx-font-weight: bold;");
            speedBox.getChildren().addAll(speedTitle, speedValue);

            VBox resistanceBox = new VBox(2);
            Label resistanceTitle = new Label("Rezistenta");
            resistanceTitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #000;");
            Label resistanceValue = new Label(String.format("%.2f", d.getRezistenta()));
            resistanceValue.setStyle("-fx-font-weight: bold;");
            resistanceBox.getChildren().addAll(resistanceTitle, resistanceValue);

            statsBox.getChildren().addAll(typeBox, speedBox, resistanceBox);
            info.getChildren().add(statsBox);
        }

        content.getChildren().add(info);
        root.setCenter(content);

        Stage stage = new Stage();
        stage.setTitle("Profil: " + u.getUsername());
        stage.setScene(new Scene(root));
        stage.show();
    }
}
