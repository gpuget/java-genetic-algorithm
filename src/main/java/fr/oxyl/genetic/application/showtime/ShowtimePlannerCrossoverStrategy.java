package fr.oxyl.genetic.application.showtime;

import fr.oxyl.genetic.api.CrossoverStrategy;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class ShowtimePlannerCrossoverStrategy implements CrossoverStrategy<ShowtimeIndividual> {

  @Override
  public Collection<ShowtimeIndividual> mate(ShowtimeIndividual parent1, ShowtimeIndividual parent2) {
    var random = ThreadLocalRandom.current();
    var showtimes = new LinkedList<Showtime>();
    if (parent1.showtimes().isEmpty()) {
      showtimes.addAll(parent2.showtimes());
    } else {
      int index = random.nextInt(0, parent1.showtimes().size());
      var time = parent1.showtimes().get(index).time();
      parent1.showtimes().stream().filter(showtime -> !showtime.time().isAfter(time)).forEach(showtimes::add);
      parent2.showtimes().stream().filter(showtime -> showtime.time().isAfter(time)).forEach(showtimes::add);
    }
    return Collections.singletonList(new ShowtimeIndividual(showtimes));
  }
}
