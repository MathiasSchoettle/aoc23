package aoc.day.util.day5;

import java.util.Arrays;
import java.util.List;

public class Ranger {
    private final List<Range> ranges;

    private Ranger(List<Range> ranges) {
        this.ranges = ranges;
    }

    public static Ranger fromBlock(String block) {
        var ranges = Arrays.stream(block.split("\n")).skip(1)
                .map(Range::fromString)
                .toList();

        return new Ranger(ranges);
    }

    public long toLocation(long value) {
        return ranges.stream()
                .filter(range -> range.contains(value))
                .map(range -> range.toDestination(value))
                .findFirst()
                .orElse(value);
    }

    private static class Range {
        private final long source;
        private final long destination;
        private final long count;

        private Range(List<Long> values) {
            this.source = values.get(1);
            this.destination = values.get(0);
            this.count = values.get(2);
        }

        public static Range fromString(String string) {
            return new Range(Arrays.stream(string.split(" ")).map(Long::parseLong).toList());
        }

        public boolean contains(long value) {
            return value >= source && value <= source + count;
        }

        public long toDestination(long value) {
            return destination + value - source;
        }
    }
}
