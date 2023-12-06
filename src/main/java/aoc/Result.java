package aoc;

public sealed abstract class Result permits Result.Error, Result.Solution {
    private long nanos;

    abstract String message();

    public void setNanos(long nanos) {
        this.nanos = nanos;
    }

    static final class Error extends Result {
        public Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        @Override
        public String message() {
            return "Ended exceptionally " + exception.getCause() + ", " + exception.getMessage();
        }
    }

    static final class Solution extends Result {
        public Object result;

        public Solution(Object result) {
            this.result = result;
        }

        @Override
        public String message() {
            return "Solution is " + result;
        }
    }

    public void printResult() {
        System.out.println(message() + " - Took: " + formatNanos(nanos));
    }

    private String formatNanos(long nanos) {
        return String.format("%d ms %d ns", nanos / 1000000, nanos % 1000000);
    }
}
