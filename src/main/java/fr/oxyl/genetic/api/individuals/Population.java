package fr.oxyl.genetic.api.individuals;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Population<E extends Individual> {

  private static final Population<Individual> EMPTY = new Population<>(Collections.emptyList());

  private final List<E> individuals;

  private Population(Collection<E> individuals) {
    this.individuals = List.copyOf(individuals);
  }

  public static <E extends Individual> Population<E> empty() {
    return (Population<E>) EMPTY;
  }

  public static <E extends Individual> Population<E> of(Collection<E> individuals) {
    return new Population<>(individuals);
  }

  public int size() {
    return this.individuals.size();
  }

}
