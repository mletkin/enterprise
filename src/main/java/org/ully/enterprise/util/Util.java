package org.ully.enterprise.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class Util {

    private Util() {
        // prevent instantiation
    }

    public static <T> Stream<T> streamOf(List<T> list) {
        return Optional.ofNullable(list).orElse(Collections.emptyList()).stream();
    }

    public static long max(long... value) {
        return LongStream.of(value).max().orElse(0);
    }

}
