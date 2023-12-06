package aoc.day;

import aoc.AbstractSolver;

import java.util.Arrays;

public class Day6 extends AbstractSolver {
    public Day6() {
        super(6);
    }

    @Override
    public Object puzzle1() {
        var lines = lines();

        var times = Arrays.stream(lines.get(0).split("\\s+"))
                .skip(1)
                .map(Integer::parseInt)
                .toList();
        var distances = Arrays.stream(lines.get(1).split("\\s+"))
                .skip(1)
                .map(Integer::parseInt)
                .toList();

        long result = 1L;

        for (int i = 0; i < times.size(); i++) {
            result *= getCount(times.get(i), distances.get(i));
        }

        return result;
    }

    private int getCount(long time, long distance) {
        int count = 0;

        for (int timeHeld = 0; timeHeld <= time; timeHeld++) {
            if (distance < ((time - (long) timeHeld) * (long) timeHeld)) {
                count++;
            }
        }

        return count;
    }

    @Override
    public Object puzzle2() {
        var lines = lines();
        var time = Long.parseLong(lines.get(0).split(":")[1].replaceAll("\\s+", "")) + 1;
        var record = Long.parseLong(lines.get(1).split(":")[1].replaceAll("\\s+", ""));

        var current = 0L;
        var stepSize = time / 2L;

        while (stepSize >= 1) {
            current += record < ((time - current) * current) ? -stepSize : stepSize;
            stepSize /= 2;
        }

        return time - 2 * current;
    }
}
