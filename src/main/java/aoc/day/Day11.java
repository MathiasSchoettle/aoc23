package aoc.day;

import aoc.AbstractSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day11 extends AbstractSolver {

    @Override
    public Object puzzle1() {
        return getDistances(lines(), 2);
    }

    @Override
    public Object puzzle2() {
        return getDistances(lines(), 1_000_000);
    }

    private long getDistances(List<String> lines, long additional) {
        List<Long> yValues = new ArrayList<>();
        List<Long> xValues = new ArrayList<>();

        long count = 0L;

        for (var line : lines) {
            count += line.matches("^\\.+$") ? additional : 1;
            yValues.add(count);
        }

        count = 0L;

        for (int i = 0; i < lines.getFirst().length(); i++) {
            final int index = i;
            count += lines.stream().allMatch(l -> l.charAt(index) == '.') ? additional : 1;
            xValues.add(count);
        }

        var points = new ArrayList<Point>();
        var pattern = Pattern.compile("#");

        for (int i = 0; i < lines.size(); i++) {
            final int y = i;
            var line = lines.get(i);
            var matcher = pattern.matcher(line);
            matcher.results().map(MatchResult::start).forEach(x -> points.add(new Point(xValues.get(x), yValues.get(y))));
        }

        long sum = 0;

        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < i; j++) {
                sum += points.get(i).distance(points.get(j));
            }
        }

        return sum;
    }

    record Point(long x, long y) {
        public long distance(Point other) {
            return Math.abs(other.x - x) + Math.abs(other.y - y);
        }
    }
}
