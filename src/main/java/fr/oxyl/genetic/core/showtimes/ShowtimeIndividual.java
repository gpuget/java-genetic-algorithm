package fr.oxyl.genetic.core.showtimes;

import fr.oxyl.genetic.api.Individual;
import java.time.Duration;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public record ShowtimeIndividual(List<Showtime> showtimes, int fitness) implements Individual<ShowtimeIndividual> {

  public ShowtimeIndividual(List<Showtime> showtimes) {
    this(showtimes, computeFitness(showtimes));
  }

  private static int computeFitness(List<Showtime> showtimes) {
    int fitness = 0;
    var movies = new LinkedHashSet<Movie>();

    for (int i = 0; i < showtimes.size() - 1; i++) {
      var current = showtimes.get(i);
      var next = showtimes.get(i + 1);

      if (movies.contains(current.movie())) {
        return 0;
      }

      if (!current.time().toLocalDate().isEqual(next.time().toLocalDate())) {
        return 0;
      }

      if (current.time().isAfter(next.time())) {
        return 0;
      }

      if (next.time().isBefore(current.end())) {
        return 0;
      }

      fitness += (int) current.movie().duration().toMinutes();
      fitness -= (int) Duration.between(current.time().plus(current.duration()), next.time()).toMinutes();
      movies.add(current.movie());
      if (i == showtimes.size() - 2) {
        if (movies.contains(next.movie())) {
          return 0;
        }
        fitness += (int) next.movie().duration().toMinutes();
      }
    }

    return fitness;
  }

  @Override
  public List<ShowtimeIndividual> crossover(ShowtimeIndividual other) {
    var random = ThreadLocalRandom.current();
    var showtimes = new LinkedList<Showtime>();
    if (showtimes().isEmpty()) {
      showtimes.addAll(other.showtimes());
    } else {
      int index = random.nextInt(0, showtimes().size());
      var time = showtimes().get(index).time();
      showtimes().stream().filter(showtime -> !showtime.time().isAfter(time)).forEach(showtimes::add);
      other.showtimes().stream().filter(showtime -> showtime.time().isAfter(time)).forEach(showtimes::add);
    }
    return Collections.singletonList(new ShowtimeIndividual(showtimes));
  }

  @Override
  public ShowtimeIndividual mutate() {
    var random = ThreadLocalRandom.current();
    var showtimes = new LinkedList<>(showtimes());
    if (showtimes().size() > 1) {
      int i = random.nextInt(0, showtimes().size());
      int j = random.nextInt(0, showtimes().size());
      Collections.swap(showtimes, i, j);
    }
    return new ShowtimeIndividual(showtimes);
  }

  @Override
  public String toString() {
    return showtimes().stream()
        .map(Showtime::toString)
        .collect(Collectors.joining("\n\t", Duration.ofMinutes(fitness()) + " \n\t", "\n"));
  }

}
