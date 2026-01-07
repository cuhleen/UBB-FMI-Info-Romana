package app;

import app.controllers.AllUsersController;
import app.controllers.LoginController;
import app.controllers.MainController;
import core.AppService;
import core.repository.*;
import core.service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {

    private RepoDuckDB duckRepository;
    private DuckService duckService;
    private RepoPersonDB personRepository;
    private PersonService personService;
    private RepoFriendshipDB friendshipRepository;
    private FriendshipService friendshipService;
    private RepoCardDB cardRepository;
    private CardService cardService;
    private AppService appService;
    private RepoEventDB eventRepository;
    private RaceEventService raceEventService;
    private RepoFriendRequestDB friendRequestRepository;
    private FriendRequestService friendRequestService;
    private RepoMessageDB messageRepository;
    private MessageService messageService;
    private RepoNotificationDB notificationRepository;
    private NotificationService notificationService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        duckRepository = new RepoDuckDB(
                "jdbc:postgresql://localhost:5432/duckdb",
                "duckuser",
                "parola123"
        );

        RepoUsersDB repoUsers = new RepoUsersDB(
                "jdbc:postgresql://localhost:5432/duckdb",
                "duckuser",
                "parola123");

        duckService = new DuckService(duckRepository, repoUsers);

        personRepository = new RepoPersonDB(
                "jdbc:postgresql://localhost:5432/duckdb",
                "duckuser",
                "parola123"
        );

        friendshipRepository = new RepoFriendshipDB("jdbc:postgresql://localhost:5432/duckdb",
                "duckuser",
                "parola123");

        friendshipService = new FriendshipService(friendshipRepository);

        personService = new PersonService(personRepository, repoUsers, friendshipService);

        cardRepository = new RepoCardDB("jdbc:postgresql://localhost:5432/duckdb",
                "duckuser",
                "parola123");

        cardService = new CardService(cardRepository, duckRepository);

        eventRepository = new RepoEventDB("jdbc:postgresql://localhost:5432/duckdb",
                "duckuser",
                "parola123");

        messageRepository = new RepoMessageDB("jdbc:postgresql://localhost:5432/duckdb",
                "duckuser",
                "parola123");

        messageService = new MessageService(messageRepository);


        notificationRepository = new RepoNotificationDB("jdbc:postgresql://localhost:5432/duckdb",
                "duckuser",
                "parola123");

        notificationService = new NotificationService(notificationRepository);

        raceEventService = new RaceEventService(eventRepository, duckRepository, personService, messageService, notificationService);

        friendRequestRepository = new RepoFriendRequestDB("jdbc:postgresql://localhost:5432/duckdb",
                "duckuser",
                "parola123");

        friendRequestService = new FriendRequestService(friendRequestRepository, friendshipService);

        appService = new AppService(duckService, personService, cardService, friendshipService, raceEventService, repoUsers, friendRequestService, notificationService);

        Stage userListStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/users/all-users.fxml"));
        AnchorPane root = loader.load();

        AllUsersController controller = loader.getController();
        controller.setAppService(appService);

        userListStage.setScene(new Scene(root));
        userListStage.setTitle("Toți utilizatorii");
        userListStage.show();


        initView(primaryStage);
//        primaryStage.setWidth(900);
//        primaryStage.setHeight(600);
//        primaryStage.setTitle("Duck Manager");
        openLoginWindow(primaryStage, "Login - Fereastra 1");

        Stage secondStage = new Stage();
        openLoginWindow(secondStage, "Login - Fereastra 2");
    }

    private void openLoginWindow(Stage stage, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        AnchorPane root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setWidth(300);
        stage.setHeight(200);

        // injectează serviciile în controller
        LoginController controller = loader.getController();
        controller.setUserService(duckService, personService, appService);

        stage.show();
    }

    private void initView(Stage primaryStage) throws IOException {

//        System.out.println(getClass().getClassLoader().getResource("views/ducks/main-view.fxml"));
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getClassLoader().getResource("views/ducks/main-view.fxml"));
//        AnchorPane rootLayout = loader.load();
//
//        Scene scene = new Scene(rootLayout);
//        primaryStage.setScene(scene);
//
//        MainController controller = loader.getController();
//        controller.setDuckService(duckService);

        System.out.println(getClass().getClassLoader().getResource("views/login.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/login.fxml"));
        AnchorPane rootLayout = loader.load();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        LoginController controller = loader.getController();
        controller.setUserService(duckService, personService, appService);
    }
}
