package aoc.day.util.day10;

public record Point(int x, int y) {
    public Point up() {
        return new Point(x, y - 1);
    }
    public Point down() {
        return new Point(x, y + 1);
    }
    public Point left() {
        return new Point(x - 1, y);
    }
    public Point right() {
        return new Point(x + 1, y);
    }
}
