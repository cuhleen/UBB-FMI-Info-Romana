package core.service;

import core.model.*;
import core.repository.RepoDuckDB;
import core.repository.RepoEventDB;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RaceEventService {

    private final RepoEventDB repoEvent;
    private final RepoDuckDB repoDuck;
    private final PersonService personService;
    private final MessageService messageService;
    private final NotificationService notificationService;

    // cache in-memory
    private final Map<Long, RaceEvent> events = new HashMap<>();

    // Executor pentru curse asincrone
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public RaceEventService(RepoEventDB repoEvent,
                            RepoDuckDB repoDuck,
                            PersonService personService,
                            MessageService messageService,
                            NotificationService notificationService) {
        this.repoEvent = repoEvent;
        this.repoDuck = repoDuck;
        this.personService = personService;
        this.messageService = messageService;
        this.notificationService = notificationService;

        loadEvents();
    }

    // ------------------------------------------
    // LOAD FROM DB
    // ------------------------------------------
    private void loadEvents() {
        List<RaceEvent> list = repoEvent.findAllRaceEvents();
        for (RaceEvent e : list) {
            events.put(e.getId(), e);

            // clear subscribers first
            e.clearSubscribers();

            List<Long> subIds = repoEvent.findSubscribersForEvent(e.getId());
            for (Long subId : subIds) {
                personService.getPerson(subId).ifPresent(e::subscribe);
            }

            // restore participants
            List<Duck> parts = repoEvent.findParticipantsForEvent(e.getId());
            for (Duck d : parts) {
                e.addParticipant(d);
            }
        }
    }


    // ------------------------------------------
    // CRUD
    // ------------------------------------------
    public RaceEvent createRaceEvent(String title, String desc, long creatorId, List<Double> balize) {
        long id = repoEvent.generateId();
        User creator = personService.getPerson(creatorId)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        RaceEvent event = new RaceEvent(id, title, desc, creator, balize);
        repoEvent.saveRaceEvent(event);
        events.put(id, event);
        return event;
    }

    public Optional<RaceEvent> findById(long id) {
        return Optional.ofNullable(events.get(id));
    }

    public List<RaceEvent> findAll() {
        return new ArrayList<>(events.values());
    }

    public void update(RaceEvent event) {
        repoEvent.updateRaceEvent(event);
    }

    public void delete(long id) {
        repoEvent.deleteEvent(id);
        events.remove(id);
    }

    // ------------------------------------------
    // SUBSCRIBE / UNSUBSCRIBE
    // ------------------------------------------
    public void subscribe(long eventId, User user) {
        RaceEvent e = events.get(eventId);
        if (e == null) return;

        if (!e.getSubscribers().contains(user)) {
            e.subscribe(user);
            repoEvent.addSubscriber(eventId, user.getId());
        } else {
            System.out.println("User " + user.getUsername() + " already subscribed!");
        }
    }

    public void unsubscribe(long eventId, User user) {
        RaceEvent e = events.get(eventId);
        if (e == null) return;

        e.unsubscribe(user);
        repoEvent.removeSubscriber(eventId, user.getId());
    }

    /**
     * Folosit când un user este șters
     */
    public void removeUserFromAllEvents(long userId) {
        for (RaceEvent e : events.values()) {
            e.getSubscribers().stream()
                    .filter(u -> u.getId() == userId)
                    .findFirst()
                    .ifPresent(u -> unsubscribe(e.getId(), u));
        }
    }

    // ------------------------------------------
    // PARTICIPANTS
    // ------------------------------------------
    public void addParticipant(long eventId, long duckId) {
        RaceEvent e = events.get(eventId);
        if (e == null) return;

        Optional<Duck> duckOpt = repoDuck.findById(duckId);
        if (duckOpt.isEmpty()) return;

        Duck d = duckOpt.get();
        repoEvent.addParticipant(eventId, duckId);
        e.getParticipants().add(d);
    }

    public void removeParticipant(long eventId, long duckId) {
        RaceEvent e = events.get(eventId);
        if (e == null) return;

        repoEvent.removeParticipant(eventId, duckId);
        e.getParticipants().removeIf(d -> d.getId() == duckId);
    }

    // ------------------------------------------
    // START RACE ASINCRON
    // ------------------------------------------
    public CompletableFuture<RaceEvent.RaceResult> startRaceAsync(long eventId) {
        RaceEvent e = events.get(eventId);
        if (e == null) throw new RuntimeException("Eventul nu există");

        return CompletableFuture.supplyAsync(() -> runRaceAndNotify(e), executor);
    }

    private RaceEvent.RaceResult runRaceAndNotify(RaceEvent e) {
        System.out.println("=== Running race for event: " + e.getTitle() + " ===");
        System.out.println("Subscribers BEFORE notification:");
        for (User u : e.getSubscribers()) {
            System.out.println(" - " + u.getUsername() + " (id=" + u.getId() + ")");
        }

        RaceEvent.RaceResult result = e.simulateRace();

        for (User u : new ArrayList<>(e.getSubscribers())) {
            System.out.println("Notifying user: " + u.getUsername() + " (id=" + u.getId() + ")");
            notificationService.notifyUser(u.getId(), "[RaceEvent " + e.getTitle() + "] Rezultatul cursei:\n" +
                    String.join("\n", result.lines));

            System.out.println("Unsubscribing user: " + u.getUsername());
            unsubscribe(e.getId(), u);
        }

        System.out.println("Subscribers AFTER notification:");
        for (User u : e.getSubscribers()) {
            System.out.println(" - " + u.getUsername() + " (id=" + u.getId() + ")");
        }

        return result;
    }


    // ------------------------------------------
    // SIMULARE SINCRONĂ
    // ------------------------------------------
    public RaceEvent.RaceResult simulate(long eventId) {
        RaceEvent e = events.get(eventId);
        if (e == null) throw new RuntimeException("Eventul nu există");
        return e.simulateRace();
    }

    // ------------------------------------------
    // CLEANUP
    // ------------------------------------------
    public void shutdown() {
        executor.shutdown();
    }
}
