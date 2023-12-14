package a01b.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FlattenerFactoryImpl implements FlattenerFactory {

    public <I, O> Flattener<I, O> generic(Predicate<List<List<I>>> condition, Function<List<I>, O> action) {
        return new Flattener<I,O>() {
            @Override
            public List<O> flatten(List<List<I>> list) {
                List<List<I>> input = new ArrayList<>();
                List<I> buffer = new ArrayList<>();
                List<O> output = new ArrayList<>();

                for (List<I> sublist : list) {
                    input.add(sublist);
                    buffer.addAll(sublist);

                    if(condition.test(input)) {
                        output.add(action.apply(buffer));
                        buffer.clear();
                        input.clear();
                    }
                }

                if(!input.isEmpty()) {
                    output.add(action.apply(buffer));
                }

                return output;
            }
        };
    }

    @Override
    public Flattener<Integer, Integer> sumEach() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sumEach'");
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flattenAll'");
    }

    @Override
    public Flattener<String, String> concatPairs() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'concatPairs'");
    }

    @Override
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'each'");
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sumVectors'");
    }

    /*@Override
    public Flattener<Integer, Integer> sumEach() {
        return generic(sublist -> sublist.size() == 1, sublist -> sublist.stream().mapToInt(i -> i).sum());
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        return generic(sublist -> sublist.size() == 1, sublist -> sublist);
    }

    @Override
    public Flattener<String, String> concatPairs() {
        return generic(sublist -> sublist.size() == 2, sublist -> List.of(sublist.get(0).stream().reduce((str1, str2) -> str1.concat(str2)).get().concat(
                                                                          sublist.size() == 2
                                                                          ? sublist.get(1).stream().reduce((str1, str2) -> str1.concat(str2)).get()
                                                                          : "")));
    }

    @Override
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        return generic(sublist -> sublist.size() == 1, sublist -> List.of(mapper.apply(sublist.get(0))));
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        List<Integer> test = new ArrayList<>();
        
        return null;
    }*/

}
