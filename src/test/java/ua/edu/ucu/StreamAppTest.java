package ua.edu.ucu;

import ua.edu.ucu.stream.*;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

/**
 * @author andrii
 */
public class StreamAppTest {

    private IntStream intStream;

    @Before
    public void init() {
        int[] intArr = {-1, 0, 1, 2, 3};
        new AsIntStream(intArr);
        intStream = AsIntStream.of(intArr);
    }

    @Test
    public void testStreamOperations() {
        System.out.println("streamOperations");
        int expResult = 42;
        int result = StreamApp.streamOperations(intStream);
        assertEquals(expResult, result);
    }

    @Test
    public void testStreamToArray() {
        System.out.println("streamToArray");
        int[] expResult = {-1, 0, 1, 2, 3};
        int[] result = StreamApp.streamToArray(intStream);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testStreamForEach() {
        System.out.println("streamForEach");
        String expResult = "-10123";
        String result = StreamApp.streamForEach(intStream);
        assertEquals(expResult, result);
    }

    @Test
    public void testStreamAverage() {
        int[] intArr = {0, 1, 2};
        IntStream st = AsIntStream.of(intArr);
        assertEquals(java.util.Optional.of(1.0), java.util.Optional.of(st.average()));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testStreamAverageErr() {
        int[] intArr = {};
        IntStream st = AsIntStream.of(intArr);
        java.util.Optional.of(st.average());
    }

    @Test
    public void testStreamCount() {
        int[] intArr = {0, 1, 2};
        assertEquals(3, AsIntStream.of(intArr).count());
    }

    @Test
    public void testStreamSum() {
        int[] intArr = {0, 1, 2};
        assertEquals(java.util.Optional.of(3), java.util.Optional.of(AsIntStream.of(intArr).sum()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStreamSumErr() {
        int[] intArr = {};
        IntStream st = AsIntStream.of(intArr);
        java.util.Optional.of(st.sum());
    }

    @Test
    public void testStreamMin() {
        int[] intArr = {0, 1, 2};
        assertEquals(java.util.Optional.of(0), java.util.Optional.of(AsIntStream.of(intArr).min()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStreamMinErr() {
        int[] intArr = {};
        IntStream st = AsIntStream.of(intArr);
        java.util.Optional.of(st.min());
    }


    @Test
    public void testStreamMax() {
        int[] intArr = {0, 1, 2};
        assertEquals(java.util.Optional.of(2), java.util.Optional.of(AsIntStream.of(intArr).max()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStreamMaxErr() {
        int[] intArr = {};
        IntStream st = AsIntStream.of(intArr);
        java.util.Optional.of(st.max());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStreamReduceErr() {
        int[] intArr = {};
        IntStream st = AsIntStream.of(intArr);
        java.util.Optional.of(st.reduce(0, (t, x) -> t += x));
    }


}
