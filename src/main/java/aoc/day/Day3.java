package aoc.day;

import aoc.AbstractSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Day3 extends AbstractSolver {
    private static final Pattern NUMBERS = Pattern.compile("\\d+");
    public static final Pattern STAR = Pattern.compile("\\*");

    public Day3() {
        super(3);
    }

    @Override
    public Object puzzle1() {
        var sum = 0;

        for (int i = 0; i < getLines().size(); i++) {
            var liner = Liner.build(getLines(), i);

            sum += liner.getMatches().stream()
                    .filter(match -> liner.checkHorizontal(match) || liner.checkVertical(match))
                    .mapToInt(Liner.Match::value)
                    .sum();
        }

        return sum;
    }

    @Override
    public Object puzzle2() {
        var lines = getLines();
        var map = new HashMap<Point, List<Integer>>();

        for (int i = 0; i < lines.size(); i++) {
            var liner = Liner.build(lines, i);
            var matcher = NUMBERS.matcher(liner.current);

            while (matcher.find()) {

                var start = matcher.start();
                var end = matcher.end() - 1;
                var value = Integer.parseInt(matcher.group());

                if (start > 0 && liner.current.charAt(start - 1) == '*') {
                    map.computeIfAbsent(new Point(start - 1, i), point -> new ArrayList<>()).add(value);
                }

                if (end < liner.current.length() - 1 && liner.current.charAt(end + 1) == '*') {
                    map.computeIfAbsent(new Point(end + 1, i), point -> new ArrayList<>()).add(value);
                }

                var substringStart = start > 0 ? start - 1 : start;
                var substringEnd = end < liner.current.length() - 1 ? end + 1 : end;

                if (liner.previous != null) {
                    var beforeMatcher = STAR.matcher(liner.previous.substring(substringStart, substringEnd + 1));
                    while (beforeMatcher.find()) {
                        map.computeIfAbsent(new Point(beforeMatcher.start() + substringStart, i - 1), point -> new ArrayList<>()).add(value);
                    }
                }

                if (liner.next != null) {
                    var afterMatcher = STAR.matcher(liner.next.substring(substringStart, substringEnd + 1));
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
