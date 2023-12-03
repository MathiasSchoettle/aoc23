package aoc.day;

import aoc.AbstractSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Day3 extends AbstractSolver {
    public Day3() {
        super(3);
    }

    @Override
    public Object puzzle1() {

        var pattern = Pattern.compile("\\d+");
        var lines = getLines();
        var sum = 0;

        for (int i = 0; i < lines.size(); i++) {
            var before = i > 0 ? lines.get(i - 1) : null;
            var current = lines.get(i);
            var after = i < lines.size() - 1 ? lines.get(i + 1) : null;
            var matcher = pattern.matcher(current);

            while (matcher.find()) {

                var start = matcher.start();
                var end = matcher.end() - 1;
                boolean isPartNumber = false;

                if (start > 0) {
                    Character c = current.charAt(start - 1);
                    if (!c.equals('.')) isPartNumber = true;
                }

                if (end < current.length() - 1) {
                    Character c = current.charAt(end + 1);
                    if (!c.equals('.')) isPartNumber = true;
                }

                var substringStart = start > 0 ? start - 1 : start;
                var substringEnd = end < current.length() - 1 ? end + 1 : end;

                if (before != null) {
                    var substring = before.substring(substringStart, substringEnd + 1).replace(".", "");
                    if (!substring.isEmpty()) isPartNumber = true;
                }

                if (after != null) {
                    var substring = after.substring(substringStart, substringEnd + 1).replace(".", "");
                    if (!substring.isEmpty()) isPartNumber = true;
                }

                if (isPartNumber) {
                    sum += Integer.parseInt(matcher.group());
                }
            }
        }

        return sum;
    }

    @Override
    public Object puzzle2() {
        var pattern = Pattern.compile("\\d+");
        var starPattern = Pattern.compile("\\*");
        var lines = getLines();
        var map = new HashMap<Point, List<Integer>>();

        for (int i = 0; i < lines.size(); i++) {
            var before = i > 0 ? lines.get(i - 1) : null;
            var current = lines.get(i);
            var after = i < lines.size() - 1 ? lines.get(i + 1) : null;
            var matcher = pattern.matcher(current);

            while (matcher.find()) {

                var start = matcher.start();
                var end = matcher.end() - 1;
                var value = Integer.parseInt(matcher.group());

                if (start > 0 && current.charAt(start - 1) == '*') {
                    map.computeIfAbsent(new Point(start - 1, i), point -> new ArrayList<>()).add(value);
                }

                if (end < current.length() - 1 && current.charAt(end + 1) == '*') {
                    map.computeIfAbsent(new Point(end + 1, i), point -> new ArrayList<>()).add(value);
                }

                var substringStart = start > 0 ? start - 1 : start;
                var substringEnd = end < current.length() - 1 ? end + 1 : end;

                if (before != null) {
                    var beforeMatcher = starPattern.matcher(before.substring(substringStart, substringEnd + 1));
                    while (beforeMatcher.find()) {
                        map.computeIfAbsent(new Point(beforeMatcher.start() + substringStart, i - 1), point -> new ArrayList<>()).add(value);
                    }
                }

                if (after != null) {
                    var afterMatcher = starPattern.matcher(after.substring(substringStart, substringEnd + 1));
                    while (afterMatcher.find()) {
                        map.computeIfAbsent(new Point(afterMatcher.start() + substringStart, i + 1), point -> new ArrayList<>()).add(value);
                    }
                }
            }
        }

        return map.values().stream()
                .filter(values -> values.size() == 2)
                .mapToInt(values -> values.get(0) * values.get(1))
                .sum();
    }

    public record Point(int x, int y){}
}
