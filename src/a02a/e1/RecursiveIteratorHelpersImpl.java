package a02a.e1;

import java.util.LinkedList;
import java.util.List;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers {

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        List<X> tempList = new LinkedList<>(list);
        if(tempList.size() > 0)
        {
            return new RecursiveIterator<X>() {

                @Override
                public X getElement() {
                    return tempList.get(0);
                }

                @Override
                public RecursiveIterator<X> next() {
                    tempList.remove(0);
                    return fromList(tempList);
                }
                
            };
        } else {
            return null;
        }
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        List<X> outList = new LinkedList<>();
        if(input != null) {
            if(max > 0) {
                outList.add(input.getElement());
            }
            outList.addAll(toList(input.next(), max - 1));
        }
        return outList;
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        return first != null && second != null
             ? new RecursiveIterator<Pair<X,Y>>() {

                @Override
                public Pair<X, Y> getElement() {
                    return new Pair<X,Y>(first.getElement(), second.getElement());
                }

                @Override
                public RecursiveIterator<Pair<X, Y>> next() {
                    return zip(first.next(), second.next());
                } 
            }
            : null;
    }

    public RecursiveIterator<Integer> integerIterator(int start) {
        return new RecursiveIterator<Integer>() {
            private int counter = start;

            @Override
            public Integer getElement() {
                return counter;
            }

            @Override
            public RecursiveIterator<Integer> next() {
                return integerIterator(start + 1);
            }
            
        };
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return zip(iterator, integerIterator(0));
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        return first == null && second == null ? null
        : new RecursiveIterator<X>() {

            @Override
            public X getElement() {
                return first != null ? first.getElement() : second.getElement();
            }

            @Override
            public RecursiveIterator<X> next() {
                return alternate(second, first != null ? first.next() : second.next());
            }
            
        };
    }

}
