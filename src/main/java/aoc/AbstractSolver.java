package aoc;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.List;

public abstract class AbstractSolver {
    private final int day;
    private static final String YEAR = "2023";
    private static final String SESSION_COOKIE;

    static {
        SESSION_COOKIE = System.getenv("SESSION_COOKIE");
    }

    public AbstractSolver(int day) {
        this.day = day;
        persistInput();
    }

    public abstract Object puzzle1() throws Exception;

    public abstract Object puzzle2() throws Exception;

    @SuppressWarnings("CallToPrintStackTrace")
    public void solve() {
        try {
            System.out.println("Solution for puzzle1: " + puzzle1());
        } catch (Exception e) {
            System.out.println("Error in calculation for solution 1:");
            e.printStackTrace();
        }

        try {
            System.out.println("Solution for puzzle2: " + puzzle2());
        } catch (Exception e) {
            System.out.println("Error in calculation for solution 2:");
            e.printStackTrace();
        }
    }

    public List<String> getLines() {
        try {
            return Files.readAllLines(getInputFile().toPath());
        } catch (Exception e) {
            throw new RuntimeException("Error while reading input of " + day + " - " + e.getCause());
        }
    }

    private void persistInput() {

        if (getInputFile().exists()) return;

        try {
            Files.createFile(getInputFile().toPath());
            Files.writeString(getInputFile().toPath(), getInput());
        } catch (Exception e) {
            throw new RuntimeException("Error while persisting input of " + day + " - " + e.getCause());
        }
    }

    private String getInput() {
        var urlString = String.format("https://adventofcode.com/%s/day/%s/input", YEAR, day);

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
        return new File((System.getProperty("java.io.tmpdir") + File.separator + day));
    }
}
