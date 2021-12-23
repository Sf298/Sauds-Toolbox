package sauds.toolbox.data.structures;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class VolumeSet {

    HashSet<Cuboid> cuboids = new HashSet<>();

    public BigInteger area() {
        return cuboids.stream().map(Cuboid::area)
                .reduce(BigInteger.ZERO, BigInteger::add);
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

    public void subtract(Cuboid toSubtract) {
        List<Cuboid> affected = cuboids.stream()
                .filter(toSubtract::intersects)
                .collect(toList());
        if (affected.isEmpty()) {
            return;
        }

        cuboids.removeAll(affected);

        List<Cuboid> newAffected = affected.stream()
                .flatMap(c -> c.segment(toSubtract).stream())
                .filter(c -> !toSubtract.contains(c))
                .collect(toList());

        List<Cuboid> merged = Cuboid.mergeAll(newAffected);

        cuboids.addAll(merged);
    }

    public void retain(Cuboid toAdd) {
        List<Cuboid> affected = cuboids.stream()
                .filter(toAdd::intersects)
                .collect(toList());
        cuboids.clear();

        if (affected.isEmpty()) {
            return;
        }

        List<Cuboid> newAffected = affected.stream()
                .flatMap(c -> c.segment(toAdd).stream())
                .filter(toAdd::contains)
                .collect(toList());

        List<Cuboid> merged = Cuboid.mergeAll(newAffected);

        cuboids.addAll(merged);
    }

    /* TODO finish implementing this.
    public void retainAll(List<Cuboid> toRetain) {
        List<Cuboid> affected = cuboids.stream()
                .filter(c -> toRetain.stream().anyMatch(c::intersects))
                .collect(toList());
        cuboids.clear();

        if (affected.isEmpty()) {
            return;
        }

        List<Cuboid> newAffected = affected.stream()
                .flatMap(c -> toRetain.stream().flatMap(tr -> c.segment(tr).stream()))
                .filter(toRetain::contains)
                .collect(toList());

        List<Cuboid> merged = Cuboid.mergeAll(newAffected);

        cuboids.addAll(merged);
    }*/

}
