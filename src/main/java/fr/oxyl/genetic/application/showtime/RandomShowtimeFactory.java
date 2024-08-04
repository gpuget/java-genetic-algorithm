package fr.oxyl.genetic.application.showtime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public interface RandomShowtimeFactory {

  static List<Showtime> createShowtimes() {
    var random = ThreadLocalRandom.current();
    return Stream.generate(() -> new Movie("Movie " + random.nextInt(0, 10000),
            Duration.of(random.nextInt(90, 180), ChronoUnit.MINUTES)))
        .limit(10)
        .flatMap(movie -> Stream.generate(() -> createRandomShowtime(movie)).limit(10))
        .toList();
  }

  private static Showtime createRandomShowtime(Movie movie) {
    var random = ThreadLocalRandom.current();
    var now = LocalDate.now();
    int hour = random.nextInt(10, 24);
    int minute = random.nextInt(0, 6) * 10;
    var date = LocalDateTime.of(now, LocalTime.of(hour, minute));
    return new Showtime(date, movie);
  }

}
