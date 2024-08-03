package fr.oxyl.genetic.api;

import java.util.List;

public interface Crossover<T extends Individual<T>> {

  List<T> mate(T parent1, T parent2);

  class DefaultCrossover<T extends Individual<T>> implements Crossover<T> {

    @Override
    public List<T> mate(T parent1, T parent2) {
      return parent1.crossover(parent2);
    }

  }

}
