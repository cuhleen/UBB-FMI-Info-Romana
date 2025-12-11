public class Nota {
    private Student student;
    private Tema tema;
    private double value;
    private String profesor;

    public Nota(Student student, Tema tema, double value, String profesor) {
        this.student = student;
        this.tema = tema;
        this.value = value;
        this.profesor = profesor;
    }

    public Student getStudent() {
        return student;
    }

    public Tema getTema() {
        return tema;
    }

    public double getValue() {
        return value;
    }

    public String getProfesor() {
        return profesor;
    }

    @Override
    public String toString() {
        return "Note{" +
                "student=" + student +
                ", tema=" + tema +
                ", value=" + value +
                ", profesor='" + profesor + '\'' +
                '}';
    }
}
