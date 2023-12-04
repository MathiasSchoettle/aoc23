package aoc.day;

import aoc.AbstractSolver;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        var lines = getLines();

        Map<Integer, Integer> collect = IntStream.range(0, lines.size())
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> 1));

        for (int i = 0; i < lines.size(); i++) {
            String[] full = lines.get(i).split(":")[1].trim().split("\\|");

            var winners = Arrays.stream(full[0].trim().split(" +"))
                    .distinct()
                    .map(Integer::parseInt)
                    .toList();

            long count = Arrays.stream(full[1].trim().split(" +"))
                    .map(Integer::parseInt)
                    .filter(winners::contains)
                    .count();

            var amount = collect.get(i);
            for (int j = 1; j <= count; j++) {
                collect.computeIfPresent(i + j, (k, v) -> v + amount);
            }
        }

        return collect.values().stream().mapToInt(v -> v).sum();
    }
}
