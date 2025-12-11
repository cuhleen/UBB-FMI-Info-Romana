package com.org.example.factories;

import com.org.example.id_generators.IdGenerators;
import com.org.example.model.Event;
import com.org.example.model.RaceEvent;
import com.org.example.users.User;

import java.util.List;

public class EventFactory {
    public static Event createGenericEvent(String title, String description, User creator) {
        return new SimpleEvent(IdGenerators.nextEventId(), title, description, creator);
    }
    public static RaceEvent createRaceEvent(String title, String description, User creator, List<Double> balize) {
        return new RaceEvent(IdGenerators.nextEventId(), title, description, creator, balize);
    }

    // SimpleEvent

    private static class SimpleEvent extends Event {
        public SimpleEvent(long id, String title, String description, User creator) {
            super(id, title, description, creator);
        }
        @Override public void startEvent() {
            log("SimpleEvent started");
            notifySubscribers("Event " + title + " has started!");
        }
    }
}
