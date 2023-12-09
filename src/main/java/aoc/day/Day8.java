package aoc.day;

import aoc.AbstractSolver;

import java.util.*;

import static aoc.day.util.day8.LCM.lcm;
import static java.util.stream.Collectors.toMap;

public class Day8 extends AbstractSolver {

    @Override
    public Object puzzle1() {
        var blocks = splitByEmptyLine();
        var instructions = Arrays.stream(blocks.getFirst().split("")).toList();
        var nodes = Arrays.stream(blocks.get(1).split("\n"))
                .collect(toMap(string -> string.substring(0, 3), string -> new Node(string.substring(7, 10), string.substring(12,15))));

        var current = "AAA";
        int count = 0;

        while (!current.equals("ZZZ")) {
            var instruction = instructions.get(count++ % instructions.size());
            current = nodes.get(current).next(instruction);
        }

        return count;
    }

    @Override
    public Object puzzle2() {
        var blocks = splitByEmptyLine();
        var instructions = Arrays.stream(blocks.getFirst().split("")).toList();
        var nodes = Arrays.stream(blocks.get(1).split("\n"))
                .collect(toMap(string -> string.substring(0, 3), string -> new Node(string.substring(7, 10), string.substring(12,15))));

        var counters = new ArrayList<Integer>();

        for (var node : nodes.entrySet().stream().filter(entry -> entry.getKey().endsWith("A")).toList()) {
            int count = 0;
            int instructionPointer = 0;
            var current = node.getKey();

            while (!current.endsWith("Z")) {
                var instruction = instructions.get(instructionPointer++);
                if (instructionPointer >= instructions.size()) instructionPointer = 0;

                current = nodes.get(current).next(instruction);
                count++;
            }

            counters.add(count);
        }

        return lcm(counters);
    }

    record Node(String left, String right) {
        public String next(String instruction) {
            return instruction.equals("L") ? left : right;
        }
    }
}
