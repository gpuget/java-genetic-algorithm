package fr.oxyl.genetic.application.showtime;

import fr.oxyl.genetic.core.ListIndividual;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ShowtimeIndividual extends ListIndividual<Showtime> {

  protected ShowtimeIndividual(List<Showtime> genome) {
    super(genome);
  }

  public List<Showtime> showtimes() {
    return genome();
  }

  @Override
  public String toString() {
    return showtimes().stream()
        .map(Showtime::toString)
        .collect(Collectors.joining("\n\t", fitness() + " " + Duration.ofMinutes(fitness()) + " \n\t", "\n"));
  }

}
