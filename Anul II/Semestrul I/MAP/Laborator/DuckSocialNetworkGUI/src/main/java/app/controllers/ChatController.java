package app.controllers;

import core.model.*;
import core.service.FriendRequestService;
import core.service.MessageService;
import core.AppService;
import core.service.NotificationService;
import core.service.RaceEventService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ChatController {

    private Timeline refreshTimeline;

    @FXML
    private void initialize() {
        listRaces.setItems(races);
        listRaces.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(RaceEvent race, boolean empty) {
                super.updateItem(race, empty);
                if (empty || race == null) { setText(null); return; }

                boolean subscribed = race.getSubscribers().stream().anyMatch(u -> u.getId() == loggedUserId);
                setText(race.getTitle() + " by " + race.getCreator().getUsername() +
                        (subscribed ? " [Subscribed]" : ""));
            }
        });

        listRaces.setOnMouseClicked(event -> {
            RaceEvent selectedRace = listRaces.getSelectionModel().getSelectedItem();
            if (selectedRace != null) {
                showRaceOptions(selectedRace);
            }
        });

        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> loadConversation()));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();

        txtMessage.setOnAction(event -> onSendMessage());

        Timeline notifTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateNotifButton()));
        notifTimeline.setCycleCount(Timeline.INDEFINITE);
        notifTimeline.play();

        Timeline leftBarTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> loadUsers()));
        leftBarTimeline.setCycleCount(Timeline.INDEFINITE);
        leftBarTimeline.play();
    }

    @FXML private ListView<String> listUsers;
    @FXML private ListView<Message> listMessages;
    @FXML private TextField txtMessage;
    @FXML private ListView<RaceEvent> listRaces;
    @FXML private Label lblReplyingTo;

    private final ObservableList<String> users = FXCollections.observableArrayList();
    private final ObservableList<Message> messages = FXCollections.observableArrayList();
    private final ObservableList<RaceEvent> races = FXCollections.observableArrayList();

    private AppService appService;
    private MessageService messageService;
    private RaceEventService raceEventService;

    private long loggedUserId;
    private long selectedUserId;
    private Long replyToId = null;

    public void setServices(AppService appService, MessageService messageService, long loggedUserId, RaceEventService raceEventService) {
        this.appService = appService;
        this.messageService = messageService;
        this.loggedUserId = loggedUserId;
        this.raceEventService = raceEventService;
        loadUsers();
        loadRaces();
    }

    private void loadUsers() {
        users.setAll(appService.getFriendUsernames(loggedUserId));
        listUsers.setItems(users);
        listUsers.setOnMouseClicked(this::onUserSelected);
    }

    private void onUserSelected(MouseEvent event) {
        String selectedUsername = listUsers.getSelectionModel().getSelectedItem();
        if (selectedUsername == null) return;
        selectedUserId = appService.getUserIdByUsername(selectedUsername);
        loadConversation();
    }

    private void loadConversation() {
        if (selectedUserId == 0) return;

        List<Message> conv = messageService.getConversation(loggedUserId, selectedUserId);
        messages.setAll(conv);
        listMessages.setItems(messages);

        listMessages.setCellFactory(lv -> {
            ListCell<Message> cell = new ListCell<>() {
                @Override
                protected void updateItem(Message msg, boolean empty) {
                    super.updateItem(msg, empty);

                    if (empty || msg == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        String time = msg.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm"));

                        String prefix = msg.getSenderId() == loggedUserId
                                ? "Eu: "
                                : appService.findUser(msg.getSenderId())
                                .map(user -> user.getUsername() + ": ")
                                .orElse("??? : ");

                        // Prefix reply, dacă există
                        StringBuilder replyPrefix = new StringBuilder();
                        if (msg.getReplyToId() != null) {
                            final long replyId = msg.getReplyToId();
                            messageService.getById(replyId).ifPresentOrElse(
                                    originalMsg -> replyPrefix.append("Reply to [").append(originalMsg.getContent()).append("]: "),
                                    () -> replyPrefix.append("Reply to [Unknown]: ")
                            );
                        }

                        setText(time + " " + replyPrefix + prefix + msg.getContent());

                        // Highlight pentru mesajul selectat pentru reply
                        if (replyToId != null && replyToId.equals(msg.getId())) {
                            setStyle("-fx-background-color: lightyellow;");
                        } else {
                            setStyle("");
                        }
                    }
                }
            };

            // Click pe mesaj pentru reply
            cell.setOnMouseClicked(event -> {
                Message selected = cell.getItem();
                if (selected != null) {
                    replyToId = selected.getId();
                    lblReplyingTo.setText("Replying to: " + selected.getContent());
                    listMessages.refresh();
                }
            });

            return cell;
        });
    }

    @FXML
    private void onSendMessage() {
        String text = txtMessage.getText().trim();
        if (text.isEmpty() || selectedUserId == 0) return;

        messageService.sendMessage(loggedUserId, selectedUserId, text, replyToId);

        txtMessage.clear();
        replyToId = null;
        lblReplyingTo.setText("");
        loadConversation();
    }

    @FXML
    private void onAddFriends() {
        // Creăm o nouă fereastră
        Stage stage = new Stage();
        stage.setTitle("Add Friends");

        // VBox principal
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        // ListView cu utilizatorii care nu sunt încă prieteni
        ListView<String> listNotFriends = new ListView<>();
        listNotFriends.setItems(FXCollections.observableArrayList(
                appService.getUsernamesNotFriends(loggedUserId)
        ));

        // Buton Trimite cerere
        Button btnSendRequest = new Button("Trimite cerere de prietenie");
        btnSendRequest.setOnAction(e -> {
            String selectedUser = listNotFriends.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                // mesaj de eroare simplu
                Alert alert = new Alert(Alert.AlertType.WARNING, "Selectează un utilizator!");
                alert.showAndWait();
                return;
            }

            long toUserId = appService.getUserIdByUsername(selectedUser);
            try {
                FriendRequestService frs = appService.getFriendRequestService();
                frs.sendFriendRequest(loggedUserId, toUserId);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cerere trimisă!");
                alert.showAndWait();

                // scoatem utilizatorul din listă după ce trimite cererea
                listNotFriends.getItems().remove(selectedUser);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                alert.showAndWait();
            }
        });

        // Aranjăm elementele
        root.getChildren().addAll(listNotFriends, btnSendRequest);

        // Scene și Stage
        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.initOwner(listUsers.getScene().getWindow()); // fereastra principală ca owner
        stage.show();
    }

    @FXML
    private Button btnViewNotifs;

    @FXML
    public void onViewNotifs(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setTitle("Notificări");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        // 1 Cereri de prietenie
        FriendRequestService friendRequestService = appService.getFriendRequestService();
        List<FriendRequest> pendingRequests = friendRequestService.getPendingRequests(loggedUserId);

        if (pendingRequests.isEmpty()) {
            Label lblEmptyFR = new Label("Nu există cereri noi de prietenie.");
            root.getChildren().add(lblEmptyFR);
        } else {
            for (FriendRequest fr : pendingRequests) {
                HBox row = new HBox(10);
                row.setPadding(new Insets(5));

                String fromUsername = appService.getUsernameById(fr.getFromUserId());
                Label lblRequest = new Label(fromUsername);
                Button btnAccept = new Button("Accept");
                Button btnReject = new Button("Reject");

                btnAccept.setOnAction(e -> {
                    try {
                        friendRequestService.acceptRequest(fr.getId());
                        root.getChildren().remove(row);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Prietenie adăugată!");
                        alert.showAndWait();
                        loadUsers(); // refresh lista de prieteni
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                        alert.showAndWait();
                    }
                });

                btnReject.setOnAction(e -> {
                    try {
                        friendRequestService.rejectRequest(fr.getId());
                        root.getChildren().remove(row);
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                        alert.showAndWait();
                    }
                });

                row.getChildren().addAll(lblRequest, btnAccept, btnReject);
                root.getChildren().add(row);
            }
        }

        // 2 Notificări RaceEvent
        NotificationService notificationService = appService.getNotificationService();
        List<Notification> raceNotifs = notificationService.getUnreadForUser(loggedUserId);
        System.out.println("[DEBUG] Loaded notifications for user " + loggedUserId + ": " + raceNotifs.size());
        for (Notification n : raceNotifs) {
            System.out.println("[DEBUG] Notification: " + n.getContent() + " (read=" + n.isRead() + ")");
        }

        if (raceNotifs.isEmpty()) {
            Label lblEmptyRace = new Label("Nu există notificări noi de la curse.");
            root.getChildren().add(lblEmptyRace);
        } else {
            for (Notification n : raceNotifs) {
                HBox row = new HBox(10);
                row.setPadding(new Insets(5));

                Label lblText = new Label(n.getContent());
                Button btnMarkRead = new Button("Marchează ca citit");

                btnMarkRead.setOnAction(e -> {
                    notificationService.markRead(n.getId());
                    root.getChildren().remove(row);
                });

                row.getChildren().addAll(lblText, btnMarkRead);
                root.getChildren().add(row);
            }
        }

        Scene scene = new Scene(root, 400, 200 + (pendingRequests.size() + raceNotifs.size()) * 50);
        stage.setScene(scene);
        stage.initOwner(listUsers.getScene().getWindow());
        stage.show();
    }

    private void updateNotifButton() {
        int friendReqCount = appService.getFriendRequestService()
                .getPendingRequests(loggedUserId).size();

        int raceNotifCount = appService.getNotificationService()
                .getUnreadForUser(loggedUserId).size();

        int pendingCount = friendReqCount + raceNotifCount;

        if (pendingCount > 0) {
            btnViewNotifs.setText("Vezi notificări (" + pendingCount + ")");
        } else {
            btnViewNotifs.setText("Vezi notificări");
        }
    }


    // -----------------------------
    // RaceEvent actions
    // -----------------------------
    private void loadRaces() {
        List<RaceEvent> allRaces = raceEventService.findAll();
        races.setAll(allRaces);
    }

    private void showRaceOptions(RaceEvent race) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Race Event Options");
        alert.setHeaderText(race.getTitle() + " by " + race.getCreator().getUsername());

        ButtonType subscribeBtn = new ButtonType("Subscribe");
        ButtonType unsubscribeBtn = new ButtonType("Unsubscribe");
        ButtonType startBtn = new ButtonType("Start Race");
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        boolean subscribed = race.getSubscribers().stream().anyMatch(u -> u.getId() == loggedUserId);

        if (race.getCreator().getId() == loggedUserId) {
            // creator poate să dea start
            alert.getButtonTypes().setAll(startBtn, cancelBtn);
        } else if (subscribed) {
            alert.getButtonTypes().setAll(unsubscribeBtn, cancelBtn);
        } else {
            alert.getButtonTypes().setAll(subscribeBtn, cancelBtn);
        }

        Optional<ButtonType> result = alert.showAndWait();
        User loggedUser = appService.findUser(loggedUserId).orElseThrow(() ->
                new RuntimeException("Logged user not found"));
        result.ifPresent(bt -> {
            if (bt == subscribeBtn) raceEventService.subscribe(race.getId(), loggedUser);
            else if (bt == unsubscribeBtn) raceEventService.unsubscribe(race.getId(), loggedUser);
            else if (bt == startBtn) {
                // start race asincron
                raceEventService.startRaceAsync(race.getId())
                        .thenAccept(res -> {
                            // tot ce ține de UI trebuie în Platform.runLater
                            Platform.runLater(() -> {
                                // pop-up cu rezultat
                                showRaceResult(res);

                                // refresh lista de curse (opțional)
                                loadRaces();

                                // eventual disable butonul de start dacă vrei
                            });
                        })
                        .exceptionally(ex -> {
                            // afișare eroare dacă ceva merge prost
                            Platform.runLater(() -> {
                                Alert alertErr = new Alert(Alert.AlertType.ERROR);
                                alertErr.setTitle("Race Error");
                                alertErr.setHeaderText("Race failed");
                                alertErr.setContentText(ex.getMessage());
                                alertErr.showAndWait();
                            });
                            return null;
                        });
            }

        });

        loadRaces();
    }

    private void showRaceResult(RaceEvent.RaceResult res) {
        StringBuilder sb = new StringBuilder();
        for (String line : res.lines) sb.append(line).append("\n");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Race Result");
        alert.setHeaderText("Overall time: " + res.overallTime + " s");
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }
}
