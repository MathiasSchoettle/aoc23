package aoc;

public sealed abstract class Result permits Result.Error, Result.Solution {
    private long nanos;

    abstract String message();

    public void setNanos(long nanos) {
        this.nanos = nanos;
    }

    static final class Error extends Result {
        private final Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        @Override
        public String message() {
            return "Ended exceptionally " + exception.getCause() + ", " + exception.getMessage();
        }
    }

    static final class Solution extends Result {
        private final Object result;
        private final int day;

        public Solution(Object result, int day) {
            this.result = result;
            this.day = day;
        }

        @Override
        public String message() {
            return "Solution for " + day + " is " + result;
        }
    }

    public void printResult() {
        System.out.println(message() + " - Took: " + formatNanos(nanos));
    }

    private String formatNanos(long nanos) {
        return String.format("%d ms %d ns", nanos / 1000000, nanos % 1000000);
    }
}
