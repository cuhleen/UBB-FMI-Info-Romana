//package com.org.example;
//
//import com.org.example.repo.RepoPersonDB;
//import com.org.example.users.Person;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//public class TestPersonDB {
//    public static void main(String[] args) {
//        RepoPersonDB repo = new RepoPersonDB();
//
//        // 1️⃣ Inserare
//        Person p1 = repo.createPerson("user123", "user@mail.com", "parola123",
//                "Popescu", "Ana", LocalDate.of(2000, 5, 12),
//                "Programatoare", 9.3);
//        System.out.println("Persoană inserată cu ID: " + p1.getId());
//
//        // 2️⃣ Căutare după ID
//        Optional<Person> found = repo.findById(p1.getId());
//        found.ifPresentOrElse(
//                person -> System.out.println("Am găsit persoana: " + person.getNume() + " " + person.getPrenume()),
//                () -> System.out.println("Persoana nu există.")
//        );
//
//        // 3️⃣ Listare
//        List<Person> all = repo.listAll();
//        System.out.println("Toate persoanele:");
//        all.forEach(person ->
//                System.out.println(" - " + person.getNume() + " " + person.getPrenume() +
//                        " (" + person.getOcupatie() + "), empatie: " + person.getNivelEmpatie())
//        );
//    }
//}
