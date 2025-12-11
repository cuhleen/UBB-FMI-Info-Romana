package streams;

import streams.model.Square;

import java.util.List;
import java.util.function.Predicate;

public class PredicateTest {
    private static <E> void printList(List<E> list, Predicate<E> p){
        list.forEach(x -> {
            if(p.test(x)){
                System.out.println(x);
            }
        });
    }

    public static void main(String[] args) {
        Square square1 = new Square(5);
        Square square2 = new Square(8.7);

        var squares = List.of(square1, square2);
        printList(squares, s->s.getLength()<8);
        Predicate<Square> squarePredicate = s -> s.getLength()>8;
        printList(squares, squarePredicate);
    }
}
