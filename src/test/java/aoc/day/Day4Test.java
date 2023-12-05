package aoc.day;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {

    @Test
    void puzzle1() {
        assertEquals(27845, new Day4().puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(9496801, new Day4().puzzle2());
    }
}