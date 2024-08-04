package fr.oxyl.genetic.application.showtime;

import fr.oxyl.genetic.api.FitnessCalculator;
import java.time.Duration;
import java.util.LinkedHashSet;

public final class ShowtimePlannerFitnessCalculator implements FitnessCalculator<ShowtimeIndividual> {

  @Override
  public int compute(ShowtimeIndividual individual) {
    int fitness = 0;
    var movies = new LinkedHashSet<Movie>();

    for (int i = 0; i < individual.genome().size() - 1; i++) {
      var current = individual.genome().get(i);
      var next = individual.genome().get(i + 1);

      if (movies.contains(current.movie())) {
        return Integer.MIN_VALUE;
      }

      if (current.time().isAfter(next.time())) {
        return Integer.MIN_VALUE;
      }

      if (next.time().isBefore(current.end())) {
        return Integer.MIN_VALUE;
      }

      fitness += (int) current.movie().duration().toMinutes();
      fitness -= (int) Duration.between(current.end(), next.time()).toMinutes();
      movies.add(current.movie());
    }
    var last = individual.genome().getLast();
    if (movies.contains(last.movie())) {
      return Integer.MIN_VALUE;
    }
    fitness += (int) last.movie().duration().toMinutes();

    return fitness;
  }
}
