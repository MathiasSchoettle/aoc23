package aoc.day;

import aoc.AbstractSolver;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day7 extends AbstractSolver {

    String input = "32T3K 765\nT55J5 684\nKK677 28\nKTJJT 220\nQQQJA 483\n";

    @Override
    public Object puzzle1() {
        Map<String, Long> map = lines().stream().map(line -> line.split(" "))
                .collect(Collectors.toMap(arr -> arr[0], arr -> Long.parseLong(arr[1])));

        Map<Long, Long> collect = map.entrySet().stream()
                .collect(Collectors.toMap(entry -> toValue(entry.getKey()), Map.Entry::getValue));

        var sorted = new TreeMap<>(collect);

        int i = 1;
        long sum = 0L;

        for (var entry : sorted.entrySet()) {
            sum += i++ * entry.getValue();
        }

        return sum;
    }

    Map<Character, Long> map = Map.ofEntries(
            Map.entry('A', 14L),
            Map.entry('K', 13L),
            Map.entry('Q', 12L),
            Map.entry('J', 11L),
            Map.entry('T', 10L),
            Map.entry('9', 9L),
            Map.entry('8', 8L),
            Map.entry('7', 7L),
            Map.entry('6', 6L),
            Map.entry('5', 5L),
            Map.entry('4', 4L),
            Map.entry('3', 3L),
            Map.entry('2', 2L)
    );

    private long toValue(String cards) {
        Map<String, Long> collect = Arrays.stream(cards.split(""))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long multi = switch (collect.size()) {
            case 5 -> 1L;
            case 4 -> 10L;
            case 3 -> collect.values().stream().max(Comparator.naturalOrder()).get() == 3L ? 1_000L : 100L;
            case 2 -> collect.values().stream().sorted().findFirst().get() == 2L ? 10_000L : 100_000L;
            case 1 -> 1_000_000L;
            default -> throw new RuntimeException("Invalid case for card matching: " + collect.size());
        } * 10_000_000_000L;

        long value = 0L;
        long m = 1L;

        for (int i = 4; i >= 0; i--) {
            value += map.get(cards.charAt(i)) * m;
            m *= 100L;
        }

        return multi + value;
    }

    @Override
    public Object puzzle2() {
        Map<String, Long> map = lines().stream().map(line -> line.split(" "))
                .collect(Collectors.toMap(arr -> arr[0], arr -> Long.parseLong(arr[1])));

        Map<Long, Long> collect = map.entrySet().stream()
                .collect(Collectors.toMap(entry -> toValuePart2(entry.getKey()), Map.Entry::getValue));

        var sorted = new TreeMap<>(collect);

        int i = 1;
        long sum = 0L;

        for (var entry : sorted.entrySet()) {
            sum += i++ * entry.getValue();
        }

        return sum;
    }

    Map<Character, Long> map2 = Map.ofEntries(
            Map.entry('A', 13L),
            Map.entry('K', 12L),
            Map.entry('Q', 11L),
            Map.entry('T', 10L),
            Map.entry('9', 9L),
            Map.entry('8', 8L),
            Map.entry('7', 7L),
            Map.entry('6', 6L),
            Map.entry('5', 5L),
            Map.entry('4', 4L),
            Map.entry('3', 3L),
            Map.entry('2', 2L),
            Map.entry('J', 1L)
    );

    private long toValuePart2(String cards) {
        Map<String, Long> collect = Arrays.stream(cards.split(""))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (collect.containsKey("J") && collect.get("J") < 5L) {
            var jokerCount = collect.get("J");
            collect.remove("J");

            Map.Entry<String, Long> stringLongEntry = collect.entrySet().stream()
                    .max((entry1, entry2) -> {

                        var val11 = entry1.getValue();
                        var val22 = entry2.getValue();

                        if (!val11.equals(val22)) {
                            return val11.compareTo(val22);
                        }

                        var val1 = map2.get(entry1.getKey().charAt(0));
                        var val2 = map2.get(entry2.getKey().charAt(0));

                        return val1.compareTo(val2);
                    }).get();

            var current = collect.get(stringLongEntry.getKey());
            collect.put(stringLongEntry.getKey(), current + jokerCount);
        } else if (collect.containsKey("J") && collect.get("J") == 5L) {
            collect = Map.of("A", 5L);
        }

        long multi = switch (collect.size()) {
            case 5 -> 1L;
            case 4 -> 10L;
            case 3 -> collect.values().stream().max(Comparator.naturalOrder()).get() == 3L ? 1_000L : 100L;
            case 2 -> collect.values().stream().sorted().findFirst().get() == 2L ? 10_000L : 100_000L;
            case 1 -> 1_000_000L;
            default -> throw new RuntimeException("Invalid case for card matching: " + collect.size());
        } * 10_000_000_000L;

        long value = 0L;
        long m = 1L;

        for (int i = 4; i >= 0; i--) {
            value += map2.get(cards.charAt(i)) * m;
            m *= 100L;
        }

        return multi + value;
    }
}
