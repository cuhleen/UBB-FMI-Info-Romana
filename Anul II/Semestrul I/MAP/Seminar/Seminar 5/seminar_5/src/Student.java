public class Student extends Entity<Integer>{

    private String nume;
    private int group;

    public Student(int id, String nume, int group) {
        super(id);
        this.nume = nume;
        this.group = group;
    }

    public String getNume() {
        return nume;
    }

    public int getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", group=" + group +
                '}';
    }
}
