package aoc.day;

import aoc.AbstractSolver;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Day7 extends AbstractSolver {
    private static final List<String> MAPPINGS = List.of("11111", "2111", "221", "311", "32", "41", "5");

    @Override
    public Object puzzle1() {
        var mapped = lines().stream().map(line -> line.split(" "))
                .collect(Collectors.toMap(arr -> arr[0], arr -> Long.parseLong(arr[1])))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> strength(entry.getKey()),
                        Map.Entry::getValue,
                        (k1, k2) -> k1,
                        TreeMap::new
                ));

        var i = new AtomicLong(1);
        return mapped.values().stream().reduce(0L, (v1, v2) -> v1 + i.getAndIncrement() * v2);
    }

    private long strength(String card) {
        // Ace (A) is also valid hex
        var hex = card.replace("A", "E")
                      .replace("K", "D")
                      .replace("Q", "C")
                      .replace("J", "B")
                      .replace("T", "A");

        var handType = Arrays.stream(hex.split(""))
                .collect(groupingBy(Function.identity(), Collectors.toList()))
                .values().stream()
                .map(List::size)
                .sorted(Comparator.reverseOrder())
                .map(String::valueOf)
                .collect(Collectors.joining());

        return Long.parseLong(MAPPINGS.indexOf(handType) + hex, 16);
    }

    @Override
    public Object puzzle2() {
        var mapped = lines().stream().map(line -> line.split(" "))
                .collect(Collectors.toMap(arr -> arr[0], arr -> Long.parseLong(arr[1])))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> strength2(entry.getKey()),
                        Map.Entry::getValue,
                        (k1, k2) -> k1,
                        TreeMap::new
                ));

        var counter = new AtomicLong(1);
        return mapped.values().stream().reduce(0L, (v1, v2) -> v1 + counter.getAndIncrement() * v2);
    }

    private long strength2(String card) {
        card = card.replace("A", "D")
                   .replace("K", "C")
                   .replace("Q", "B")
                   .replace("T", "A");

        String toReplace = Arrays.stream(card.replace("J", "").split(""))
                .collect(collectingAndThen(groupingBy(Function.identity(), counting()), map -> map.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(map.values().stream().max(Long::compareTo).orElse(0L)))
                        .map(Map.Entry::getKey)
                        .max(Comparator.comparingLong(o -> Long.parseLong(o, 16)))))
                .filter(value -> !value.isBlank())
                .orElse("A");

        String handType = Arrays.stream(card.replace("J", toReplace).split(""))
                .collect(groupingBy(Function.identity(), toList()))
                .values().stream()
                .map(List::size)
                .sorted(Comparator.reverseOrder())
                .map(String::valueOf)
                .collect(joining());

        return Long.parseLong(MAPPINGS.indexOf(handType) + card.replace("J", "1"), 16);
    }
}
