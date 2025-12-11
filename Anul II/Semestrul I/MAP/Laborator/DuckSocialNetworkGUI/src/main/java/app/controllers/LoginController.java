package app.controllers;

import core.AppService;
import core.repository.RepoMessageDB;
import core.security.PasswordUtil;
import core.service.DuckService;
import core.service.MessageService;
import core.service.PersonService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    private DuckService duckService;
    private PersonService personService;
    private AppService appService;
    private Runnable onLoginSuccess;

    @FXML
    public void initialize() {
        // Enter pe parola apasă Login
        txtPassword.setOnAction(event -> onLogin());
        txtUsername.setOnAction(event -> onLogin());
    }

    public void setUserService(DuckService duckService, PersonService personService, AppService appService) {
        this.duckService = duckService;
        this.personService = personService;
        this.appService = appService;
    }

    public void setOnLoginSuccess(Runnable callback) {
        this.onLoginSuccess = callback;
    }

    @FXML
    public void onLogin() {
        try {
            String username = txtUsername.getText().trim();
            String password = txtPassword.getText();

            String hashed = PasswordUtil.sha256(password);

            boolean ok = appService.checkLogin(username, hashed);

            if (!ok) {
                showError("User sau parolă incorectă.");
                return;
            }

            // 1. obține ID-ul user-ului logat
            long loggedUserId = appService.getUserIdByUsername(username);

            // 2. închide fereastra de login
            closeWindow();

            // 3. deschide fereastra de chat
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/chat.fxml"));
            AnchorPane root = loader.load();

            ChatController chatController = loader.getController();
            chatController.setServices(appService, new MessageService(new RepoMessageDB(
                    "jdbc:postgresql://localhost:5432/duckdb",
                    "duckuser",
                    "parola123"
            )), loggedUserId);

            Stage stage = new Stage();
            stage.setTitle("Chat");
            stage.setScene(new Scene(root));
            stage.setWidth(800);
            stage.setHeight(600);
            stage.show();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText("Autentificare eșuată");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
