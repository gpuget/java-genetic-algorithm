package fr.oxyl.genetic.core.showtimes;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class ShowtimeIndividualTest {

  @Test
  void fitness_noShowtime() {
    var a = new ShowtimeIndividual(List.of());

    assertThat(a.fitness()).isZero();
  }

  @Test
  void fitness_sameMovie() {
    var movie = new Movie("title", Duration.ofMinutes(90));
    var showtime1 = new Showtime(LocalDateTime.now().minusHours(2L), movie);
    var showtime2 = new Showtime(LocalDateTime.now(), movie);
    var a = new ShowtimeIndividual(List.of(showtime1, showtime2));

    assertThat(a.fitness()).isZero();
  }

  @Test
  void fitness_notSameDay() {
    var movie1 = new Movie("title", Duration.ofMinutes(90));
    var movie2 = new Movie("title2", Duration.ofMinutes(90));
    var showtime1 = new Showtime(LocalDateTime.now().minusDays(1L), movie1);
    var showtime2 = new Showtime(LocalDateTime.now(), movie2);
    var a = new ShowtimeIndividual(List.of(showtime1, showtime2));

    assertThat(a.fitness()).isZero();
  }

  @Test
  void fitness_override1() {
    var movie1 = new Movie("title", Duration.ofMinutes(90));
    var movie2 = new Movie("title2", Duration.ofMinutes(90));
    var showtime1 = new Showtime(LocalDateTime.now().plusHours(1L), movie1);
    var showtime2 = new Showtime(LocalDateTime.now(), movie2);
    var a = new ShowtimeIndividual(List.of(showtime1, showtime2));

    assertThat(a.fitness()).isZero();
  }

  @Test
  void fitness_override2() {
    var movie1 = new Movie("title", Duration.ofMinutes(90));
    var movie2 = new Movie("title2", Duration.ofMinutes(90));
    var showtime1 = new Showtime(LocalDateTime.now(), movie1);
    var showtime2 = new Showtime(LocalDateTime.now().plusHours(1L), movie2);
    var a = new ShowtimeIndividual(List.of(showtime1, showtime2));

    assertThat(a.fitness()).isZero();
  }

  @Test
  void fitness() {
    var movie1 = new Movie("title", Duration.ofMinutes(90));
    var movie2 = new Movie("title2", Duration.ofMinutes(90));
    var showtime1 = new Showtime(LocalDateTime.now().minusHours(2L), movie1);
    var showtime2 = new Showtime(LocalDateTime.now(), movie2);
    var a = new ShowtimeIndividual(List.of(showtime1, showtime2));

    assertThat(a.fitness()).isEqualTo(90 - 15 + 90);
  }

}