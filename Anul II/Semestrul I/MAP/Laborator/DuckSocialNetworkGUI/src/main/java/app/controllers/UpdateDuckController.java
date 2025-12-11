package app.controllers;

import core.model.Duck;
import core.model.TipRata;
import core.service.DuckService;
import core.validation.ValidatorContext;
import core.validation.exceptions.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpdateDuckController {

    @FXML private TextField txtId;
    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    @FXML private ComboBox<String> comboTip;
    @FXML private TextField txtViteza;
    @FXML private TextField txtRezistenta;

    private DuckService duckService;
    private Duck duck;
    private Runnable callback;

    private final ValidatorContext validatorContext = new ValidatorContext();

    public void setDuckService(DuckService service) { this.duckService = service; }

    public void setDuck(Duck d) {
        this.duck = d;
        loadDuckData();
    }

    public void setCallback(Runnable r) { this.callback = r; }

    @FXML
    public void initialize() {
        comboTip.getItems().setAll("SWIMMING","FLYING","FLYING_AND_SWIMMING");
    }

    private void loadDuckData() {
        txtId.setText(String.valueOf(duck.getId()));
        txtId.setDisable(true);

        txtUsername.setText(duck.getUsername());
        txtEmail.setText(duck.getEmail());
        txtPassword.setText(duck.getPassword());

        comboTip.setValue(duck.getTip().toString());
        txtViteza.setText(String.valueOf(duck.getViteza()));
        txtRezistenta.setText(String.valueOf(duck.getRezistenta()));
    }

    @FXML
    public void onSave() {
        try {
            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String password = txtPassword.getText();
            TipRata tip = parseTip(comboTip.getValue());
            double viteza = parseDouble(txtViteza.getText(), "Viteza");
            double rezistenta = parseDouble(txtRezistenta.getText(), "Rezistenta");

            // actualizează obiectul
            duck.setUsername(username);
            duck.setEmail(email);
            duck.setPassword(password);
            duck.setTip(tip);
            duck.setViteza(viteza);
            duck.setRezistenta(rezistenta);

            validatorContext.validate(duck);

            duckService.updateDuck(duck);

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
        alert.setHeaderText("Update Duck Error");
        alert.setContentText(msg);
        alert.showAndWait();
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
