package a03b.e1;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LazyTreeFactoryImpl implements LazyTreeFactory {

    @Override
    public <X> LazyTree<X> cons(Optional<X> root, Supplier<LazyTree<X>> leftSupp, Supplier<LazyTree<X>> rightSupp) {
        return new LazyTree<X>() {

            @Override
            public boolean hasRoot() {
                return root.isPresent();
            }

            @Override
            public X root() {
                return root.get();
            }

            @Override
            public LazyTree<X> left() {
                return leftSupp.get();
            }

            @Override
            public LazyTree<X> right() {
                return rightSupp.get();
            }
            
        };
    }

    private <X> LazyTree<X> emptyTree() {
        return cons(Optional.empty(), () -> null, () -> null);
    }

    @Override
    public <X> LazyTree<X> constantInfinite(X value) {
        return cons(Optional.of(value),
                    () -> constantInfinite(value),
                    () -> constantInfinite(value));
    }

    @Override
    public <X> LazyTree<X> fromMap(X root, Map<X, Pair<X, X>> map) {
        return cons(Optional.of(root),
                    () -> map.containsKey(root) ? fromMap(map.get(root).getX(), map) : emptyTree(),
                    () -> map.containsKey(root) ? fromMap(map.get(root).getY(), map) : emptyTree());
    }

    @Override
    public <X> LazyTree<X> fromTwoIterations(X root, UnaryOperator<X> leftOp, UnaryOperator<X> rightOp) {
        return cons(Optional.of(root),
                    () -> fromTwoIterations(leftOp.apply(root), leftOp, rightOp),
                    () -> fromTwoIterations(rightOp.apply(root), leftOp, rightOp));
    }

    @Override
    public <X> LazyTree<X> fromTreeWithBound(LazyTree<X> tree, int bound) {
        return bound > 0
             ? cons(Optional.ofNullable(tree.root()), () -> fromTreeWithBound(tree, bound - 1), () -> fromTreeWithBound(tree, bound - 1))
             : emptyTree();
    }

}
