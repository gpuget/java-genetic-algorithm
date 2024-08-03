package fr.oxyl.genetic.core;

import fr.oxyl.genetic.api.Crossover;
import fr.oxyl.genetic.api.Individual;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ProbabilityCrossover<T extends Individual<T>> implements Crossover<T> {

  private final float probability;

  private ProbabilityCrossover(float probability) {
    this.probability = probability;
  }

  public static <U extends Individual<U>> ProbabilityCrossover<U> create(float probability) {
    return new ProbabilityCrossover<>(probability);
  }

  @Override
  public List<T> perform(List<T> individuals) {
    var bestOffsprings = individuals.get(0).crossover(individuals.get(1));
    var offsprings = new LinkedList<>(bestOffsprings);

    var random = ThreadLocalRandom.current();
    while (offsprings.size() < individuals.size()) {
      var parent1 = individuals.get(random.nextInt(0, individuals.size()));
      var parent2 = individuals.get(random.nextInt(0, individuals.size()));

      if (random.nextFloat() < this.probability) {
        offsprings.addAll(parent1.crossover(parent2));
      } else {
        offsprings.add(parent1);
        offsprings.add(parent2);
      }
    }

    while (offsprings.size() != individuals.size()) {
      offsprings.remove(random.nextInt(0, offsprings.size()));
    }

    return offsprings;
  }

}
