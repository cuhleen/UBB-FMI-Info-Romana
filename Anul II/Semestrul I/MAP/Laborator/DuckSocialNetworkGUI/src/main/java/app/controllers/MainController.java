package app.controllers;

import core.AppService;
import core.model.Card;
import core.model.Duck;
import core.model.Person;
import core.repository.*;
import core.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class MainController {

    @FXML private ComboBox<String> comboFilter;

    private int currentPage = 0;
    private final int pageSize = 3;

    private List<Duck> allDucks;
    private List<Duck> filteredDucks;

    @FXML private TableView<Duck> duckTable;
    @FXML private TableColumn<Duck, Long> colId;
    @FXML private TableColumn<Duck, String> colUsername;
    @FXML private TableColumn<Duck, String> colEmail;
    @FXML private TableColumn<Duck, String> colPassword;
    @FXML private TableColumn<Duck, String> colTip;
    @FXML private TableColumn<Duck, Double> colViteza;
    @FXML private TableColumn<Duck, Double> colRezistenta;
    @FXML private TableColumn<Duck, String> colCard;

    private DuckService duckService = new DuckService(
            new RepoDuckDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"),
            new RepoUsersDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123")
    );

    @FXML private TableView<Person> personTable;
    @FXML private TableColumn<Person, Long> colPersonId;
    @FXML private TableColumn<Person, String> colPersonUsername;
    @FXML private TableColumn<Person, String> colPersonEmail;
    @FXML private TableColumn<Person, String> colPersonNume;
    @FXML private TableColumn<Person, String> colPersonPrenume;
    @FXML private TableColumn<Person, String> colPersonOcupatie;
    @FXML private TableColumn<Person, Double> colPersonEmpatie;
    @FXML private TableColumn<Person, String> colPersonPassword;
    @FXML private TableColumn<Person, LocalDate> colPersonDataNasterii;

    private PersonService personService = new PersonService(
            new RepoPersonDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"),
            new RepoUsersDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"),
            new FriendshipService(new RepoFriendshipDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"))
    );

    private List<Person> allPersons;
    private ObservableList<Person> masterPersonList;
    private int currentPersonPage = 0;
    private final int personPageSize = 3;

    private ObservableList<Duck> masterList;

    public void setDuckService(DuckService service) {
        this.duckService = service;
        loadTable();
    }

    @FXML private TableView<long[]> friendshipTable;
    @FXML private TableColumn<long[], Long> colFriendA;
    @FXML private TableColumn<long[], Long> colFriendB;

    private List<long[]> allFriendships;
    private ObservableList<long[]> masterFriendList;
    private int currentFriendPage = 0;
    private final int friendPageSize = 3;

    private FriendshipService friendshipService = new FriendshipService(
            new RepoFriendshipDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123")
    );

    @FXML
    public void initialize() {
        masterPersonList = FXCollections.observableArrayList();
        masterList = FXCollections.observableArrayList();
        masterFriendList = FXCollections.observableArrayList();

        personTable.setItems(masterPersonList);
        duckTable.setItems(masterList);
        friendshipTable.setItems(masterFriendList);

        // Coloane tabel
        colId.setCellValueFactory(d -> new javafx.beans.property.SimpleLongProperty(d.getValue().getId()).asObject());
        colUsername.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getUsername()));
        colEmail.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEmail()));
        colPassword.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getPassword()));
        colTip.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getTip().toString()));
        colViteza.setCellValueFactory(d -> new javafx.beans.property.SimpleDoubleProperty(d.getValue().getViteza()).asObject());
        colRezistenta.setCellValueFactory(d -> new javafx.beans.property.SimpleDoubleProperty(d.getValue().getRezistenta()).asObject());
        colCard.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCard().map(Card::getNumeCard).orElse("")));

        comboFilter.setItems(FXCollections.observableArrayList(
                "ALL", "FLYING", "SWIMMING", "FLYING_AND_SWIMMING"
        ));
        comboFilter.getSelectionModel().select("ALL");

        loadTable();

        colPersonId.setCellValueFactory(p -> new javafx.beans.property.SimpleLongProperty(p.getValue().getId()).asObject());
        colPersonUsername.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getUsername()));
        colPersonEmail.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getEmail()));
        colPersonNume.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getNume()));
        colPersonPrenume.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getPrenume()));
        colPersonOcupatie.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getOcupatie()));
        colPersonEmpatie.setCellValueFactory(p -> new javafx.beans.property.SimpleDoubleProperty(p.getValue().getNivelEmpatie()).asObject());
        colPersonPassword.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getPassword()));
        colPersonDataNasterii.setCellValueFactory(p -> new javafx.beans.property.SimpleObjectProperty<>(p.getValue().getDataNasterii()));


        loadPersonTable();

        colFriendA.setCellValueFactory(f -> new javafx.beans.property.SimpleLongProperty(f.getValue()[0]).asObject());
        colFriendB.setCellValueFactory(f -> new javafx.beans.property.SimpleLongProperty(f.getValue()[1]).asObject());

        loadFriendshipTable();

        VBox.setVgrow(duckTable, Priority.ALWAYS);
        VBox.setVgrow(personTable, Priority.ALWAYS);
        VBox.setVgrow(friendshipTable, Priority.ALWAYS);

        duckTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        colId.prefWidthProperty().bind(duckTable.widthProperty().multiply(0.10));
        colUsername.prefWidthProperty().bind(duckTable.widthProperty().multiply(0.15));
        colEmail.prefWidthProperty().bind(duckTable.widthProperty().multiply(0.20));
        colPassword.prefWidthProperty().bind(duckTable.widthProperty().multiply(0.10));
        colTip.prefWidthProperty().bind(duckTable.widthProperty().multiply(0.15));
        colViteza.prefWidthProperty().bind(duckTable.widthProperty().multiply(0.10));
        colRezistenta.prefWidthProperty().bind(duckTable.widthProperty().multiply(0.10));
        colCard.prefWidthProperty().bind(duckTable.widthProperty().multiply(0.10));

        personTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        colPersonId.prefWidthProperty().bind(personTable.widthProperty().multiply(0.10));
        colPersonUsername.prefWidthProperty().bind(personTable.widthProperty().multiply(0.15));
        colPersonEmail.prefWidthProperty().bind(personTable.widthProperty().multiply(0.20));
        colPersonPassword.prefWidthProperty().bind(personTable.widthProperty().multiply(0.10));
        colPersonNume.prefWidthProperty().bind(personTable.widthProperty().multiply(0.10));
        colPersonPrenume.prefWidthProperty().bind(personTable.widthProperty().multiply(0.10));
        colPersonDataNasterii.prefWidthProperty().bind(personTable.widthProperty().multiply(0.10));
        colPersonOcupatie.prefWidthProperty().bind(personTable.widthProperty().multiply(0.10));
        colPersonEmpatie.prefWidthProperty().bind(personTable.widthProperty().multiply(0.05));
        friendshipTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        colFriendA.prefWidthProperty().bind(friendshipTable.widthProperty().multiply(0.50));
        colFriendB.prefWidthProperty().bind(friendshipTable.widthProperty().multiply(0.50));



    }

    private void updatePage() {
        int fromIndex = currentPage * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, filteredDucks.size());

        if (fromIndex >= filteredDucks.size()) {
            currentPage = 0;
            fromIndex = 0;
            toIndex = Math.min(pageSize, filteredDucks.size());
        }

        masterList = FXCollections.observableArrayList(
                filteredDucks.subList(fromIndex, toIndex)
        );

        duckTable.setItems(masterList);
    }

    private void applyFilter() {
        String selected = comboFilter.getValue();

        if (selected == null || selected.equals("ALL")) {
            filteredDucks = allDucks;
        } else {
            filteredDucks = allDucks.stream()
                    .filter(d -> d.getTip().toString().equals(selected))
                    .toList();
        }

        currentPage = 0;
        updatePage();
    }

    private void loadTable() {
        allDucks = duckService.findAll();
        applyFilter();
        for (Duck d : masterList) {
            System.out.println("Duck " + d.getId() + " card = " + d.getCard());
        }
    }

    @FXML
    public void onUpdate() {
        Duck selected = duckTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Select a duck first!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ducks/updateDuck.fxml"));
            Parent root = loader.load();

            UpdateDuckController ctrl = loader.getController();
            ctrl.setDuckService(duckService);
            ctrl.setDuck(selected);
            ctrl.setCallback(this::loadTable);

            Stage stage = new Stage();
            stage.setTitle("Update Duck");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to open update window: " + e.getMessage());
        }
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Info");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    @FXML
    public void onPrevPage() {
        if (currentPage > 0) {
            currentPage--;
            updatePage();
        }
    }

    @FXML
    public void onNextPage() {
        if ((currentPage + 1) * pageSize < filteredDucks.size()) {
            currentPage++;
            updatePage();
        }
    }

    @FXML
    public void onFilterChanged() {
        applyFilter();
    }

    @FXML
    public void onAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ducks/addDuck.fxml"));
            Parent root = loader.load();

            AddDuckController ctrl = loader.getController();
            ctrl.setDuckService(duckService);
            ctrl.setCallback(this::loadTable);

            Stage stage = new Stage();
            stage.setTitle("Add Duck");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to open add window: " + e.getMessage());
        }
    }

    @FXML
    public void onDelete() {
        Duck selected = duckTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Select a duck first!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete duck " + selected.getUsername() + "?");

        if (confirm.showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {
            duckService.deleteDuck(selected.getId());
            loadTable();
            loadPersonTable();
            loadFriendshipTable();
        }
    }

    private void loadPersonTable() {
        allPersons = personService.findAll();
        updatePersonPage();
    }

    private void updatePersonPage() {
        int fromIndex = currentPersonPage * personPageSize;
        int toIndex = Math.min(fromIndex + personPageSize, allPersons.size());

        if (fromIndex >= allPersons.size()) {
            currentPersonPage = 0;
            fromIndex = 0;
            toIndex = Math.min(personPageSize, allPersons.size());
        }

        //masterPersonList = FXCollections.observableArrayList(allPersons.subList(fromIndex, toIndex));
//        personTable.setItems(masterPersonList);
        masterPersonList.setAll(allPersons.subList(fromIndex, toIndex));
    }

    @FXML
    public void onAddPerson() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/persons/addPerson.fxml"));
            Parent root = loader.load();

            AddPersonController ctrl = loader.getController();
            ctrl.setPersonService(personService);
            ctrl.setCallback(this::loadPersonTable);

            Stage stage = new Stage();
            stage.setTitle("Add Person");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to open add Person window: " + e.getMessage());
        }
    }


    public void onDeletePerson(ActionEvent actionEvent) {
        Person selected = personTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Select a person first!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete " + selected.getUsername() + "?");

        if (confirm.showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {

            friendshipService.deleteFriendshipsForUser(selected.getId());

            personService.deletePerson(selected.getId());
            loadTable();
            loadPersonTable();
            loadFriendshipTable();
        }

    }

    @FXML
    public void onPrevPersonPage() {
        if (currentPersonPage > 0) {
            currentPersonPage--;
            updatePersonPage();
        }
    }

    @FXML
    public void onNextPersonPage() {
        if ((currentPersonPage + 1) * personPageSize < allPersons.size()) {
            currentPersonPage++;
            updatePersonPage();
        }
    }

    private void loadFriendshipTable() {
        allFriendships = friendshipService.getAllFriendships();
        updateFriendPage();
    }

    private void updateFriendPage() {
        int fromIndex = currentFriendPage * friendPageSize;
        int toIndex = Math.min(fromIndex + friendPageSize, allFriendships.size());

        if (fromIndex >= allFriendships.size()) {
            currentFriendPage = 0;
            fromIndex = 0;
            toIndex = Math.min(friendPageSize, allFriendships.size());
        }

        masterFriendList = FXCollections.observableArrayList(
                allFriendships.subList(fromIndex, toIndex)
        );

        friendshipTable.setItems(masterFriendList);
    }

    @FXML
    public void onPrevFriendPage() {
        if (currentFriendPage > 0) {
            currentFriendPage--;
            updateFriendPage();
        }
    }

    @FXML
    public void onNextFriendPage() {
        if ((currentFriendPage + 1) * friendPageSize < allFriendships.size()) {
            currentFriendPage++;
            updateFriendPage();
        }
    }

    @FXML
    public void onAddFriendship() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/friendships/addFriendship.fxml"));
            Parent root = loader.load();

            AddFriendshipController ctrl = loader.getController();
            ctrl.setFriendshipService(friendshipService);
            ctrl.setCallback(this::loadFriendshipTable);

            Stage stage = new Stage();
            stage.setTitle("Add Friendship");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            showAlert("Failed to open add friendship window: " + e.getMessage());
        }
    }

    @FXML
    public void onDeleteFriendship() {
        long[] selected = friendshipTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Select a friendship first!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete friendship between " + selected[0] + " and " + selected[1] + "?");

        if (confirm.showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {
            boolean success = friendshipService.removeFriendship(selected[0], selected[1]);
            if (!success) {
                showAlert("Failed to delete friendship.");
            }
            loadFriendshipTable();
        }
    }

    AppService appService = new AppService(
            new DuckService(new RepoDuckDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"), new RepoUsersDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123")),
            new PersonService(new RepoPersonDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"), new RepoUsersDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"), new FriendshipService(new RepoFriendshipDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"))),
            new CardService(new RepoCardDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"), new RepoDuckDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123")),
            new FriendshipService(new RepoFriendshipDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123")),
            new RaceEventService(new RepoEventDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"), new RepoDuckDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"), new PersonService(new RepoPersonDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"), new RepoUsersDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"), new FriendshipService(new RepoFriendshipDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"))), new MessageService(new RepoMessageDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123")), new NotificationService(new RepoNotificationDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"))),
            new RepoUsersDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"),
            new FriendRequestService(new RepoFriendRequestDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"), new FriendshipService(new RepoFriendshipDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"))),
            new NotificationService(new RepoNotificationDB("jdbc:postgresql://localhost:5432/duckdb", "duckuser", "parola123"))
    );

    @FXML
    public void onShowCommunityCount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/friendships/communityCount.fxml"));
            Parent root = loader.load();

            CommunityCountController ctrl = loader.getController();
            ctrl.setService(appService);

            Stage st = new Stage();
            st.setTitle("Număr Comunități");
            st.setScene(new Scene(root));
            st.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onShowMostSocialCommunity() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/friendships/mostSocialCommunity.fxml"));
            Parent root = loader.load();

            MostSocialCommunityController ctrl = loader.getController();
            ctrl.setService(appService);

            Stage st = new Stage();
            st.setTitle("Cea Mai Sociabilă Comunitate");
            st.setScene(new Scene(root));
            st.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
