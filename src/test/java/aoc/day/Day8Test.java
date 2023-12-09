package aoc.day;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day8Test {

    @Test
    void puzzle1() {
        assertEquals(18113, new Day8().puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(12315788159977L, new Day8().puzzle2());
    }
}