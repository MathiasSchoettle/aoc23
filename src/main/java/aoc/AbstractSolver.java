package aoc;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractSolver {
    private final int day;
    private String input;
    private final String year;
    private static final String SESSION_COOKIE;

    static {
        SESSION_COOKIE = System.getenv("SESSION_COOKIE");
    }

    public AbstractSolver() {
        String number = this.getClass().getSimpleName().replaceAll("\\D", "");
        this.day = Integer.parseInt(number);
        this.year = "2023";
        persistInput();
        this.input = readFile();
    }

    public abstract Object puzzle1() throws Exception;

    public abstract Object puzzle2() throws Exception;

    public void solve() {
        measure(this::solve1).printResult();
        measure(this::solve2).printResult();
    }

    private Result measure(Supplier<Result> method) {
        var start = System.nanoTime();
        var result = method.get();
        var end = System.nanoTime() - start;

        result.setNanos(end);
        return result;
    }

    private Result solve1() {
        try {
            return new Result.Solution(puzzle1(), day);
        } catch (Exception e) {
            return new Result.Error(e);
        }
    }

    private Result solve2() {
        try {
            return new Result.Solution(puzzle2(), day);
        } catch (Exception e) {
            return new Result.Error(e);
        }
    }

    public List<String> lines() {
        return lines(input);
    }

    public List<String> lines(String input) {
        return Arrays.stream(input.split("\n")).toList();
    }

    public List<String> splitByEmptyLine() {
        return splitByEmptyLine(input);
    }

    public List<String> splitByEmptyLine(String input) {
        return Arrays.stream(input.split("\n\n")).toList();
    }

    private String readFile() {
        try {
            return Files.readString(getInputFile().toPath());
        } catch (Exception e) {
            throw new RuntimeException("Error while reading input of " + day + " - " + e.getCause());
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void persistInput() {

        if (getInputFile().exists()) return;

        try {
            getInputFile().getParentFile().mkdirs();
            Files.createFile(getInputFile().toPath());
            Files.writeString(getInputFile().toPath(), getInput());
        } catch (Exception e) {
            throw new RuntimeException("Error while persisting input of " + day + " - " + e.getCause());
        }
    }

    public void reload() {
        try {
            Files.deleteIfExists(getInputFile().toPath());
        } catch (Exception e) {
            throw new RuntimeException("Error while reloading input of " + day + " - " + e.getCause());
        }
        persistInput();
        this.input = readFile();
    }

    private String getInput() {
        var urlString = String.format("https://adventofcode.com/%s/day/%s/input", year, day);

        try (var client = HttpClient.newHttpClient()) {

            System.out.println("fetching data for day " + day + "...");

            var request = HttpRequest.newBuilder()
                    .uri(new URI(urlString))
                    .header("Cookie", SESSION_COOKIE)
                    .GET()
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching data of " + day + " - " + e.getCause());
        }
    }

    private File getInputFile() {
        return new File(System.getProperty("java.io.tmpdir") + File.separator + "aoc" + File.separator + day);
    }
}
