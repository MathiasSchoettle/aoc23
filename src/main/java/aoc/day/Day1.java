package aoc.day;

import aoc.AbstractSolver;

import java.util.Map;

public class Day1 extends AbstractSolver {

    @Override
    public Integer puzzle1() {
        return lines().stream()
                .map(s -> s.replaceAll("\\D", ""))
                .map(s -> s.charAt(0) + "" + s.charAt(s.length() - 1))
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private static final Map<String, String> VALUES = Map.of(
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9"
    );

    @Override
    public Integer puzzle2() {
        return lines().stream()
                .map(this::replaceTextNumbers)
                .map(s -> s.replaceAll("\\D", ""))
                .map(s -> s.charAt(0) + "" + s.charAt(s.length() - 1))
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private String replaceTextNumbers(String value) {
        for (var entry : VALUES.entrySet()) {
            var replace = entry.getKey() + entry.getValue() + entry.getKey();
            value = value.replace(entry.getKey(), replace);
        }

        return value;
    }
}
