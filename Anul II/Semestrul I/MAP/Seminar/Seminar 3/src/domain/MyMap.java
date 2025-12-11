package domain;

import java.util.*;

public class MyMap {
    private final Map<Integer, List<Student>> studentsByGrade;

    public MyMap() {
        this.studentsByGrade = new TreeMap<>(new StudentGradeComparator());
    }

    private static class StudentGradeComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2-o1; //schimbam ordinea ca sa ordonam descresc
        }
    }

    public void addStudent(Student student) {
        int key = Math.round(student.getGrade());
        var students = studentsByGrade.get(key);

        if(students == null) {
            students = new ArrayList<>();
            studentsByGrade.put(key, students);
        }
        students.add(student);
    }

    //public <Map.Entry<Integer, List<Student>>> getEntries() {

    //}

}
