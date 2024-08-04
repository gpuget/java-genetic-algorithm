package fr.oxyl.genetic.application.showtime;

import java.time.Duration;
import java.time.LocalTime;

public record Showtime(LocalTime time, Movie movie) {

  private static final int DURATION_EXTENSION_IN_MINUTES = 15;

  public Duration duration() {
    return movie.duration().plus(Duration.ofMinutes(DURATION_EXTENSION_IN_MINUTES));
  }

  public LocalTime end() {
    return this.time.plus(duration());
  }

  @Override
  public String toString() {
    return "Showtime{" +
           "time=" + time +
           ", end=" + end() +
           ", movie=" + movie +
           '}';
  }
}