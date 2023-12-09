package aoc.day;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day9Test {

    @Test
    void puzzle1() {
        assertEquals(1762065988L, new Day9().puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(1066L, new Day9().puzzle2());
    }
}