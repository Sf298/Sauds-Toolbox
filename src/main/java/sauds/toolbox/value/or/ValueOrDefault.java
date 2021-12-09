
package sauds.toolbox.value.or;

import java.util.function.Function;

import static java.util.Objects.isNull;

public class ValueOrDefault {

    public static <T> T valueOrDefault(T t, T defaultValue) {
        return isNull(t) ? defaultValue : t;
    }

    public static <T, R> R valueOrDefault(T t, Function<T, R> func, R defaultValue) {
        return isNull(t) ? defaultValue : func.apply(t);
    }

}
