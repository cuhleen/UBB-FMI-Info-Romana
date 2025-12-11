//package com.org.example.example;
//
//import com.org.example.enums.TipRata;
//import com.org.example.repo.RepoDuckDB;
//import com.org.example.users.Duck;
//
//import java.util.List;
//import java.util.Optional;
//
//public class TestDB {
//    public static void main(String[] args) {
//        RepoDuckDB repo = new RepoDuckDB();
//
//        // 1️⃣ Creăm o rață nouă și o salvăm în DB
//        Duck d1 = repo.createDuck("rațaAlba", "rata@lac.com", "quack123",
//                TipRata.FLYING, 10.5, 8.2);
//        System.out.println("Rață inserată cu ID: " + d1.getId());
//
//        // 2️⃣ Căutăm rața după ID
//        Optional<Duck> found = repo.findById(d1.getId());
//        found.ifPresentOrElse(
//                duck -> System.out.println("Am găsit rața: " + duck.getUsername()),
//                () -> System.out.println("Rața nu există.")
//        );
//
//        // 3️⃣ Listăm toate rațele din baza de date
//        List<Duck> all = repo.listAll();
//        System.out.println("Toate rațele:");
//        all.forEach(duck -> System.out.println(" - " + duck.getUsername() + " (" + duck.getTip() + ")"));
//    }
//}
