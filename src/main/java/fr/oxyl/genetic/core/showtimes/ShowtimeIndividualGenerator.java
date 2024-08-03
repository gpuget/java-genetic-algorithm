package fr.oxyl.genetic.core.showtimes;

import fr.oxyl.genetic.api.PopulationGenerator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class ShowtimeIndividualGenerator implements PopulationGenerator<ShowtimeIndividual> {

  private static final int MAX_SHOWTIMES_PER_INDIVIDUAL = 10;

  private ShowtimeIndividual createRandomShowtimeIndividual(List<Showtime> showtimes) {
    var random = ThreadLocalRandom.current();
    return new ShowtimeIndividual(Stream.generate(() -> showtimes.get(random.nextInt(0, showtimes.size())))
        .limit(random.nextInt(1, MAX_SHOWTIMES_PER_INDIVIDUAL))
        .toList());
  }

  @Override
  public List<ShowtimeIndividual> generate(int size) {
    var showtimes = showtimes();
    return IntStream.range(0, size)
        .mapToObj(i -> createRandomShowtimeIndividual(showtimes))
        .toList();
  }

  protected abstract List<Showtime> showtimes();

}
