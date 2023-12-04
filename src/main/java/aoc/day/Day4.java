package aoc.day;

import aoc.AbstractSolver;

import java.util.Arrays;

public class Day4 extends AbstractSolver {
    public Day4() {
        super(4);
    }

    @Override
    public Object puzzle1() {
        int sum = 0;

        for (var line : getLines()) {
            String[] full = line.split(":")[1].trim().split("\\|");

            var winners = Arrays.stream(full[0].trim().split(" +"))
                    .distinct()
                    .map(Integer::parseInt)
                    .toList();

            sum += Arrays.stream(full[1].trim().split(" +"))
                    .map(Integer::parseInt)
                    .filter(winners::contains)
                    .reduce(1, (v1, v2) -> v1 * 2) / 2;
        }

        return sum;
    }

    @Override
    public Object puzzle2() {

        return null;
    }
}
