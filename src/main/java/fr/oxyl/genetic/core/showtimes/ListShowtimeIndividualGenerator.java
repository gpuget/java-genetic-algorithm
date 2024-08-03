package fr.oxyl.genetic.core.showtimes;

import java.util.List;

public class ListShowtimeIndividualGenerator extends ShowtimeIndividualGenerator {

  private final List<Showtime> showtimes;

  public ListShowtimeIndividualGenerator(List<Showtime> showtimes) {
    this.showtimes = showtimes;
  }

  @Override
  protected List<Showtime> showtimes() {
    return this.showtimes;
  }

}
