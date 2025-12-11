import java.util.*;
import java.util.stream.Collectors;

public class Sem6App {
    private static void report1(List<Nota> note, String name){
        Map<Student, List<Nota>> studentsGrades = note.stream()
                .collect(Collectors.groupingBy(Nota::getStudent));

        studentsGrades.entrySet().stream()
                .filter(e -> e.getKey().getNume().contains(name))
                .sorted((e1, e2) -> {
                    double avg1 = getAverage(e1.getValue());
                    double avg2 = getAverage(e2.getValue());
                    return Double.compare(avg1, avg2);
                })
                .forEach(e -> System.out.println(e.getKey().getNume() + " Media " +  getAverage(e.getValue())));
    }

    private static void report2(List<Nota> note, String name){
        Map<String, Double> profesorAvg = note.stream()
                .filter(nota -> nota.getProfesor().contains(name))
                .collect(Collectors.groupingBy(Nota::getProfesor, Collectors.averagingDouble(Nota::getValue)))
                .entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, _) -> o1, LinkedHashMap::new));
        System.out.println(profesorAvg);
    }

    private static void report3(List<Nota> note, int group){
        note.stream()
                .filter(n -> n.getStudent().getGroup() == group)
                .collect(Collectors.groupingBy(Nota::getStudent, Collectors.counting()))
                .entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(e -> System.out.println(e.getKey().getNume() + " numar note " + e.getValue()));
    }

    private static void report4(List<Nota> note, int startingGroup){
        note.stream()
                .collect(Collectors.groupingBy(n -> n.getStudent().getGroup(), Collectors.averagingDouble(Nota::getValue)))
                .entrySet().stream()
                .filter(e -> Integer.toString(e.getKey()).startsWith(Integer.toString(startingGroup)))
                .sorted(Map.Entry.<Integer,Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey() + " media notelor " + e.getValue()));
    }

    private static void report5(List<Nota> note){
        note.stream()
                .collect(Collectors.groupingBy(n -> n.getStudent().getGroup()))
                .entrySet().stream()
                .map( e -> {
                    var grupa = e.getKey();
                    var notes = e.getValue();

                    var avgGrades = notes.stream()
                            .mapToDouble(Nota::getValue)
                            .average()
                            .orElse(0);
                    var distinctStudents = notes.stream()
                            .map(n -> n.getStudent().getNume())
                            .distinct()
                            .count();
                    double average = avgGrades == 0 ? 0 : avgGrades / distinctStudents;
                    return new AbstractMap.SimpleEntry<grupa, average>;
                })
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())) // tspmo
                .forEach(e -> System.out.println(e.getKey() + " Raport medie/student: " + ))
    }

    private static double getAverage(List<Nota> note){
        double sum = note.stream().map(Nota::getValue).reduce(0.0, Double::sum);
        return sum / note.size();
    }

    public static void main(String[] args) {
        var notes = getNote(getTeme(), getStudents());
//        report1(notes, "");
//        report2(notes, "");
//        report3(notes, 221);
//        report3(notes, 222);
        report4(notes, 22);
    }

    private static List<Student> getStudents() {
        return List.of(
                new Student(1, "Ana", 221),
                new Student(2, "Maria", 221),
                new Student(3, "Alin", 221),
                new Student(4, "Marius", 222),
                new Student(5, "George", 222)
        );
    }

    private static List<Tema> getTeme(){
        return List.of(
                new Tema("1", "descriere 1"),
                new Tema("2", "descriere 2"),
                new Tema("3", "descriere 3"),
                new Tema("4", "descriere 4"),
                new Tema("5", "descriere 5")
        );
    }

    private static List<Nota> getNote(List<Tema> teme, List<Student> students){
        return List.of(
                new Nota(students.get(0), teme.get(0), 10.0, "Prof1"),
                new Nota(students.get(0), teme.get(1), 8.2, "Prof1"),
                new Nota(students.get(1), teme.get(0), 7.0, "Prof2"),
                new Nota(students.get(1), teme.get(1), 7.5, "Prof1"),
                new Nota(students.get(1), teme.get(2), 10.0, "Prof1"),
                new Nota(students.get(2), teme.get(0), 9, "Prof2"),
                new Nota(students.get(2), teme.get(2), 9.7, "Prof2"),
                new Nota(students.get(3), teme.get(2), 8.5, "Prof2"),
                new Nota(students.get(0), teme.get(2), 6.7, "Prof1"),
                new Nota(students.get(2), teme.get(2), 5.6, "Prof1")
        );
    }
}