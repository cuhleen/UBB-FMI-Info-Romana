package app.controllers;

import core.model.Duck;
import core.model.TipRata;
import core.service.DuckService;
import core.validation.exceptions.ValidationException;
import core.validation.ValidatorContext;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddDuckController {

    @FXML private TextField txtId;
    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    @FXML private ComboBox<String> comboTip;
    @FXML private TextField txtViteza;
    @FXML private TextField txtRezistenta;

    private DuckService duckService;
    private Runnable callback;

    private final ValidatorContext validatorContext = new ValidatorContext();

    public void setDuckService(DuckService service) { this.duckService = service; }
    public void setCallback(Runnable r) { this.callback = r; }

    @FXML
    public void initialize() {
        comboTip.getItems().setAll("SWIMMING","FLYING","FLYING_AND_SWIMMING");
    }

    @FXML
    public void onSave() {
        try {
            long id = parseLong(txtId.getText(), "ID");
            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String password = txtPassword.getText();
            TipRata tip = parseTip(comboTip.getValue());
            double viteza = parseDouble(txtViteza.getText(), "Viteza");
            double rezistenta = parseDouble(txtRezistenta.getText(), "Rezistenta");

            Duck duck = new Duck(id, username, email, password, tip, viteza, rezistenta);

            validatorContext.validate(duck);

            duckService.addDuck(duck);

            if (callback != null) callback.run();
            onCancel();

        } catch (ValidationException e) {
            showError("Validation error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            showError("Input error: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error: " + e.getMessage());
        }
    }

    @FXML
    public void onCancel() {
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Add Duck Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private long parseLong(String value, String fieldName) {
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " trebuie să fie un număr valid.");
        }
    }

    private double parseDouble(String value, String fieldName) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " trebuie să fie un număr valid.");
        }
    }

    private TipRata parseTip(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Trebuie să selectezi un tip.");
        }
        return TipRata.valueOf(value);
    }
}
