package fr.oxyl.genetic.application.showtime;

import fr.oxyl.genetic.api.CrossoverStrategy;
import fr.oxyl.genetic.api.FitnessCalculator;
import fr.oxyl.genetic.api.MutationStrategy;
import fr.oxyl.genetic.crossover.CrossoverStrategies;
import fr.oxyl.genetic.mutation.MutationStrategies;
import fr.oxyl.genetic.recombiner.ListRecombiner;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public interface ShowtimeIndividualFactory {

  int MAX_SHOWTIMES_PER_INDIVIDUAL = 10;

  static ShowtimeIndividual createRandomShowtimeIndividual(List<Showtime> showtimes) {
    var random = ThreadLocalRandom.current();
    return new ShowtimeIndividual(Stream.generate(() -> showtimes.get(random.nextInt(0, showtimes.size())))
        .limit(random.nextInt(1, MAX_SHOWTIMES_PER_INDIVIDUAL))
        .toList());
  }

  static FitnessCalculator<ShowtimeIndividual> showtimePlannerFitnessCalculator() {
    return new ShowtimePlannerFitnessCalculator();
  }

  static CrossoverStrategy<ShowtimeIndividual> singlePointCrossover() {
    return CrossoverStrategies.singlePointCrossoverStrategy(new ListRecombiner<>(), ShowtimeIndividual::new);
  }

  static CrossoverStrategy<ShowtimeIndividual> showtimePlannerCrossoverStrategy() {
    return new ShowtimePlannerCrossoverStrategy();
  }

  static MutationStrategy<ShowtimeIndividual> removeShowtimeMutation() {
    return MutationStrategies.removeListMutationStrategy(ShowtimeIndividual::new);
  }

  static MutationStrategy<ShowtimeIndividual> swapShowtimeMutation() {
    return MutationStrategies.swapListMutationStrategy(ShowtimeIndividual::new);
  }

}
