package fr.oxyl.genetic.core;

import fr.oxyl.genetic.api.FitnessCalculator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Population<T extends Individual<?>> {

  private final List<T> individuals;

  private Population(List<T> individuals) {
    this.individuals = new ArrayList<>(individuals);
  }

  private Population(int size, Supplier<T> individualFactory) {
    this(Stream.generate(individualFactory).limit(size).toList());
  }

  public static <U extends Individual<?>> Population<U> create(List<U> individuals) {
    return new Population<>(individuals);
  }

  public static <U extends Individual<?>> Population<U> create(int size, Supplier<U> individualFactory) {
    return new Population<>(size, individualFactory);
  }

  public Optional<T> computeFitness(FitnessCalculator<T> fitnessCalculator) {
    this.individuals.forEach(individual -> individual.setFitness(fitnessCalculator.compute(individual)));
    this.individuals.sort(Comparator.comparing(T::fitness).reversed());
    return this.individuals.isEmpty()
        ? Optional.empty()
        : Optional.of(this.individuals.getFirst());
  }

  public List<T> individuals() {
    return List.copyOf(this.individuals);
  }

}
