package app.controllers;

import core.model.Person;
import core.service.PersonService;
import core.validation.ValidatorContext;
import core.validation.exceptions.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddPersonController {

    @FXML private TextField txtId;
    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    @FXML private TextField txtNume;
    @FXML private TextField txtPrenume;
    @FXML private DatePicker dateDataNasterii;
    @FXML private TextField txtOcupatie;
    @FXML private TextField txtNivelEmpatie;

    private PersonService personService;
    private Runnable callback;

    private final ValidatorContext validatorContext = new ValidatorContext();

    public void setPersonService(PersonService service) {
        this.personService = service;
    }

    public void setCallback(Runnable r) {
        this.callback = r;
    }

    @FXML
    public void onSave() {
        try {
            long id = parseLong(txtId.getText(), "ID");
            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String password = txtPassword.getText();
            String nume = txtNume.getText().trim();
            String prenume = txtPrenume.getText().trim();
            LocalDate dataNasterii = dateDataNasterii.getValue();
            String ocupatie = txtOcupatie.getText().trim();
            double nivelEmpatie = parseDouble(txtNivelEmpatie.getText(), "Nivel empatie");

            Person p = new Person(id, username, email, password, nume, prenume, dataNasterii, ocupatie, nivelEmpatie);

            validatorContext.validate(p);

            personService.addPerson(p);

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
        alert.setHeaderText("Add Person Error");
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
}
