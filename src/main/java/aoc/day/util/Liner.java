package aoc.day.util;

import java.util.List;
import java.util.regex.Pattern;

public class Liner {
    public final String previous;
    public final String current;
    public final String next;
    private static final Pattern NUMBERS = Pattern.compile("\\d+");

    private Liner(String previous, String current, String next) {
        this.previous = previous;
        this.current = current;
        this.next = next;
    }

    public static Liner build(List<String> lines, int index) {
        var previous = index > 0 ? lines.get(index - 1) : null;
        var current = lines.get(index);
        var next = index < lines.size() - 1 ? lines.get(index + 1) : null;

        return new Liner(previous, current, next);
    }

    public boolean checkVertical(Match match) {
        return notDot(match.start - 1) || notDot(match.end);
    }

    public boolean checkHorizontal(Match match) {
        return containsSymbol(previous, match) || containsSymbol(next, match);
    }

    private boolean containsSymbol(String line, Match match) {
        if (line == null) return false;

        var start = match.start + (match.start > 0 ? -1 : 0);
        var end = match.end + (match.end < current.length() - 1 ? 1 : 0);

        return !line.substring(start, end).replace(".", "").isEmpty();
    }

    private boolean notDot(int index) {
        // out of bounds is like a dot
        if (index < 0 || index >= current.length()) return false;

        return !Character.valueOf(current.charAt(index)).equals('.');
    }

    public List<Match> getMatches() {
        return NUMBERS.matcher(current).results()
                .map(m -> new Match(m.start(), m.end(), Integer.parseInt(m.group())))
                .toList();
    }

    public record Match(int start, int end, int value) {}
}
