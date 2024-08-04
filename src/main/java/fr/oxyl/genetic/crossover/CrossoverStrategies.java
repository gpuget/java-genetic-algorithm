package fr.oxyl.genetic.crossover;

import fr.oxyl.genetic.api.Recombiner;
import fr.oxyl.genetic.core.Individual;
import java.util.function.Function;

public interface CrossoverStrategies {

  static <T, U extends Individual<T>> SinglePointCrossoverStrategy<T, U> singlePointCrossoverStrategy(
      Recombiner<T> recombiner, Function<T, U> factory) {
    return new SinglePointCrossoverStrategy<>(recombiner, factory);
  }

  static <T, U extends Individual<T>> TwoPointsCrossoverStrategy<T, U> twoPointsCrossoverStrategy(
      Recombiner<T> recombiner, Function<T, U> factory) {
    return new TwoPointsCrossoverStrategy<>(recombiner, factory);
  }

}
