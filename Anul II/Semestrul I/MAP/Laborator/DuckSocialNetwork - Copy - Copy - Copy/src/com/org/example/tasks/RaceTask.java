package com.org.example.tasks;

import com.org.example.model.RaceEvent;
import com.org.example.users.Duck;

import java.util.List;

public class RaceTask implements Runnable {
    private final RaceEvent event;
    private final List<Duck> availableDucks;
    private final int M;

    public RaceTask(RaceEvent event, List<Duck> availableDucks, int M) {
        this.event = event; this.availableDucks = availableDucks; this.M = M;
    }

    @Override
    public void run() {
        event.log("RaceTask running");
        try {
            event.autoSelectParticipants(availableDucks, M);
            RaceEvent.RaceResult result = event.simulateRace();
            for (String line : result.lines) {
                event.log(line);
                System.out.println(line);
            }
            System.out.println("Overall time: " + result.overallTime + " s");
        } catch (Exception ex) {
            event.log("RaceTask failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
