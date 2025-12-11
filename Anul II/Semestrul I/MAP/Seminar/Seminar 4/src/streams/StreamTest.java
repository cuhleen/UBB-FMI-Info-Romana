package streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamTest {
    public static <E> List<E> filterGeneric(List<E> lista, Predicate<E> p){
        lista.stream()
                .filter(p)
                .toList();
    }

    public static <E> List<E> filterGeneric(List<E> lista, Predicate<E> p, Comparator<E> c){
        lista.stream()
                .filter(p)
                .sorted(c)
                .toList();
    }

    public static void main(String[] args) {
        List<Integer> lista = List.of(1, 2, 3, 4, 5);
        var evenNumbers = filterGeneric(lista, e -> e % 2 == 0);
        evenNumbers.forEach(System.out::println);
        var oddNumbersSorted = filterGeneric(lista, e -> e % 2 == 1, (i1, i2) -> i2 - i1);
        oddNumbersSorted.forEach(System.out::println);

        List<String> names = List.of("Ana", "George", "Denis", "Gabriela");
        List<String> namesUppercase = names.stream()
                .filter(n -> n.contains("a"))
                .map(String::toUpperCase)
                    .collect(Collectors.toList())
                .toList()
        // heeeeelp heelp meeeeee
        namesUppercase.add("Eu");
    }
}
