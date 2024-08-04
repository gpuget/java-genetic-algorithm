package fr.oxyl.genetic.application.showtime;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public interface ShowtimeFactory {

  static List<Showtime> createRandomShowtimes() {
    var random = ThreadLocalRandom.current();
    return Stream.generate(() -> new Movie("Movie " + random.nextInt(0, 10000),
            Duration.of(random.nextInt(90, 180), ChronoUnit.MINUTES)))
        .limit(10)
        .flatMap(movie -> Stream.generate(() -> createRandomShowtime(movie)).limit(10))
        .toList();
  }

  private static Showtime createRandomShowtime(Movie movie) {
    var random = ThreadLocalRandom.current();
    int hour = random.nextInt(10, 24);
    int minute = random.nextInt(0, 6) * 10;
    var time = LocalTime.of(hour, minute);
    return new Showtime(time, movie);
  }

}
