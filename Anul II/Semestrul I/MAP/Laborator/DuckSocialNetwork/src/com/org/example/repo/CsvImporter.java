package com.org.example.repo;

import com.org.example.enums.TipRata;
import com.org.example.users.Card;
import com.org.example.service.Service;
import com.org.example.users.Duck;
import com.org.example.users.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class CsvImporter {

    public static void importFromCsv(String filename,
                                     RepoPerson repoPerson,
                                     RepoDuck repoDuck,
                                     Service service) throws IOException {

        Map<String, Person> persons = new HashMap<>();
        Map<String, Duck> ducks = new HashMap<>();
        Map<Long, Card> cards = new HashMap<>();
        List<String[]> friendships = new ArrayList<>();
        List<String[]> cardMemberships = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String header = br.readLine(); // skip header dacă există
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",", -1);
                String type = data[0].trim().toUpperCase();

                switch (type) {

                    // === PERSON ===
                    case "PERSON" -> {
                        String username = safeGet(data, 1);
                        String email = safeGet(data, 2);
                        String password = safeGet(data, 3);
                        String lastName = safeGet(data, 4);
                        String firstName = safeGet(data, 5);
                        String birthStr = safeGet(data, 6);
                        String occupation = safeGet(data, 7);
                        String humorStr = safeGet(data, 8);

                        LocalDate birthDate = LocalDate.parse(birthStr.isEmpty() ? "2000-01-01" : birthStr);
                        double humorScore = parseDoubleSafe(humorStr, 0.5, "nivelEmpatie");

                        Person p = repoPerson.createPerson(
                                username, email, password, lastName, firstName,
                                birthDate, occupation, humorScore
                        );
                        persons.put(username, p);
                        service.addUser(p);
                    }

                    // === DUCK ===
                    case "DUCK" -> {
                        String username = safeGet(data, 1);
                        String email = safeGet(data, 2);
                        String password = safeGet(data, 3);
                        String tipString = safeGet(data, 4);
                        String speedStr = safeGet(data, 5);
                        String perfStr = safeGet(data, 6);

                        TipRata tip;
                        try {
                            tip = TipRata.valueOf(tipString.toUpperCase());
                        } catch (Exception e) {
                            System.err.println("[WARN] Tip necunoscut pentru rață: " + tipString + ", implicit FLYING");
                            tip = TipRata.FLYING;
                        }

                        double speed = parseDoubleSafe(speedStr, 1.0, "viteza");
                        double performance = parseDoubleSafe(perfStr, 1.0, "performanta");

                        Duck d = repoDuck.createDuck(username, email, password, tip, speed, performance);
                        ducks.put(username, d);
                        service.addUser(d);
                    }

                    // === CARD ===
                    case "CARD" -> {
                        String idStr = safeGet(data, 1);
                        String name = safeGet(data, 2);
                        if (idStr.isEmpty() || name.isEmpty()) continue;

                        try {
                            long id = Long.parseLong(idStr);
                            Card c = repoDuck.createCard(id, name);
                            cards.put(id, c);
                        } catch (NumberFormatException e) {
                            System.err.println("[WARN] ID card invalid: " + idStr);
                        }
                    }

                    // === FRIENDSHIP ===
                    case "FRIEND" -> friendships.add(data);

                    // === CARD MEMBERSHIP ===
                    case "CARD_MEMBER" -> cardMemberships.add(data);

                    default -> System.err.println("[WARN] Linie necunoscută în CSV: " + line);
                }
            }
        }

        // === Prietenii ===
        for (String[] f : friendships) {
            if (f.length < 3) continue;
            String user1 = safeGet(f, 1);
            String user2 = safeGet(f, 2);
            if (user1.isEmpty() || user2.isEmpty()) continue;

            boolean ok = service.addFriendship(user1, user2);
            if (!ok) System.err.println("[WARN] Nu s-a putut adăuga prietenia: " + user1 + " - " + user2);
        }

        // === Cârduri ===
        for (String[] c : cardMemberships) {
            if (c.length < 3) continue;
            String duckUsername = safeGet(c, 1);
            String cardIdStr = safeGet(c, 2);

            if (duckUsername.isEmpty() || cardIdStr.isEmpty()) continue;

            try {
                long cardId = Long.parseLong(cardIdStr);
                if (cards.containsKey(cardId) && ducks.containsKey(duckUsername)) {
                    cards.get(cardId).addMember(ducks.get(duckUsername));
                } else {
                    System.err.println("[WARN] Card sau rață inexistentă: " + duckUsername + " / " + cardId);
                }
            } catch (NumberFormatException e) {
                System.err.println("[WARN] ID invalid pentru card: " + cardIdStr);
            }
        }

        System.out.println("Import CSV complet: "
                + persons.size() + " persoane, "
                + ducks.size() + " rațe, "
                + cards.size() + " cărți, "
                + friendships.size() + " prietenii, "
                + cardMemberships.size() + " membri în cârduri");
    }

    // ------------------------------------------------------------

    private static String safeGet(String[] arr, int index) {
        return (index < arr.length) ? arr[index].trim() : "";
    }

    private static double parseDoubleSafe(String s, double fallback, String field) {
        if (s == null || s.trim().isEmpty()) {
            System.err.println("[WARN] Câmp numeric gol pentru " + field + ", implicit " + fallback);
            return fallback;
        }
        try {
            return Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            System.err.println("[WARN] Valoare invalidă pentru " + field + ": " + s + ", implicit " + fallback);
            return fallback;
        }
    }
}
