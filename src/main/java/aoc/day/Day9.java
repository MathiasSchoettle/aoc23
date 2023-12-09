package aoc.day;

import aoc.AbstractSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 extends AbstractSolver {

    @Override
    public Object puzzle1() {

        var sum = 0L;

        for (var line : lines()) {
            List<Long> list = Arrays.stream(line.split("\\s")).map(Long::parseLong).toList();
            List<Long> numbers = new ArrayList<>();

            while (true) {
                numbers.addFirst(list.getLast());
                if (list.stream().allMatch(v -> v == 0)) break;
                list = toList(list);
            }

            sum += numbers.stream().reduce(0L, Long::sum);
        }

        return sum;
    }

    @Override
    public Object puzzle2() {
        var sum = 0L;

        for (var line : lines()) {
            List<Long> list = Arrays.stream(line.split("\\s")).map(Long::parseLong).toList();
            List<Long> numbers = new ArrayList<>();

            while (true) {
                numbers.addFirst(list.getFirst());
                if (list.stream().allMatch(v -> v == 0)) break;
                list = toList(list);
            }

            sum += numbers.stream().reduce(0L, (v1, v2) -> v2 - v1);
        }

        return sum;
    }

    private List<Long> toList(List<Long> list) {
        var current = list.getFirst();
        var result = new ArrayList<Long>();

        for (var item : list.stream().skip(1).toList()) {
            result.addLast(item - current);
            current = item;
        }

        return result;
    }
}
