package fr.oxyl.genetic.application.showtime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public interface ShowtimeFileReader {

  static List<Showtime> read(String filename) {
    try {
      var path = Paths.get("src", "main", "resources", filename);
      var data = Files.readString(path);
      return createShowtimesFromString(data);
    } catch (IOException e) {
      System.err.println("Impossible to read the file: " + filename);
      return Collections.emptyList();
    }
  }

  private static List<Showtime> createShowtimesFromString(String data) {
    return Arrays.stream(data.split("(\r?\n){2}"))
        .flatMap(movieData -> {
          var d = movieData.split("\r?\n");
          var title = d[0];
          var durationData = d[1].split(":");
          var hour = Integer.parseInt(durationData[0]);
          var minute = Integer.parseInt(durationData[1]);
          var duration = Duration.ofMinutes((60L * hour) + minute);
          var movie = new Movie(title, duration);
          return IntStream.range(2, d.length)
              .mapToObj(i -> new Showtime(LocalTime.parse(d[i]), movie));
        })
        .toList();
  }

}
