package aoc.day;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Test {
    @Test
    void puzzle1() {
        assertEquals(2716, new Day2().puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(72227, new Day2().puzzle2());
    }
}
