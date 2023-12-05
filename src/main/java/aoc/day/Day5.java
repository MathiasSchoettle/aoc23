package aoc.day;

import aoc.AbstractSolver;
import aoc.day.util.day5.Ranger;

import java.util.*;

public class Day5 extends AbstractSolver {

    public Day5() {
        super(5);
    }

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

        var seedRanges = Arrays.stream(inputs.get(0).split(" ")).skip(1).map(Long::parseLong).toList();
        List<Range> ranges = new ArrayList<>();

        for (int i = 0; i < seedRanges.size(); i += 2) {
            ranges.add(new Range(seedRanges.get(i), seedRanges.get(i + 1)));
        }

        List<List<Mapper>> mappers = inputs.stream().skip(1).map(block ->
                Arrays.stream(block.split("\n"))
                        .skip(1)
                        .map(line -> {
                            var arr = line.split(" ");
                            return new Mapper(Long.parseLong(arr[1]), Long.parseLong(arr[0]), Long.parseLong(arr[2]));
                        }).toList()).toList();

        for (var mapper : mappers) {
            ranges = nextRanges(ranges, mapper);
        }

        return ranges.stream().min(Comparator.comparingLong(o -> o.from)).orElseThrow().from;
    }


    private List<Range> nextRanges(List<Range> ranges, List<Mapper> mappers) {

        // list of ranges which where in range of mappers
        // store separately and merge with ranges at end to prevent subsequent mappers matching witch an already
        // mapped region
        var mappedRanges = new ArrayList<Range>();

        for (var mapper : mappers) {

            var tempList = new ArrayList<Range>();

            for (var range : ranges) {

                if (range.to() < mapper.source || range.from > mapper.to()) {
                    // completely outside of mapper range
                    tempList.add(range);
                } else {
                    if (mapper.source - range.from > 0) {
                        // left side of input range is outside of mapper range
                        // should be handled by following mappers
                        tempList.add(new Range(range.from, mapper.source - range.from));
                    }

                    if (range.to() - mapper.to() > 0) {
                        // right side of input range is outside of mapper range
                        // should be handled by following mappers
                        tempList.add(new Range(mapper.to() - 1, range.to() - mapper.to()));
                    }

                    // if we are here we always have a part of the input range inside the mapper range
                    // this is the part we need to transform and exclude from following mapper checks
                    var upper = Math.max(range.from, mapper.source) - mapper.source + mapper.destination;
                    var lower = Math.min(range.to(), mapper.to()) - mapper.source + mapper.destination;
                    mappedRanges.add(new Range(upper, lower - upper));
                }
            }

            ranges = tempList;
        }

        mappedRanges.addAll(ranges);
        return mappedRanges;
    }

    private record Mapper(long source, long destination, long count) {
        public long to() {return source + count;}
    }

    private record Range(long from, long count) {
        public long to() {return from + count;}
    }
}
