package sauds.toolbox.graphs;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Node<T> {

    public T value;
    public LinkedHashSet<Node<T>> adjacent;

    public Node(T value) {
        this(value, null);
    }

    public Node(T value, Collection<Node<T>> adjacent) {
        this.value = value;
        this.adjacent = new LinkedHashSet<>();
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


    public Iterable<List<Node<T>>> walks(Node<T> target) {
        return walks(target, (tNode, tNode2) -> true);
    }

    public Iterable<List<Node<T>>> walks(Node<T> target, BiPredicate<Node<T>, Node<T>> predicate) {
        Node<T> thiz = this;

        return () -> new Iterator<>() {

            private boolean isInit = false;
            private Path nextPath = new Path(List.of(thiz));
            private final List<List<Node<T>>> siblings = new ArrayList<>(List.of(List.of(thiz)));
            private final List<Integer> treeIndex = new ArrayList<>(List.of(0));

            private void tryInit() {
                if (isInit) return;

                isInit = true;
                dive();
            }

            @Override
            public boolean hasNext() {
                tryInit();
                return nextPath != null;
            }

            @Override
            public List<Node<T>> next() {
                tryInit();
                while (true) {
                    Path selectedPath = nextPath;
                    List<Node<T>> lastSiblings = siblings.get(siblings.size() - 1);

                    int tailIndexValue = treeIndex.get(treeIndex.size() - 1);
                    if (tailIndexValue < lastSiblings.size() - 1) { // not end of folder
                        treeIndex.set(treeIndex.size() - 1, tailIndexValue + 1);
                        nextPath = new Path(siblings, treeIndex);
                    } else {
                        goUp();
                    }
                    if (nextPath != null) {
                        dive();
                    }
                    if (selectedPath.tail().equals(target)) {
                        return selectedPath.getPath();
                    }
                }
            }

            private void goUp() {
                while(treeIndex.get(treeIndex.size()-1) == siblings.get(siblings.size()-1).size()-1) {
                    if(treeIndex.size() == 1) {
                        nextPath = null;
                        return;
                    }
                    siblings.remove(siblings.size()-1);
                    treeIndex.remove(treeIndex.size()-1);
                }
                treeIndex.set(treeIndex.size()-1, treeIndex.get(treeIndex.size()-1) + 1);
                nextPath = new Path(siblings, treeIndex);
            }

            private void dive() {
                while(nextPath.tail().adjacent.stream().anyMatch(n -> !target.equals(n) && !nextPath.contains(n))) {
                    List<Node<T>> newSiblings = nextPath.tail().adjacent.stream()
                            .filter(n -> !nextPath.contains(n))
                            .collect(Collectors.toList());
                    if(newSiblings.isEmpty()) {
                        return;
                    }

                    siblings.add(newSiblings);
                    treeIndex.add(0);
                    nextPath = new Path(siblings, treeIndex);
                }
            }

            class Path {
                private final List<Node<T>> path;
                private final Set<Node<T>> nodes;
                public Path(List<Node<T>> path) {
                    this.path = path;
                    this.nodes = new HashSet<>(path);
                }
                public Path(List<List<Node<T>>> siblings, List<Integer> treeIndex) {
                    path = new ArrayList<>(treeIndex.size());
                    for (int i = 0; i < treeIndex.size(); i++) {
                        path.add(siblings.get(i).get(treeIndex.get(i)));
                    }
                    nodes = new HashSet<>(path);
                }
                public List<Node<T>> getPath() {
                    return path;
                }
                public Set<Node<T>> getNodes() {
                    return nodes;
                }
                public boolean contains(Node<T> n) {
                    return nodes.contains(n);
                }
                public Node<T> tail() {
                    return path.get(path.size()-1);
                }
            }

        };
    }

}
