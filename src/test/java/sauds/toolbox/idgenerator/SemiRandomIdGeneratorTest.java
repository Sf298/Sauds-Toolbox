package sauds.toolbox.idgenerator;

import org.junit.jupiter.api.Test;
import sauds.toolbox.data.structures.RangeSet;

import java.util.Set;

public class SemiRandomIdGeneratorTest {

    @Test
    void next() {
        for (int i = 4; i < 100; i++) {
            Set<Long> set = generateAll(i);
            System.out.println(i + set.toString());
            assert set.contains((long) i - 1);
        }
    }

    private Set<Long> generateAll(int n) {
        SemiRandomIdGenerator gen = new SemiRandomIdGenerator(n);
        Set<Long> set = new RangeSet();
        for (int i = 0; i < n; i++) {
            set.add(gen.next());
        }
        return set;
    }

}