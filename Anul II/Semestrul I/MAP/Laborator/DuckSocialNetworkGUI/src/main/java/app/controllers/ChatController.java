package app.controllers;

import core.model.Message;
import core.service.MessageService;
import core.AppService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChatController {

    private Timeline refreshTimeline;
    @FXML private Label lblReplyingTo;
    private Long replyToId = null;

    @FXML
    private void initialize() {
        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> loadConversation()));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
        txtMessage.setOnAction(event -> onSendMessage());
    }

    @FXML private ListView<String> listUsers;
    @FXML private ListView<Message> listMessages;
    @FXML private TextField txtMessage;

    private final ObservableList<String> users = FXCollections.observableArrayList();
    private final ObservableList<Message> messages = FXCollections.observableArrayList();

    private AppService appService;       // pentru id-uri și username-uri
    private MessageService messageService;

    private long loggedUserId;
    private long selectedUserId;

    public void setServices(AppService appService, MessageService messageService, long loggedUserId) {
        this.appService = appService;
        this.messageService = messageService;
        this.loggedUserId = loggedUserId;
        loadUsers();
    }

    private void loadUsers() {
        // toți userii fără user-ul logat
        users.setAll(appService.getAllUsernamesExcept(loggedUserId));
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

        // Preluăm conversația între utilizatori
        List<Message> conv = messageService.getConversation(loggedUserId, selectedUserId);
        messages.setAll(conv);
        listMessages.setItems(messages);

        // CellFactory pentru a diferenția mesajele trimise vs primite și reply
        listMessages.setCellFactory(lv -> {
            ListCell<Message> cell = new ListCell<>() {
                @Override
                protected void updateItem(Message msg, boolean empty) {
                    super.updateItem(msg, empty);

                    if (empty || msg == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Formatare oră
                        String time = msg.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm"));

                        // Prefix: "Eu: " dacă l-am trimis noi, altfel username-ul expeditorului
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

}
