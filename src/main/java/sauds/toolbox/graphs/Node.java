package sauds.toolbox.graphs;

import java.util.*;
import java.util.function.Consumer;
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


    /**
     * Creates an Iterable to iterate breadth first throughout the graph starting from this node.
     * @return An iterable object that iterates breadth first through the graph.
     */
    public Iterable<Node<T>> breadthFirst() {
        return breadthFirst(n -> true);
    }

    /**
     * Creates an Iterable to iterate breadth first throughout the graph starting from this node.
     * @param predicate A predicated to decide whether a node can be scanned.
     * @return An iterable object that iterates breadth first through the graph.
     */
    public Iterable<Node<T>> breadthFirst(Predicate<Node<T>> predicate) {
        return breadthFirst(predicate, null);
    }

    /**
     * Iterate breadth first throughout the graph starting from this node.
     * @param predicate A predicated to decide whether a node can be scanned.
     * @param preProcess A method to process a node (or its neighbours) before it's neighbors are scanned.
     */
    public void breadthFirstPrefiltered(Predicate<Node<T>> predicate, Consumer<Node<T>> preProcess) {
        breadthFirst(predicate, preProcess).forEach(n -> {});
    }

    /**
     * Creates an Iterable to iterate breadth first throughout the graph starting from this node.
     * @param predicate A predicated to decide whether a node can be scanned.
     * @param preProcess A method to process a node (or its neighbours) before it's neighbors are scanned.
     * @return An iterable object that iterates breadth first through the graph.
     */
    private Iterable<Node<T>> breadthFirst(Predicate<Node<T>> predicate, Consumer<Node<T>> preProcess) {
        Node<T> thiz = this;

        return () -> new Iterator<>() {

            final Queue<Node<T>> q = new LinkedList<>(singleton(thiz));
            final Set<Node<T>> scanned = new HashSet<>(singleton(thiz));

            @Override
            public boolean hasNext() {
                q.removeIf(n -> !predicate.test(n));
                return !q.isEmpty();
            }

            @Override
            public Node<T> next() {
                Node<T> curr = q.poll();
                if (isNull(curr)) {
                    return null;
                }

                if (nonNull(preProcess)) {
                    preProcess.accept(curr);
                }

                curr.adjacent.stream()
                        .filter(n -> !scanned.contains(n))
                        .filter(predicate)
                        .peek(scanned::add)
                        .forEach(q::add);

                return curr;
            }
        };
    }


    /**
     * Creates an Iterable to iterate depth first throughout the graph starting from this node.
     * @return An iterable object that iterates depth first through the graph.
     */
    public Iterable<Node<T>> depthFirst() {
        return depthFirst(n -> true);
    }

    /**
     * Creates an Iterable to iterate depth first throughout the graph starting from this node.
     * @param predicate A predicated to decide whether a node can be scanned.
     * @return An iterable object that iterates depth first through the graph.
     */
    public Iterable<Node<T>> depthFirst(Predicate<Node<T>> predicate) {
        return depthFirst(predicate, null);
    }

    /**
     * Creates an Iterable to iterate depth first throughout the graph starting from this node.
     * @param predicate A predicated to decide whether a node can be scanned.
     * @param preProcess A method to process a node (or its neighbours) before it's neighbors are scanned.
     */
    public void depthFirstPrefiltered(Predicate<Node<T>> predicate, Consumer<Node<T>> preProcess) {
        depthFirst(predicate, preProcess).forEach(n -> {});
    }

    /**
     * Creates an Iterable to iterate depth first throughout the graph starting from this node.
     * @param predicate A predicated to decide whether a node can be scanned.
     * @param preProcess A method to process a node (or its neighbours) before it's neighbors are scanned.
     * @return An iterable object that iterates depth first through the graph.
     */
    private Iterable<Node<T>> depthFirst(Predicate<Node<T>> predicate, Consumer<Node<T>> preProcess) {
        Node<T> thiz = this;

        return () -> new Iterator<>() {
            final Stack<Node<T>> s = new Stack<>() {{
                add(thiz);
            }};
            final Set<Node<T>> scanned = new HashSet<>(singleton(thiz));

            @Override
            public boolean hasNext() {
                s.removeIf(n -> !predicate.test(n));
                return !s.isEmpty();
            }

            @Override
            public Node<T> next() {
                Node<T> curr = s.pop();
                if (isNull(curr)) {
                    return null;
                }

                if (nonNull(preProcess)) {
                    preProcess.accept(curr);
                }

                curr.adjacent.stream()
                        .filter(n -> !scanned.contains(n))
                        .filter(predicate)
                        .peek(scanned::add)
                        .forEach(s::add);

                return curr;
            }
        };
    }

}
