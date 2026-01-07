package app.controllers;

import core.service.FriendRequestService;
import core.AppService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class AddFriendsController {

    @FXML
    private ListView<String> listUsers;

    private final ObservableList<String> users =
            FXCollections.observableArrayList();

    private AppService appService;
    private FriendRequestService friendRequestService;
    private long loggedUserId;

    public void setServices(AppService appService,
                            FriendRequestService friendRequestService,
                            long loggedUserId) {

        this.appService = appService;
        this.friendRequestService = friendRequestService;
        this.loggedUserId = loggedUserId;

        loadUsers();
    }

    private void loadUsers() {
        users.setAll(appService.getUsernamesNotFriends(loggedUserId));
        listUsers.setItems(users);
    }

    @FXML
    private void onSendRequest() {
        String username = listUsers.getSelectionModel().getSelectedItem();
        if (username == null) return;

        long toUserId = appService.getUserIdByUsername(username);
        friendRequestService.sendFriendRequest(loggedUserId, toUserId);

        // scoatem userul din listă după trimitere
        users.remove(username);
    }
}
