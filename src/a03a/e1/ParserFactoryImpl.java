package a03a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class ParserFactoryImpl implements ParserFactory {
    
    private <X> Parser<X> fromPattern(List<X> pattern) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                List<X> sequence = new ArrayList<>();
                iterator.forEachRemaining(sequence::add);
                return sequence.equals(pattern);
            }
            
        };
    }

    @Override
    public <X> Parser<X> fromFinitePossibilities(Set<List<X>> acceptedSequences) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                return acceptedSequences.stream()
                       .filter(pattern -> fromPattern(pattern).accept(iterator))
                       .count() > 0;
            }
            
        };
    }

    @Override
    public <X> Parser<X> fromGraph(X x0, Set<Pair<X, X>> transitions, Set<X> acceptanceInputs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromGraph'");
    }

    @Override
    public <X> Parser<X> fromIteration(X x0, Function<X, Optional<X>> next) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromIteration'");
    }

    @Override
    public <X> Parser<X> recursive(Function<X, Optional<Parser<X>>> nextParser, boolean isFinal) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recursive'");
    }

    @Override
    public <X> Parser<X> fromParserWithInitial(X x, Parser<X> parser) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromParserWithInitial'");
    }

}
