package sauds.toolbox.data.structures;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class VolumeSet {

    HashSet<Cuboid> cuboids = new HashSet<>();

    public long area() {
        return cuboids.stream().mapToInt(Cuboid::area).sum();
    }

    public void add(Cuboid toAdd) {
        List<Cuboid> affected = cuboids.stream()
                .filter(toAdd::intersects)
                .collect(toList());
        if (affected.isEmpty()) {
            cuboids.add(toAdd);
            return;
        }

        cuboids.removeAll(affected);

        List<Cuboid> newAffected = affected.stream()
                .flatMap(c -> c.segment(toAdd).stream())
                .filter(c -> !toAdd.contains(c))
                .collect(toList());

        newAffected.add(toAdd);

        List<Cuboid> merged = Cuboid.mergeAll(newAffected);

        cuboids.addAll(merged);
    }

    public void subtract(Cuboid toAdd) {
        List<Cuboid> affected = cuboids.stream()
                .filter(toAdd::intersects)
                .collect(toList());
        if (affected.isEmpty()) {
            return;
        }

        cuboids.removeAll(affected);

        List<Cuboid> newAffected = affected.stream()
                .flatMap(c -> c.segment(toAdd).stream())
                .filter(c -> !toAdd.contains(c))
                .collect(toList());

        List<Cuboid> merged = Cuboid.mergeAll(newAffected);

        cuboids.addAll(merged);
    }

}
