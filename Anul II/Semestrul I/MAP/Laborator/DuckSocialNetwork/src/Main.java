import com.org.example.service.*;
import com.org.example.repo.*;
import com.org.example.users.Duck;
import com.org.example.users.Person;
import com.org.example.users.Card;
import com.org.example.model.RaceEvent;
import com.org.example.model.Event;

import java.util.*;
import java.sql.DriverManager;

public class Main {
    private static AppService appService;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Try to load H2 driver
            Class.forName("org.h2.Driver");

            // Initialize database repositories with an in-memory H2 database
            String dbUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
            String dbUser = "sa";
            String dbPass = "";

            // Create repositories first - this ensures tables exist when we populate
            RepoDuckDB repoDuck = new RepoDuckDB(dbUrl, dbUser, dbPass);
            RepoPersonDB repoPerson = new RepoPersonDB(dbUrl, dbUser, dbPass);
            RepoCardDB repoCard = new RepoCardDB(dbUrl, dbUser, dbPass);
            RepoFriendshipDB repoFriendship = new RepoFriendshipDB(dbUrl, dbUser, dbPass);
            RepoEventDB repoEvent = new RepoEventDB(dbUrl, dbUser, dbPass);

            // Initialize and populate the database using the same connection pool
            // This needs to be done with a connection that stays open
            try (java.sql.Connection conn = java.sql.DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
                com.org.example.database.DatabaseInitializer.initializeInMemoryDatabase(conn);
                com.org.example.database.DatabasePopulation.populateWithSampleData(conn);
                System.out.println("Database initialized and populated successfully!");
            }

            // Create services
            DuckService duckService = new DuckService(repoDuck);
            PersonService personService = new PersonService(repoPerson);
            CardService cardService = new CardService(repoCard, repoDuck);
            FriendshipService friendshipService = new FriendshipService(repoFriendship);
            RaceEventService raceEventService = new RaceEventService(repoEvent, repoDuck, repoPerson);

            // Create main app service
            appService = new AppService(duckService, personService, cardService, friendshipService, raceEventService);

            // Main menu loop
            boolean running = true;
            while (running) {
                System.out.println("\n=== Duck Social Network Menu ===");
                System.out.println("1. Duck Management");
                System.out.println("2. Person Management");
                System.out.println("3. Card Management");
                System.out.println("4. Friendship Management");
                System.out.println("5. Race Event Management");
                System.out.println("6. Run Race Events");
                System.out.println("7. Community Analysis");
                System.out.println("8. Exit");
                System.out.print("Select an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        duckMenu();
                        break;
                    case 2:
                        personMenu();
                        break;
                    case 3:
                        cardMenu();
                        break;
                    case 4:
                        friendshipMenu();
                        break;
                    case 5:
                        raceEventMenu();
                        break;
                    case 6:
                        runRaceEvents();
                        break;
                    case 7:
                        communityAnalysisMenu();
                        break;
                    case 8:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("H2 Database driver not found. Please add h2.jar to your classpath.");
            System.err.println("Download it from: https://h2database.com/html/download.html");
            System.err.println("\nThe database initialization and population utilities are available in the com.org.example.database package.");
            System.err.println("Run 'java -cp out com.org.example.database.DatabaseManager' separately after adding H2 driver to classpath.");
        } catch (Exception e) {
            System.err.println("Error initializing the application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void duckMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Duck Management ---");
            System.out.println("1. Add Duck");
            System.out.println("2. View Duck by ID");
            System.out.println("3. View All Ducks");
            System.out.println("4. Update Duck");
            System.out.println("5. Delete Duck");
            System.out.println("6. Change Duck Performance");
            System.out.println("7. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addDuck();
                    break;
                case 2:
                    viewDuckById();
                    break;
                case 3:
                    viewAllDucks();
                    break;
                case 4:
                    updateDuck();
                    break;
                case 5:
                    deleteDuck();
                    break;
                case 6:
                    changeDuckPerformance();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void personMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Person Management ---");
            System.out.println("1. Add Person");
            System.out.println("2. View Person by ID");
            System.out.println("3. View All Persons");
            System.out.println("4. Update Person");
            System.out.println("5. Delete Person");
            System.out.println("6. Change Occupation");
            System.out.println("7. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addPerson();
                    break;
                case 2:
                    viewPersonById();
                    break;
                case 3:
                    viewAllPersons();
                    break;
                case 4:
                    updatePerson();
                    break;
                case 5:
                    deletePerson();
                    break;
                case 6:
                    changePersonOccupation();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void cardMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Card Management ---");
            System.out.println("1. Add Card");
            System.out.println("2. View Card by ID");
            System.out.println("3. View All Cards");
            System.out.println("4. Update Card");
            System.out.println("5. Delete Card");
            System.out.println("6. Add Duck to Card");
            System.out.println("7. Remove Duck from Card");
            System.out.println("8. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addCard();
                    break;
                case 2:
                    viewCardById();
                    break;
                case 3:
                    viewAllCards();
                    break;
                case 4:
                    updateCard();
                    break;
                case 5:
                    deleteCard();
                    break;
                case 6:
                    addDuckToCard();
                    break;
                case 7:
                    removeDuckFromCard();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void friendshipMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Friendship Management ---");
            System.out.println("1. Add Friendship");
            System.out.println("2. Remove Friendship");
            System.out.println("3. View All Friendships");
            System.out.println("4. Remove All Friendships for User");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addFriendship();
                    break;
                case 2:
                    removeFriendship();
                    break;
                case 3:
                    viewAllFriendships();
                    break;
                case 4:
                    removeAllFriendshipsForUser();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void raceEventMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Race Event Management ---");
            System.out.println("1. Create Race Event");
            System.out.println("2. View Race Event by ID");
            System.out.println("3. View All Race Events");
            System.out.println("4. Subscribe User to Event");
            System.out.println("5. Unsubscribe User from Event");
            System.out.println("6. Add Participant to Event");
            System.out.println("7. Remove Participant from Event");
            System.out.println("8. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    createRaceEvent();
                    break;
                case 2:
                    viewRaceEventById();
                    break;
                case 3:
                    viewAllRaceEvents();
                    break;
                case 4:
                    subscribeUserToEvent();
                    break;
                case 5:
                    unsubscribeUserFromEvent();
                    break;
                case 6:
                    addParticipantToEvent();
                    break;
                case 7:
                    removeParticipantFromEvent();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void communityAnalysisMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Community Analysis ---");
            System.out.println("1. Get Number of Communities");
            System.out.println("2. Get Most Sociable Community");
            System.out.println("3. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    getNumarComunitati();
                    break;
                case 2:
                    getCeaMaiSociabilaComunitate();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Duck Operations
    private static void addDuck() {
        System.out.print("Enter duck ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // consume newline
        System.out.print("Enter duck username: ");
        String username = scanner.nextLine();
        System.out.print("Enter duck email: ");
        String email = scanner.nextLine();
        System.out.print("Enter duck password: ");
        String password = scanner.nextLine();
        System.out.print("Enter duck type (SWIMMING, FLYING, FLYING_AND_SWIMMING): ");
        String type = scanner.nextLine();
        System.out.print("Enter duck speed: ");
        double speed = scanner.nextDouble();
        System.out.print("Enter duck resistance: ");
        double resistance = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        Duck duck = new Duck(id, username, email, password, com.org.example.enums.TipRata.valueOf(type.toUpperCase()), speed, resistance);
        appService.getDuckService().addDuck(duck);
        System.out.println("Duck added successfully!");
    }

    private static void viewDuckById() {
        System.out.print("Enter duck ID: ");
        long id = scanner.nextLong();
        Optional<Duck> duck = appService.getDuckService().getDuck(id);
        if (duck.isPresent()) {
            System.out.println(duck.get());
        } else {
            System.out.println("Duck not found!");
        }
    }

    private static void viewAllDucks() {
        List<Duck> ducks = appService.getDuckService().findAll();
        System.out.println("All Ducks:");
        if (ducks.isEmpty()) {
            System.out.println("No ducks found.");
        } else {
            for (Duck duck : ducks) {
                System.out.println(duck);
            }
        }
    }

    private static void updateDuck() {
        System.out.print("Enter duck ID to update: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // consume newline

        Optional<Duck> duckOpt = appService.getDuckService().getDuck(id);
        if (duckOpt.isPresent()) {
            System.out.print("Enter new username: ");
            String username = scanner.nextLine();
            System.out.print("Enter new email: ");
            String email = scanner.nextLine();
            System.out.print("Enter new password: ");
            String password = scanner.nextLine();
            System.out.print("Enter new duck type (SWIMMING, FLYING, FLYING_AND_SWIMMING): ");
            String type = scanner.nextLine();
            System.out.print("Enter new speed: ");
            double speed = scanner.nextDouble();
            System.out.print("Enter new resistance: ");
            double resistance = scanner.nextDouble();
            scanner.nextLine(); // consume newline

            // Update all duck parameters
            boolean success = appService.getDuckService().updateDuck(id, username, email, password, com.org.example.enums.TipRata.valueOf(type.toUpperCase()), speed, resistance);

            if (success) {
                System.out.println("Duck updated successfully!");
            } else {
                System.out.println("Failed to update duck.");
            }
        } else {
            System.out.println("Duck not found!");
        }
    }

    private static void deleteDuck() {
        System.out.print("Enter duck ID to delete: ");
        long id = scanner.nextLong();
        boolean success = appService.getDuckService().deleteDuck(id);
        if (success) {
            System.out.println("Duck deleted successfully!");
        } else {
            System.out.println("Failed to delete duck.");
        }
    }

    private static void changeDuckPerformance() {
        System.out.print("Enter duck ID: ");
        long id = scanner.nextLong();
        System.out.print("Enter new speed: ");
        double speed = scanner.nextDouble();
        System.out.print("Enter new resistance: ");
        double resistance = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        appService.getDuckService().changePerformance(id, speed, resistance);
        System.out.println("Duck performance changed!");
    }

    // Person Operations
    private static void addPerson() {
        System.out.print("Enter person ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // consume newline
        System.out.print("Enter person username: ");
        String username = scanner.nextLine();
        System.out.print("Enter person email: ");
        String email = scanner.nextLine();
        System.out.print("Enter person password: ");
        String password = scanner.nextLine();
        System.out.print("Enter person first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter person last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter birth date (YYYY-MM-DD): ");
        String birthDateStr = scanner.nextLine();
        System.out.print("Enter occupation: ");
        String occupation = scanner.nextLine();
        System.out.print("Enter empathy level: ");
        double empathyLevel = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        Person person = new Person(id, username, email, password, firstName, lastName,
                                   java.time.LocalDate.parse(birthDateStr), occupation, empathyLevel);
        appService.getPersonService().addPerson(person);
        System.out.println("Person added successfully!");
    }

    private static void viewPersonById() {
        System.out.print("Enter person ID: ");
        long id = scanner.nextLong();
        Optional<Person> person = appService.getPersonService().getPerson(id);
        if (person.isPresent()) {
            System.out.println(person.get());
        } else {
            System.out.println("Person not found!");
        }
    }

    private static void viewAllPersons() {
        List<Person> persons = appService.getPersonService().findAll();
        System.out.println("All Persons:");
        if (persons.isEmpty()) {
            System.out.println("No persons found.");
        } else {
            for (Person person : persons) {
                System.out.println(person);
            }
        }
    }

    private static void updatePerson() {
        System.out.print("Enter person ID to update: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // consume newline

        Optional<Person> personOpt = appService.getPersonService().getPerson(id);
        if (personOpt.isPresent()) {
            System.out.println("Update person functionality not fully implemented in the service.");
        } else {
            System.out.println("Person not found!");
        }
    }

    private static void deletePerson() {
        System.out.print("Enter person ID to delete: ");
        long id = scanner.nextLong();
        // Note: deletePerson is not fully implemented in PersonService
        System.out.println("Delete person functionality not fully implemented in the service.");
    }

    private static void changePersonOccupation() {
        System.out.print("Enter person ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new occupation: ");
        String occupation = scanner.nextLine();

        appService.getPersonService().changeOccupation(id, occupation);
        System.out.println("Person occupation changed!");
    }

    // Card Operations
    private static void addCard() {
        System.out.print("Enter card ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // consume newline
        System.out.print("Enter card name: ");
        String cardName = scanner.nextLine();

        Card card = new Card(id, cardName);
        appService.getCardService().addCard(card);
        System.out.println("Card added successfully!");
    }

    private static void viewCardById() {
        System.out.print("Enter card ID: ");
        long id = scanner.nextLong();
        Optional<Card> card = appService.getCardService().getCard(id);
        if (card.isPresent()) {
            System.out.println(card.get());
        } else {
            System.out.println("Card not found!");
        }
    }

    private static void viewAllCards() {
        List<Card> cards = appService.getCardService().getAllCards();
        System.out.println("All Cards:");
        if (cards.isEmpty()) {
            System.out.println("No cards found.");
        } else {
            for (Card card : cards) {
                System.out.println(card);
            }
        }
    }

    private static void updateCard() {
        System.out.print("Enter card ID to update: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new card name: ");
        String cardName = scanner.nextLine();

        Optional<Card> cardOpt = appService.getCardService().getCard(id);
        if (cardOpt.isPresent()) {
            // Note: Card class doesn't have a setter for numeCard
            System.out.println("Update card functionality not implemented in Card class.");
        } else {
            System.out.println("Card not found!");
        }
    }

    private static void deleteCard() {
        System.out.print("Enter card ID to delete: ");
        long id = scanner.nextLong();
        boolean success = appService.getCardService().deleteCard(id);
        if (success) {
            System.out.println("Card deleted successfully!");
        } else {
            System.out.println("Failed to delete card.");
        }
    }

    private static void addDuckToCard() {
        System.out.print("Enter duck ID: ");
        long duckId = scanner.nextLong();
        System.out.print("Enter card ID: ");
        long cardId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        appService.addDuckToCard(duckId, cardId);
        System.out.println("Duck added to card!");
    }

    private static void removeDuckFromCard() {
        System.out.print("Enter duck ID: ");
        long duckId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        appService.removeDuckFromCard(duckId);
        System.out.println("Duck removed from card!");
    }

    // Friendship Operations
    private static void addFriendship() {
        System.out.print("Enter first user ID: ");
        long idA = scanner.nextLong();
        System.out.print("Enter second user ID: ");
        long idB = scanner.nextLong();
        scanner.nextLine(); // consume newline

        appService.addFriendship(idA, idB);
        System.out.println("Friendship added!");
    }

    private static void removeFriendship() {
        System.out.print("Enter first user ID: ");
        long idA = scanner.nextLong();
        System.out.print("Enter second user ID: ");
        long idB = scanner.nextLong();
        scanner.nextLine(); // consume newline

        appService.removeFriendship(idA, idB);
        System.out.println("Friendship removed!");
    }

    private static void viewAllFriendships() {
        List<long[]> friendships = appService.getFriendshipService().getAllFriendships();
        System.out.println("All Friendships:");
        for (long[] friendship : friendships) {
            System.out.println("User " + friendship[0] + " <-> User " + friendship[1]);
        }
    }

    private static void removeAllFriendshipsForUser() {
        System.out.print("Enter user ID: ");
        long userId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        int deleted = appService.getFriendshipService().removeAllForUser(userId);
        System.out.println("Deleted " + deleted + " friendships for user " + userId);
    }

    // Race Event Operations
    private static void createRaceEvent() {
        System.out.print("Enter event title: ");
        String title = scanner.nextLine();
        System.out.print("Enter event description: ");
        String description = scanner.nextLine();
        System.out.print("Enter creator ID: ");
        long creatorId = scanner.nextLong();
        System.out.print("Enter number of balizes: ");
        int numBalizes = scanner.nextInt();
        scanner.nextLine(); // consume newline

        List<Double> balizes = new ArrayList<>();
        for (int i = 0; i < numBalizes; i++) {
            System.out.print("Enter balize " + (i+1) + ": ");
            double balize = scanner.nextDouble();
            balizes.add(balize);
        }
        scanner.nextLine(); // consume newline

        Event event = appService.createRaceEvent(title, description, creatorId, balizes);
        System.out.println("Race event created with ID: " + event.getId());
    }

    private static void viewRaceEventById() {
        System.out.print("Enter event ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // consume newline

        Optional<com.org.example.model.RaceEvent> event = appService.getRaceEventService().findById(id);
        if (event.isPresent()) {
            System.out.println(event.get());
        } else {
            System.out.println("Event not found!");
        }
    }

    private static void viewAllRaceEvents() {
        List<com.org.example.model.RaceEvent> events = appService.getRaceEventService().findAll();
        System.out.println("All Race Events:");
        if (events.isEmpty()) {
            System.out.println("No race events found.");
        } else {
            for (com.org.example.model.RaceEvent event : events) {
                System.out.println(event);
                System.out.println("  Participants: " + event.getParticipants().size());
                System.out.println("  Balizes: " + event.getBalize().size() + " (" + String.join(", ", event.getBalize().stream().map(b -> b.toString()).toArray(String[]::new)) + ")");
            }
        }
    }

    private static void subscribeUserToEvent() {
        System.out.print("Enter event ID: ");
        long eventId = scanner.nextLong();
        System.out.print("Enter user ID: ");
        long userId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        appService.subscribeUserToEvent(eventId, userId);
        System.out.println("User subscribed to event!");
    }

    private static void unsubscribeUserFromEvent() {
        System.out.print("Enter event ID: ");
        long eventId = scanner.nextLong();
        System.out.print("Enter user ID: ");
        long userId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        appService.unsubscribeUserFromEvent(eventId, userId);
        System.out.println("User unsubscribed from event!");
    }

    private static void addParticipantToEvent() {
        System.out.print("Enter event ID: ");
        long eventId = scanner.nextLong();
        System.out.print("Enter duck ID: ");
        long duckId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        appService.getRaceEventService().addParticipant(eventId, duckId);
        System.out.println("Duck added as participant to event!");
    }

    private static void removeParticipantFromEvent() {
        System.out.print("Enter event ID: ");
        long eventId = scanner.nextLong();
        System.out.print("Enter duck ID: ");
        long duckId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        appService.getRaceEventService().removeParticipant(eventId, duckId);
        System.out.println("Duck removed from event participants!");
    }

    // Run Race Events
    private static void runRaceEvents() {
        System.out.println("Running Race Events...");
        List<com.org.example.model.RaceEvent> events = appService.getRaceEventService().findAll();
        if (events.isEmpty()) {
            System.out.println("No race events available!");
            return;
        }

        System.out.println("Available race events:");
        for (int i = 0; i < events.size(); i++) {
            System.out.println((i + 1) + ". " + events.get(i).getTitle());
        }

        System.out.print("Select event to run (enter number): ");
        int selection = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (selection > 0 && selection <= events.size()) {
            com.org.example.model.RaceEvent selectedEvent = events.get(selection - 1);
            System.out.println("Simulating race event: " + selectedEvent.getTitle());

            com.org.example.model.RaceEvent.RaceResult result = appService.getRaceEventService().simulate(selectedEvent.getId());

            System.out.println("Race Results:");
            System.out.println("Overall time: " + result.overallTime + " seconds");
            System.out.println("Individual times:");
            for (String line : result.lines) {
                System.out.println("  " + line);
            }
        } else {
            System.out.println("Invalid selection!");
        }
    }

    // Community Analysis
    private static void getNumarComunitati() {
        int numCommunities = appService.getNumarComunitati();
        System.out.println("Number of communities: " + numCommunities);
    }

    private static void getCeaMaiSociabilaComunitate() {
        List<com.org.example.users.User> community = appService.getCeaMaiSociabilaComunitate();
        System.out.println("Most sociable community has " + community.size() + " users:");
        for (com.org.example.users.User user : community) {
            System.out.println("  " + user.getUsername());
        }
    }
}