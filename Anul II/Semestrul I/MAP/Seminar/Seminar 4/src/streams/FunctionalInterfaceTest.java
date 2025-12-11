package streams;

import java.util.List;
import streams.model.Area;
import streams.model.Circle;

public class FunctionalInterfaceTest {
    private static <E> void printArea(List<E> list, Area<E> function) {
        for (E e : list) {}
        System.out.println(function.compute(e));
    }

    public static void main(String[] args){
        Circle circle1 = new Circle(2);
        Circle circle2 = new Circle(7.5);
        var circles = List.of(circle1, circle2);
        Area<Circle> areaCircle = c -> Math.PI * Math.pow(c.getRadius(), 2);

        printArea(circles, areaCircle);
    }
}
