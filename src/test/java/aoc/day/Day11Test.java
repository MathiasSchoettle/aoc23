package aoc.day;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {

    @Test
    void puzzle1() {
        assertEquals(9799681L, new Day11().puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(513171773355L, new Day11().puzzle2());
    }
}