package domain;

import java.util.Objects;

public class Student extends Entity<Integer> implements Comparable<Student> {

    private String name;
    private float grade;

    public Student(String name, Float grade) {
        super(null);
        this.name = name;
        this.grade = grade;
    }

    public Student(Integer id, String nume, Float media) {
        super(id);
        this.name = nume;
        this.grade = media;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Float.compare(getGrade(), student.getGrade()) == 0 && Objects.equals(getName(), student.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGrade());
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        return this.name.compareTo(o.name);
    }
}
