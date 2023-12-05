package aoc.day;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test {
    @Test
    void puzzle1() {
        assertEquals(662197086L, new Day5().puzzle1());
    }

    @Test
    void puzzle2() {
        assertEquals(52510809L, new Day5().puzzle2());
    }
}
