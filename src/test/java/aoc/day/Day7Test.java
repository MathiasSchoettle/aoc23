package aoc.day;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {
    @Test
    void puzzle1() {
        assertEquals(250951660L, new Day7().puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(251481660L, new Day7().puzzle2());
    }
}