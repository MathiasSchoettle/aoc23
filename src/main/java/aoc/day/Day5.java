package aoc.day;

import aoc.AbstractSolver;
import aoc.day.util.day5.Ranger;

import java.util.*;
import java.util.regex.Pattern;

public class Day5 extends AbstractSolver {

    @Override
    public Object puzzle1() {
        var inputs = splitByEmptyLine();

        var seeds = Arrays.stream(inputs.get(0).split(" ")).skip(1).map(Long::parseLong).toList();
        var rangers = inputs.stream().skip(1).map(Ranger::fromBlock).toList();

        var minLocation = Long.MAX_VALUE;

        for (var seed : seeds) {
            var temp = seed;

            for (var ranger : rangers) {
                temp = ranger.toLocation(temp);
            }

            minLocation = Math.min(temp, minLocation);
        }

        return minLocation;
    }

    @Override
    public Object puzzle2() {
        var inputs = splitByEmptyLine();
        Pattern pattern = Pattern.compile(" \\d+ \\d+");

        List<Range> ranges = pattern.matcher(inputs.get(0).split(":")[1]).results()
                .map(match -> match.group().trim().split(" "))
                .map(split -> new Range(Long.parseLong(split[0]), Long.parseLong(split[1]))).toList();

        var allRangeMaps = new ArrayList<List<RangeMap>>();
        for (var block : inputs.stream().skip(1).toList()) {
            var rangeMaps = block.lines().skip(1)
                    .map(line -> line.split(" "))
                    .map(arr -> Arrays.stream(arr).mapToLong(Long::parseLong).toArray())
                    .map(arr -> new RangeMap(arr[1], arr[0], arr[2])).toList();

            allRangeMaps.add(rangeMaps);
        }

        for (var mapper : allRangeMaps) {
            ranges = nextRanges(ranges, mapper);
        }

        return ranges.stream().min(Comparator.comparingLong(o -> o.from)).orElseThrow().from;
    }

    private List<Range> nextRanges(List<Range> ranges, List<RangeMap> mappers) {

        var mappedRanges = new ArrayList<Range>();

        for (var mapper : mappers) {
            var tempList = new ArrayList<Range>();

            for (var range : ranges) {

                if (range.to() < mapper.source || range.from > mapper.to()) {
                    tempList.add(range);
                    continue;
                }

                if (mapper.source - range.from > 0) {
                    tempList.add(new Range(range.from, mapper.source - range.from));
                }

                if (range.to() - mapper.to() > 0) {
                    tempList.add(new Range(mapper.to() - 1, range.to() - mapper.to()));
                }

                var upper = Math.max(range.from, mapper.source) - mapper.source + mapper.destination;
                var lower = Math.min(range.to(), mapper.to()) - mapper.source + mapper.destination;
                mappedRanges.add(new Range(upper, lower - upper));
            }

            ranges = tempList;
        }

        mappedRanges.addAll(ranges);
        return mappedRanges;
    }

    private record RangeMap(long source, long destination, long count) {
        public long to() {return source + count;}
    }

    private record Range(long from, long count) {
        public long to() {return from + count;}
    }
}
