package aoc.day;

import aoc.AbstractSolver;
import aoc.day.util.day10.Point;

import java.util.*;

public class Day10 extends AbstractSolver {

    private static final List<Character> LEFT = List.of('-', 'J', '7', 'S');
    private static final List<Character> RIGHT = List.of('-', 'F', 'L', 'S');
    private static final List<Character> UP = List.of('|', 'L', 'J', 'S');
    private static final List<Character> DOWN = List.of('|', 'F', '7', 'S');

    private static final Map<Character, List<Direction>> DIRECTIONMAP = Map.of(
            '|', List.of(Direction.DOWN, Direction.UP),
            '-', List.of(Direction.LEFT, Direction.RIGHT),
            'L', List.of(Direction.UP, Direction.RIGHT),
            'J', List.of(Direction.UP, Direction.LEFT),
            '7', List.of(Direction.DOWN, Direction.LEFT),
            'F', List.of(Direction.DOWN, Direction.RIGHT),
            'S', Arrays.stream(Direction.values()).toList()
    );

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    @Override
    public Object puzzle1() {
        var lines = lines();

        var tiles = new HashMap<Point, Character>();
        var width = lines.size();
        var height = lines.getFirst().length();

        for (int y = 0; y < height; y++) {
            var line = lines.get(y);
            for (int x = 0; x < width; x++) {
                tiles.put(new Point(x, y), line.charAt(x));
            }
        }

        var start = tiles.entrySet().stream()
                .filter(entry -> entry.getValue().equals('S'))
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();

        List<Entry> nextItems = new ArrayList<>();

        // up
        if (DOWN.contains(tiles.get(start.up()))) {
            nextItems.add(new Entry(start.up(), tiles.get(start.up()), Direction.DOWN));
        }

        // down
        if (UP.contains(tiles.get(start.down()))) {
            nextItems.add(new Entry(start.down(), tiles.get(start.down()), Direction.UP));
        }

        // left
        if (RIGHT.contains(tiles.get(start.left()))) {
            nextItems.add(new Entry(start.left(), tiles.get(start.left()), Direction.RIGHT));
        }

        // right
        if (LEFT.contains(tiles.get(start.right()))) {
            nextItems.add(new Entry(start.right(), tiles.get(start.right()), Direction.LEFT));
        }

        int count = 1;

        while (true) {
            var first = nextItems.getFirst();
            var second = nextItems.getLast();

            if (first.point.equals(second.point)) break;

            var next1 = first.next();
            var next2 = second.next();


            var nextEntry1 = new Entry(next1.first, tiles.get(next1.first), next1.second);
            var nextEntry2 = new Entry(next2.first, tiles.get(next2.first), next2.second);

            if (first.equals(nextEntry2)) {
                break;
            }

            nextItems.clear();
            nextItems.add(nextEntry1);
            nextItems.add(nextEntry2);

            count++;
        }

        return count;
    }

    record Entry(Point point, Character symbol, Direction direction) {
        public Tuple next() {
            var nextDirection = DIRECTIONMAP.get(symbol).stream().filter(d -> !d.equals(direction)).findFirst().orElseThrow();
            return switch (nextDirection) {
                case UP ->      new Tuple(point.up(), Direction.DOWN);
                case DOWN ->    new Tuple(point.down(), Direction.UP);
                case LEFT ->    new Tuple(point.left(), Direction.RIGHT);
                case RIGHT ->   new Tuple(point.right(), Direction.LEFT);
            };
        }
    }

    record Tuple(Point first, Direction second) {}

    @Override
    public Object puzzle2() {
        return null;
    }
}
