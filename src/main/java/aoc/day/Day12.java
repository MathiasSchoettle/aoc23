package aoc.day;

import aoc.AbstractSolver;

import java.util.*;

public class Day12 extends AbstractSolver {

    @Override
    public Object puzzle1() {
        var lines = lines();
        var sum = 0L;

        for (var line : lines) {
            sum += forLine(line, 1);
        }

        return sum;
    }

    @Override
    public Object puzzle2() {
        var lines = lines();
        var sum = 0L;

        for (var line : lines) {
            sum += forLine(line, 5);
        }

        return sum;
    }

    private Long forLine(String line, int multiplier) {
        var split = line.split(" ");
        var input = String.join("?", Collections.nCopies(multiplier, split[0]));
        var values = Arrays.stream(String.join(",", Collections.nCopies(multiplier, split[1])).split(",")).map(Long::parseLong).toList();
        var map = new HashMap<Key, Long>();

        return score(input, values, 0, 0, 0, map);
    }

    private Long score(String input, List<Long> values, int index, int blockIndex, int length, HashMap<Key, Long> map) {
        var key = new Key(index, blockIndex, length);

        if (map.containsKey(key)) return map.get(key);

        if (index == input.length()) {
            if (blockIndex == values.size() && length == 0) return 1L;
            else if (blockIndex == values.size() - 1 && values.get(blockIndex) == length) return 1L;
            else return 0L;
        }

        var result = 0L;
        var symbol = input.charAt(index);

        if (symbol == '.' || symbol == '?') {
            if (length == 0) {
                result += score(input, values, index + 1, blockIndex, 0, map);
            }
            else if (length > 0 && blockIndex < values.size() && values.get(blockIndex) == length) {
                result += score(input, values, index + 1, blockIndex + 1, 0, map);
            }
        }

        if (symbol == '#' || symbol == '?') {
            result += score(input, values, index + 1, blockIndex, length + 1, map);
        }

        map.put(key, result);
        return result;
    }

    record Key(int index, int blockIndex, int length) {}
}
