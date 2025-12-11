package app.controllers;

import core.service.FriendshipService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddFriendshipController {

    @FXML private TextField txtUserA;
    @FXML private TextField txtUserB;

    private FriendshipService friendshipService;
    private Runnable callback;

    public void setFriendshipService(FriendshipService service) {
        this.friendshipService = service;
    }

    public void setCallback(Runnable r) {
        this.callback = r;
    }

    @FXML
    public void onSave() {
        try {
            long userA = Long.parseLong(txtUserA.getText().trim());
            long userB = Long.parseLong(txtUserB.getText().trim());

            if (userA == userB) {
                showError("A user cannot be friends with themselves!");
                return;
            }

            friendshipService.addFriendship(userA, userB);

            if (callback != null) callback.run();
            onCancel();

        } catch (NumberFormatException e) {
            showError("Invalid user IDs: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error: " + e.getMessage());
        }
    }

    @FXML
    public void onCancel() {
        Stage st = (Stage) txtUserA.getScene().getWindow();
        st.close();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Add Friendship Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
