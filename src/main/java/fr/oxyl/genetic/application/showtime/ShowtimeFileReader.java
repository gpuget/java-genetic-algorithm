package fr.oxyl.genetic.application.showtime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface ShowtimeFileReader {

  static List<Showtime> read(String filename) {
    try {
      var path = Paths.get("src", "main", "resources", filename);
      var data = Files.readString(path);
      return createShowtimesFromString(data);
    } catch (IOException e) {
      System.err.println("Impossible to read the file: " + filename + " - " + e.getMessage());
      return Collections.emptyList();
    }
  }

  private static List<Showtime> createShowtimesFromString(String data) {
    return Arrays.stream(data.split("(\r?\n){2}"))
        .filter(movieBlock -> !movieBlock.startsWith("#"))
        .flatMap(movieBlock -> {
          var lines = movieBlock.split("\r?\n");
          var movie = parseMovie(lines);
          return parseShowtimes(lines, movie);
        })
        .toList();
  }

  private static Movie parseMovie(String[] lines) {
    var durationParts = lines[1].replaceAll(".+\\((.+)\\)", "$1").split("[:h]");
    var hours = Integer.parseInt(durationParts[0]);
    var minutes = Integer.parseInt(durationParts[1]);
    var duration = Duration.ofMinutes((60L * hours) + minutes);
    return new Movie(lines[0], duration);
  }

  private static Stream<Showtime> parseShowtimes(String[] lines, Movie movie) {
    return IntStream.range(2, lines.length)
        .mapToObj(i -> lines[i])
        .filter(timeData -> !timeData.startsWith("#"))
        .map(timeData -> new Showtime(LocalTime.parse(timeData), movie));
  }

}
