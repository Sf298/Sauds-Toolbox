package sauds.toolbox.value.or;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static sauds.toolbox.value.or.ValueOrDefault.valueOrDefault;

class ValueOrDefaultTest {

    @Test
    void testFunctionless_isNull() {
        Integer i = null;
        int actual = valueOrDefault(i, 2);
        assertThat(actual).isEqualTo(2);
    }

    @Test
    void testFunctionless_nonNull() {
        Integer i = 1;
        int actual = valueOrDefault(i, 2);
        assertThat(actual).isEqualTo(1);
    }

    @Test
    void testFunction_isNull() {
        ValueOrDefaultEntity e = null;
        int actual = valueOrDefault(e, ValueOrDefaultEntity::getValue, 2);
        assertThat(actual).isEqualTo(2);
    }

    @Test
    void testFunction_nonNull() {
        ValueOrDefaultEntity e = new ValueOrDefaultEntity(1);
        int actual = valueOrDefault(e, ValueOrDefaultEntity::getValue, 2);
        assertThat(actual).isEqualTo(1);
    }

    public class ValueOrDefaultEntity {
        Integer value;

        public ValueOrDefaultEntity(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

}