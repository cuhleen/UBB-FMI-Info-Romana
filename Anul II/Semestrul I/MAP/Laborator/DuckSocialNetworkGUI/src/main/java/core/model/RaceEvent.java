package core.model;

import java.util.*;
import java.util.stream.Collectors;

public class RaceEvent extends core.model.Event {
    private final List<Double> balize;
    private final List<Duck> participants = new ArrayList<>();

    public RaceEvent(long id, String title, String description, User creator, List<Double> balize) {
        super(id, title, description, creator);
        this.balize = new ArrayList<>(balize);
    }

    // Constructor for loading from database (with empty balize list to be populated later)
    public RaceEvent(long id, String title, String description, User creator) {
        super(id, title, description, creator);
        this.balize = new ArrayList<>();
    }

    public List<Double> getBalize() { return Collections.unmodifiableList(balize); }
    public List<Duck> getParticipants() { return Collections.unmodifiableList(participants); }

    // Method to add balizes during database loading
    public void addBalize(double distance) {
        this.balize.add(distance);
    }

    /**
     * Selectează M rațe pentru cursă dintr-o listă dată.
     * Algoritm:
     *  - Filtrează doar rațele de tip SWIMMING sau FLYING_AND_SWIMMING
     *  - Sortează descrescător după rezistență (r) — astfel ordinea r1 >= r2 >= ...
     *  - Ia primele M. Dacă nu găsești M, aruncă excepție.
     */

    public void autoSelectParticipants(List<Duck> available, int M) {
        List<Duck> swimmers = available.stream()
                .filter(d -> d.getTip() == TipRata.SWIMMING || d.getTip() == TipRata.FLYING_AND_SWIMMING)
                .collect(Collectors.toList());
        swimmers.sort(Comparator.comparingDouble(Duck::getRezistenta).reversed());
        if (swimmers.size() < M) throw new IllegalArgumentException("Nu sunt suficiente rațe înotătoare disponibile.");
        participants.clear();
        participants.addAll(swimmers.subList(0, M));
        log("Auto-selected participants: " + participants.stream().map(Duck::getUsername).collect(Collectors.joining(", ")));
    }

    /**
     * Calculează timpii și afișează rezultatul.
     * - pentru fiecare participant i: t_i = max_j (2 * d_j / v_i)
     * - îl asociază unei benzi de la 1 la M
     */

    public RaceResult simulateRace() {
        if (participants.isEmpty()) throw new IllegalStateException("No participants selected");
        int M = participants.size();
        Map<Duck, Double> times = new LinkedHashMap<>();
        for (Duck d : participants) {
            double ti = balize.stream().mapToDouble(dj -> (2.0 * dj) / d.getViteza()).max().orElse(0.0);
            times.put(d, ti);
        }
        // overall time is max
        double overall = times.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0);

        // produce textual result lines
        List<String> lines = new ArrayList<>();
        int lane = 1;
        for (Map.Entry<Duck, Double> e : times.entrySet()) {
            lines.add(String.format("Duck %s on lane %d: t = %.3f s", e.getKey().getUsername(), lane++, e.getValue()));
        }

        notifySubscribers("Race " + title + " simulated; overall time = " + overall + " s");
        log("Race simulated; overall = " + overall);
        return new RaceResult(overall, times, lines);
    }

    @Override
    public void startEvent() {
        log("RaceEvent started");
        notifySubscribers("RaceEvent " + title + " is starting!");
    }

    // small DTO
    public static class RaceResult {
        public final double overallTime;
        public final Map<Duck, Double> times;
        public final List<String> lines;
        public RaceResult(double overallTime, Map<Duck, Double> times, List<String> lines) {
            this.overallTime = overallTime; this.times = times; this.lines = lines;
        }
    }

    public void addParticipant(Duck duck) {
        this.participants.add(duck);
    }
}

