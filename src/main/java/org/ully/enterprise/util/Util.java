package org.ully.enterprise.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class Util {

    private static final double EPSILON = 0.00000000001;

    private Util() {
        // prevent instantiation
    }

    /**
     * create a {@code null} tolerant stream.
     *
     * @param <T>
     *            Typ eof the stream elements
     * @param list
     *            a list to stream, might be {@code null}
     * @return a stream from the list
     */
    public static <T> Stream<T> streamOf(List<T> list) {
        return Optional.ofNullable(list).orElse(Collections.emptyList()).stream();
    }

    /**
     * Get the maximum from the list.
     *
     * @param value
     *            list of long values
     * @return the maximum
     */
    public static long max(long... value) {
        return LongStream.of(value).max().orElse(0);
    }

    /**
     * Check double value to be about zero.
     *
     * @param value
     *            floating point value to check
     * @return true, if the value is (about) zero
     */
    public static boolean isZero(double value) {
        return Math.abs(value) <= EPSILON;
    }

    /**
     * Filters all Objects of class T from the Stream.
     *
     * @param stream
     *            The Stream to filter, might not be {@code null}.
     * @param clazz
     *            Class object specifying the desired class
     * @return The filtered, converted stream
     */
    public static <U, T> Stream<T> filter(Stream<U> stream, Class<T> clazz) {
        return stream.filter(c -> c == null || clazz.isAssignableFrom(c.getClass())) //
                .map(c -> (T) c);
    }

}
