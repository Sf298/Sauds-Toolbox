package sauds.toolbox.graphs;

import java.util.*;
import java.util.function.Predicate;

import static java.util.Collections.singleton;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Node<T> {

    public T value;
    public Set<Node<T>> adjacent;

    public Node(T value) {
        this(value, null);
    }

    public Node(T value, Collection<Node<T>> adjacent) {
        this.value = value;
        this.adjacent = new HashSet<>();
        if (nonNull(adjacent)) {
            this.adjacent.addAll(adjacent);
        }
    }

    public Iterable<Node<T>> breadthFirst() {
        return breadthFirst(n -> true);
    }

    public Iterable<Node<T>> breadthFirst(Predicate<Node<T>> predicate) {
        Node<T> thiz = this;

        return () -> new Iterator<>() {

            final Queue<Node<T>> q = new LinkedList<>(singleton(thiz));
            final Set<Node<T>> scanned = new HashSet<>(singleton(thiz));

            @Override
            public boolean hasNext() {
                return !q.isEmpty();
            }

            @Override
            public Node<T> next() {
                Node<T> curr = q.poll();
                if (isNull(curr)) {
                    return null;
                }

                curr.adjacent.stream()
                        .filter(n -> !scanned.contains(n))
                        .peek(scanned::add)
                        .filter(predicate)
                        .forEach(q::add);

                return curr;
            }
        };
    }

    public Iterable<Node<T>> depthFirst() {
        return depthFirst(n -> true);
    }

    public Iterable<Node<T>> depthFirst(Predicate<Node<T>> predicate) {
        Node<T> thiz = this;

        return () -> new Iterator<>() {
            final Stack<Node<T>> s = new Stack<>() {{
                add(thiz);
            }};
            final Set<Node<T>> scanned = new HashSet<>(singleton(thiz));

            @Override
            public boolean hasNext() {
                return !s.isEmpty();
            }

            @Override
            public Node<T> next() {
                Node<T> curr = s.pop();
                if (isNull(curr)) {
                    return null;
                }

                curr.adjacent.stream()
                        .filter(n -> !scanned.contains(n))
                        .peek(scanned::add)
                        .filter(predicate)
                        .forEach(s::add);

                return curr;
            }
        };
    }

}
