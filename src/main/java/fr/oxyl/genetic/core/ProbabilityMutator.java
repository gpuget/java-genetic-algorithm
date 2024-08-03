package fr.oxyl.genetic.core;

import fr.oxyl.genetic.api.Individual;
import fr.oxyl.genetic.api.Mutator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ProbabilityMutator<T extends Individual<T>> implements Mutator<T> {

  private final float probability;

  public ProbabilityMutator(float probability) {
    this.probability = probability;
  }

  @Override
  public List<T> mutate(List<T> individuals) {
    var random = ThreadLocalRandom.current();
    return individuals.stream()
        .map(individual -> random.nextFloat() < this.probability ? individual.mutate() : individual)
        .toList();
  }

}
