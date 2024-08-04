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
        return 0;
      }

      if (current.time().isAfter(next.time())) {
        return 0;
      }

      if (next.time().isBefore(current.end())) {
        return 0;
      }

      fitness += (int) current.movie().duration().toMinutes();
      fitness -= (int) Duration.between(current.end(), next.time()).toMinutes();
      movies.add(current.movie());
      if (i == individual.genome().size() - 2) {
        if (movies.contains(next.movie())) {
          return 0;
        }
        fitness += (int) next.movie().duration().toMinutes();
      }
    }

    return fitness;
  }
}
