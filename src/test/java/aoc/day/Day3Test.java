package aoc.day;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {

    @Test
    void puzzle1() {
        assertEquals(525119, new Day3().puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(76504829, new Day3().puzzle2());
    }
}