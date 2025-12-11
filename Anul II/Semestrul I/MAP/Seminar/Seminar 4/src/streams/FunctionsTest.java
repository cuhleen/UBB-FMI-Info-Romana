package streams;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FunctionsTest {
    public static void main(String[] args) {

        Supplier<List<>> arrayListSupplier = () -> new ArrayList<>();
        Supplier<List<String>> stringArraySupplier = ArrayList::new;

        List<Integer> intList = arrayListSupplier.get();

        System.out.println(arrayListSupplier.get());
        System.out.println(stringArraySupplier.get());

    }
}
