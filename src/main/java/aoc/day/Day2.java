package aoc.day;

import aoc.AbstractSolver;

import java.util.*;
import java.util.stream.Collectors;

public class Day2 extends AbstractSolver {
    public Day2() {
        super(2);
    }

    @Override
    public Integer puzzle1() {

        int sum = 0;
        var first = Map.of("red", 12, "green", 13, "blue", 14);

        for (var line : getLines()) {
            var gameSplit = line.split(":");

            var map = Arrays.stream(gameSplit[1].split(";"))
                    .flatMap(string -> Arrays.stream(string.trim().split(",")))
                    .map(string -> string.trim().split(" "))
                    .collect(Collectors.toMap(strings -> strings[1], strings -> Integer.parseInt(strings[0]), (v1, v2) -> v1 > v2 ? v1 : v2));

            if (map.entrySet().stream().allMatch(entry -> first.get(entry.getKey()) >= entry.getValue())) {
                sum += Integer.parseInt(gameSplit[0].split(" ")[1]);
            }
        }

        return sum;
    }

    @Override
    public Integer puzzle2() {

        int sum = 0;

        for (var line : getLines()) {
            var map = Arrays.stream(line.split(":")[1].split(";"))
                    .flatMap(string -> Arrays.stream(string.trim().split(",")))
                    .map(string -> string.trim().split(" "))
                    .collect(Collectors.toMap(strings -> strings[1], strings -> Integer.parseInt(strings[0]), (v1, v2) -> v1 > v2 ? v1 : v2));

            sum += map.values().stream().reduce((v1, v2) -> v1 * v2).orElse(0);
        }

        return sum;
    }
}
