package fr.oxyl.genetic.crossover;

import fr.oxyl.genetic.api.CrossoverStrategy;
import fr.oxyl.genetic.api.Recombiner;
import fr.oxyl.genetic.core.Individual;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class AbtractCrossoverStrategy<T, U extends Individual<T>> implements CrossoverStrategy<U> {

  protected final Recombiner<T> recombiner;
  private final Function<T, U> factory;

  public AbtractCrossoverStrategy(Recombiner<T> recombiner, Function<T, U> factory) {
    this.recombiner = recombiner;
    this.factory = factory;
  }

  @Override
  public Collection<U> mate(U parent1, U parent2) {
    return offspringsGenomes(parent1, parent2)
        .map(this.factory)
        .toList();
  }

  protected abstract Stream<T> offspringsGenomes(U parent1, U parent2);

}
