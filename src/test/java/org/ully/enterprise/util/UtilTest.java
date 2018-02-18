package org.ully.enterprise.util;

import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;


public class UtilTest {

    Stream<Number> str = Stream.of(Integer.valueOf(10), Double.valueOf(1.1));
    Stream<Number> strWithNull = Stream.of(Integer.valueOf(10), Double.valueOf(1.1), null);

    @Test
    public void filterNumbers() {
        Assert.assertEquals(2, Util.filter(str, Number.class).count());
    }

    @Test
    public void filterInteger() {
        Assert.assertEquals(1, Util.filter(str, Integer.class).count());
    }

    @Test
    public void nullIsIncluded() {
        Assert.assertEquals(2, Util.filter(strWithNull, Integer.class).count());
    }

}
