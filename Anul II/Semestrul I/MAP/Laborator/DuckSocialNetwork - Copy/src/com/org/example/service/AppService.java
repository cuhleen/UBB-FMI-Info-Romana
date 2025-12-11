package com.org.example.service;

import com.org.example.model.Event;
import com.org.example.users.User;

import java.time.Instant;
import java.util.*;

/**
 * Orchestrator central — folosește servicii specializate pentru
 * rațe, persoane, cârduri, prietenii și evenimente.
 */
public class AppService {

    private final DuckService duckService;
    private final PersonService personService;
    private final CardService cardService;
    private final FriendshipService friendshipService;
    private final RaceEventService raceEventService;

    // Cache local
    private final Map<Long, User> usersById = new HashMap<>();
    private final Map<String, User> usersByUsername = new HashMap<>();
    private final Map<Long, Event> events = new HashMap<>();

    // Log global
    private final List<String> activityLog = new ArrayList<>();


    public AppService(
            DuckService duckService,
            PersonService personService,
            CardService cardService,
            FriendshipService friendshipService,
            RaceEventService raceEventService
    ) {
        this.duckService = duckService;
        this.personService = personService;
        this.cardService = cardService;
        this.friendshipService = friendshipService;
        this.raceEventService = raceEventService;

        loadInitialData();
    }

    // ------------------------------------------------------------
    // LOADING INITIAL DATA
    // ------------------------------------------------------------

    private void loadInitialData() {
        log("Loading initial users & events...");

        personService.findAll().forEach(this::cacheUser);
        duckService.findAll().forEach(this::cacheUser);
        raceEventService.findAll().forEach(this::cacheEvent);
    }

    private void cacheUser(User u) {
        usersById.put(u.getId(), u);
        usersByUsername.put(u.getUsername(), u);
    }

    private void cacheEvent(Event e) {
        events.put(e.getId(), e);
    }

    // ------------------------------------------------------------
    // USER MANAGEMENT
    // ------------------------------------------------------------

    public Optional<User> findUser(long id) {
        return Optional.ofNullable(usersById.get(id));
    }

    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }

    public Collection<User> getAllUsers() {
        return usersById.values();
    }

    /**
     * Șterge un user complet:
     *  - prietenii
     *  - card (dacă e rață)
     *  - abonări la event-uri
     *  - entitatea din DB
     */
    public void deleteUser(long id) {
        User u = usersById.get(id);
        if (u == null) return;

        // 1. prietenii
        friendshipService.deleteFriendshipsForUser(id);

        // 2. event-uri: unsubscribe
        raceEventService.removeUserFromAllEvents(id);

        // 3. daca e rata — scoate-o din cârd
        duckService.removeDuckFromCardIfExists(id);

        // 4. șterge efectiv
        if (duckService.isDuck(id)) {
            duckService.deleteDuck(id);
        } else {
            personService.deletePerson(id);
        }

        usersByUsername.remove(u.getUsername());
        usersById.remove(id);

        log("User " + id + " deleted completely.");
    }

    // ------------------------------------------------------------
    // FRIENDSHIP OPERATIONS (delegate)
    // ------------------------------------------------------------

    public void addFriendship(long idA, long idB) {
        friendshipService.addFriendship(idA, idB);
        log("Friendship added: " + idA + " <-> " + idB);
    }

    public void removeFriendship(long idA, long idB) {
        boolean removed = friendshipService.removeFriendship(idA, idB);
        if (removed) {
            log("Friendship removed: " + idA + " X " + idB);
        } else {
            log("Friendship not found: " + idA + " X " + idB);
        }
    }

    // ------------------------------------------------------------
    // CARD OPERATIONS (delegate)
    // ------------------------------------------------------------

    public void addDuckToCard(long duckId, long cardId) {
        cardService.addDuckToCard(duckId, cardId);
        log("Duck " + duckId + " added to card " + cardId);
    }

    public void removeDuckFromCard(long duckId) {
        cardService.removeDuckFromCard(duckId);
        log("Duck " + duckId + " removed from card.");
    }

    // ------------------------------------------------------------
    // EVENT OPERATIONS (delegate)
    // ------------------------------------------------------------

    public Event createRaceEvent(String title, String desc, long creatorId, List<Double> balize) {
        Event e = raceEventService.createRaceEvent(title, desc, creatorId, balize);
        cacheEvent(e);
        log("RaceEvent created: " + e.getId());
        return e;
    }

    public void subscribeUserToEvent(long eventId, long userId) {
        raceEventService.subscribe(eventId, userId);
        log("User " + userId + " subscribed to event " + eventId);
    }

    public void unsubscribeUserFromEvent(long eventId, long userId) {
        raceEventService.unsubscribe(eventId, userId);
        log("User " + userId + " unsubscribed from event " + eventId);
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events.values());
    }

    // ------------------------------------------------------------
    // COMMUNITY ANALYSIS (same logic from your old Service)
    // ------------------------------------------------------------

    public int getNumarComunitati() {
        Set<User> vizitat = new HashSet<>();
        int count = 0;
        for (User u : usersById.values()) {
            if (!vizitat.contains(u)) {
                exploreComponent(u, vizitat);
                count++;
            }
        }
        log("Număr comunități: " + count);
        return count;
    }

    private void exploreComponent(User start, Set<User> vizitat) {
        Queue<User> q = new LinkedList<>();
        q.add(start);
        vizitat.add(start);

        while (!q.isEmpty()) {
            User cur = q.poll();
            for (User friend : cur.getFriends()) {
                if (vizitat.add(friend)) q.add(friend);
            }
        }
    }

    public List<User> getCeaMaiSociabilaComunitate() {
        Set<User> vizitat = new HashSet<>();
        List<User> bestComponent = new ArrayList<>();
        int bestDiametru = -1;

        for (User u : usersById.values()) {
            if (!vizitat.contains(u)) {
                List<User> component = getComponentMembers(u, vizitat);
                int diam = calculeazaDiametru(component);
                if (diam > bestDiametru) {
                    bestDiametru = diam;
                    bestComponent = component;
                }
            }
        }
        log("Cea mai sociabilă comunitate are diametrul: " + bestDiametru);
        return bestComponent;
    }

    private List<User> getComponentMembers(User start, Set<User> vizitat) {
        List<User> comp = new ArrayList<>();
        Queue<User> q = new LinkedList<>();
        q.add(start);
        vizitat.add(start);

        while (!q.isEmpty()) {
            User cur = q.poll();
            comp.add(cur);
            for (User f : cur.getFriends()) {
                if (vizitat.add(f)) q.add(f);
            }
        }
        return comp;
    }

    private int calculeazaDiametru(List<User> comp) {
        int max = 0;
        for (User start : comp) {
            int dist = dfsMaxDist(start, new HashSet<>(), 0);
            max = Math.max(max, dist);
        }
        return max;
    }

    private int dfsMaxDist(User cur, Set<User> viz, int depth) {
        viz.add(cur);
        int max = depth;
        for (User f : cur.getFriends()) {
            if (!viz.contains(f)) {
                max = Math.max(max, dfsMaxDist(f, viz, depth + 1));
            }
        }
        return max;
    }

    // ------------------------------------------------------------
    // LOGGING
    // ------------------------------------------------------------

    private void log(String line) {
        String s = Instant.now() + " - " + line;
        activityLog.add(s);
        System.out.println("[AppService Log] " + s);
    }

    public List<String> getActivityLog() {
        return Collections.unmodifiableList(activityLog);
    }

    // Getters for services
    public DuckService getDuckService() {
        return duckService;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public CardService getCardService() {
        return cardService;
    }

    public FriendshipService getFriendshipService() {
        return friendshipService;
    }

    public RaceEventService getRaceEventService() {
        return raceEventService;
    }
}
