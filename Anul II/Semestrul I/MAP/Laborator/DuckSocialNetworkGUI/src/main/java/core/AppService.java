package core;

import core.model.Event;
import core.model.User;
import core.model.UserAuthData;
import core.repository.RepoUsersDB;
import core.service.*;

import java.time.Instant;
import java.util.*;

public class AppService {

    private final DuckService duckService;
    private final PersonService personService;
    private final CardService cardService;
    private final FriendshipService friendshipService;
    private final RaceEventService raceEventService;
    private final RepoUsersDB repoUsers;

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
            RaceEventService raceEventService,
            RepoUsersDB repoUsers
    ) {
        this.duckService = duckService;
        this.personService = personService;
        this.cardService = cardService;
        this.friendshipService = friendshipService;
        this.raceEventService = raceEventService;
        this.repoUsers = repoUsers;

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

    public boolean checkLogin(String username, String hashedPass) {
        Optional<UserAuthData> data = repoUsers.findAuthDataByUsername(username);
        if (data.isEmpty())
            return false;

        return data.get().hashedPassword().equals(hashedPass);
    }

    public Optional<User> findUser(long id) {
        return Optional.ofNullable(usersById.get(id));
    }


    public Collection<User> getAllUsers() {
        return usersById.values();
    }

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

    // returnează toate username-urile, fără user-ul logat
    public List<String> getAllUsernamesExcept(long excludeId) {
        List<String> list = new ArrayList<>();
        for (User u : usersById.values()) {
            if (u.getId() != excludeId) {
                list.add(u.getUsername());
            }
        }
        return list;
    }

    // returnează id-ul unui user după username
    public long getUserIdByUsername(String username) {
        User u = usersByUsername.get(username);
        return u != null ? u.getId() : 0;
    }

    // returnează username după id
    public String getUsernameById(long id) {
        User u = usersById.get(id);
        return u != null ? u.getUsername() : "Unknown";
    }

    // ------------------------------------------------------------
    // FRIENDSHIP OPERATIONS
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
    // CARD OPERATIONS
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
    // EVENT OPERATIONS
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
    // COMMUNITY ANALYSIS
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

    private int calculeazaDiametru(List<User> comp) {
        if (comp.isEmpty()) return 0;
        int diam = 0;
        for (User u : comp) {
            BFSResult r = bfsFarthest(u, comp);
            diam = Math.max(diam, r.dist);
        }
        return diam;
    }

    private BFSResult bfsFarthest(User start, List<User> component) {
        Queue<User> q = new LinkedList<>();
        Map<User, Integer> dist = new HashMap<>();

        q.add(start);
        dist.put(start, 0);

        User farthest = start;
        int maxDist = 0;

        while (!q.isEmpty()) {
            User cur = q.poll();
            int d = dist.get(cur);

            if (d > maxDist) {
                maxDist = d;
                farthest = cur;
            }

            for (User f : cur.getFriends()) {
                if (component.contains(f) && !dist.containsKey(f)) {
                    dist.put(f, d + 1);
                    q.add(f);
                }
            }
        }

        return new BFSResult(farthest, maxDist);
    }

    private static class BFSResult {
        User user;
        int dist;
        BFSResult(User u, int d) { user = u; dist = d; }
    }

    private BFSResult bfsFarthest(User start) {
        Queue<User> q = new LinkedList<>();
        Map<User, Integer> dist = new HashMap<>();

        q.add(start);
        dist.put(start, 0);

        User farthest = start;
        int maxDist = 0;

        while (!q.isEmpty()) {
            User cur = q.poll();
            int d = dist.get(cur);

            if (d > maxDist) {
                maxDist = d;
                farthest = cur;
            }

            for (User f : cur.getFriends()) {
                if (!dist.containsKey(f)) {
                    dist.put(f, d + 1);
                    q.add(f);
                }
            }
        }

        return new BFSResult(farthest, maxDist);
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
