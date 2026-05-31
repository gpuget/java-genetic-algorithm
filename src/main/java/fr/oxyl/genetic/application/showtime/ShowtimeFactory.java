package fr.oxyl.genetic.application.showtime;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public interface ShowtimeFactory {

  int MOVIE_POOL_SIZE = 10;
  int SHOWTIMES_PER_MOVIE = 10;
  int MOVIE_TITLE_ID_BOUND = 10_000;
  int MIN_MOVIE_DURATION_MINUTES = 90;
  int MAX_MOVIE_DURATION_MINUTES = 180;
  int FIRST_SHOWTIME_HOUR = 10;
  int LAST_SHOWTIME_HOUR = 24;
  int MINUTE_STEPS_COUNT = 6;
  int MINUTE_STEP_MINUTES = 10;

  static List<Showtime> createRandomShowtimes() {
    var random = ThreadLocalRandom.current();
    return Stream.generate(() -> new Movie("Movie " + random.nextInt(0, MOVIE_TITLE_ID_BOUND),
            Duration.of(random.nextInt(MIN_MOVIE_DURATION_MINUTES, MAX_MOVIE_DURATION_MINUTES), ChronoUnit.MINUTES)))
        .limit(MOVIE_POOL_SIZE)
        .flatMap(movie -> Stream.generate(() -> createRandomShowtime(movie)).limit(SHOWTIMES_PER_MOVIE))
        .toList();
  }

  private static Showtime createRandomShowtime(Movie movie) {
    var random = ThreadLocalRandom.current();
    int hour = random.nextInt(FIRST_SHOWTIME_HOUR, LAST_SHOWTIME_HOUR);
    int minute = random.nextInt(0, MINUTE_STEPS_COUNT) * MINUTE_STEP_MINUTES;
    var time = LocalTime.of(hour, minute);
    return new Showtime(time, movie);
  }

}
