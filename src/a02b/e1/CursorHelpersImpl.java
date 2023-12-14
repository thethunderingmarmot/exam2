package a02b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class CursorHelpersImpl implements CursorHelpers {

    private <X> Cursor<X> generic(Iterator<X> iterator) {
        return new Cursor<X>() {
            
            private X currentElement = iterator.next();

            @Override
            public X getElement() {
                return currentElement;
            }

            @Override
            public boolean advance() {
                boolean hasNext = iterator.hasNext();
                if(hasNext) {
                    currentElement = iterator.next();
                }
                return hasNext;
                
            }
        };
    }

    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return generic(list.iterator());
    }

    @Override
    public Cursor<Integer> naturals() {
        return generic(IntStream.iterate(0, x->x+1).iterator());
    }

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        return new Cursor<X>() {
            
            private int index = 0;

            @Override
            public X getElement() {
                return input.getElement();
            }

            @Override
            public boolean advance() {
                return ++index < max ? input.advance() : false;
            }
            
        };
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        do {
            consumer.accept(input.getElement());
        } while(input.advance());
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        List<X> elements = new ArrayList<>();
        forEach(take(input, max), elem -> elements.add(elem));
        return elements;
    }

}
