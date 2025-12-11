import domain.Student;

import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student("ana", 10f);
        Student student2 = new Student("bana", 10f);
        Student student3 = new Student("lana", 10f);

        //Set<Student> students = new HashSet<>();
        //Set<Student> students = new TreeSet<>();
        Set<Student> students = new TreeSet<>(
                (s1, s2) -> Float.compare(s1.getGrade(), s2.getGrade())
        );
        students.add(student1);
        students.add(student2);
        students.add(student3);

        for(Student student : students){
            System.out.println(student);
        }
    }
}