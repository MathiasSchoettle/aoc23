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
    public Object puzzle2() throws Exception {
        return null;
    }
}
