package aoc.day.util.day8;

import java.util.List;

public class LCM {
    public static long lcm(List<Integer> numbers) {
        if (numbers.size() == 2) return lcm(numbers.getFirst(), numbers.getLast());
        return lcm(numbers.getFirst(), lcm(numbers.stream().skip(1).toList()));
    }

    private static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    public static long gcd(long a, long b) {
        return a % b == 0 ? b : gcd(b, a % b);
    }
}
