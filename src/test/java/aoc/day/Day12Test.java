package aoc.day;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {

    @Test
    void puzzle1() {
        assertEquals(7490L, new Day12().puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(65607131946466L, new Day12().puzzle2());
    }
}