package aoc.day;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test
{
    @Test
    void puzzle1() {
        assertEquals(53921, new Day1().puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(54676, new Day1().puzzle2());
    }
}
