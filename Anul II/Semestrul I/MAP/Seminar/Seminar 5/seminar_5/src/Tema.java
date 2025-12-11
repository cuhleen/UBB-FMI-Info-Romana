public class Tema extends Entity<String>{

    private final String description;

    public Tema(String s, String description) {
        super(s);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Tema{" +
                "description='" + description + '\'' +
                '}';
    }
}
