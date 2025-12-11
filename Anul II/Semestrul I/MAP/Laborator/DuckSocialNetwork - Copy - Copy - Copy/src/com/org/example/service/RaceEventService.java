package com.org.example.service;

import com.org.example.model.Event;
import com.org.example.model.RaceEvent;
import com.org.example.repo.RepoEventDB;
import com.org.example.repo.RepoDuckDB;
import com.org.example.users.Duck;
import com.org.example.users.User;

import java.util.*;

public class RaceEventService {

    private final RepoEventDB repoEvent;
    private final RepoDuckDB repoDuck;
    private final PersonService personService;
    // cache in-memory
    private final Map<Long, RaceEvent> events = new HashMap<>();

    public RaceEventService(RepoEventDB repoEvent, RepoDuckDB repoDuck, PersonService personService) {
        this.repoEvent = repoEvent;
        this.repoDuck = repoDuck;
        this.personService = personService;

        loadEvents();
    }

    // ------------------------------------------------------
    // INITIAL LOAD
    // ------------------------------------------------------
    private void loadEvents() {
        List<RaceEvent> list = repoEvent.findAllRaceEvents();
        for (RaceEvent e : list) {
            events.put(e.getId(), e);

            // restore subscribers
            List<Long> subs = repoEvent.findSubscribersForEvent(e.getId());
            subs.forEach(subId -> {
                // în mod normal trebuie repoUser, dar nu există,
                // așa că va fi recuperat mai târziu de AppService
                // care conectează userii între ei
            });

            // restore participants
            List<Duck> parts = repoEvent.findParticipantsForEvent(e.getId());
            for (Duck duck : parts) {
                e.addParticipant(duck);
            }
        }
    }

    // ------------------------------------------------------
    // CRUD
    // ------------------------------------------------------

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

    // ------------------------------------------------------
    // SUBSCRIBE
    // ------------------------------------------------------

    public void subscribe(long eventId, long userId) {
        RaceEvent e = events.get(eventId);
        if (e == null) return;

        repoEvent.addSubscriber(eventId, userId);
        // user-ul efectiv va fi injectat de AppService după ce este cunoscut
    }

    public void unsubscribe(long eventId, long userId) {
        RaceEvent e = events.get(eventId);
        if (e == null) return;

        repoEvent.removeSubscriber(eventId, userId);
    }

    /**
     * Folosit de AppService când se șterge un user.
     */
    public void removeUserFromAllEvents(long userId) {
        for (RaceEvent e : events.values()) {
            repoEvent.removeSubscriber(e.getId(), userId);
        }
    }

    // ------------------------------------------------------
    // PARTICIPANTS
    // ------------------------------------------------------

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

    // ------------------------------------------------------
    // RUN RACE (delegat din RaceTask)
    // ------------------------------------------------------

    public RaceEvent.RaceResult simulate(long eventId) {
        RaceEvent e = events.get(eventId);
        if (e == null) throw new RuntimeException("Eventul nu există");

        return e.simulateRace();
    }
}
