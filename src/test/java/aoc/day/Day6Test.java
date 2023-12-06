package aoc.day;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {
    @Test
    void puzzle1() {
        assertEquals(2449062L, new Day6().puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(33149631L, new Day6().puzzle2());
    }
}
