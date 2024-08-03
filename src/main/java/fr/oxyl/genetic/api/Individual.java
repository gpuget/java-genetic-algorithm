package fr.oxyl.genetic.api;

import java.util.List;

public interface Individual<T extends Individual<?>> extends Comparable<T> {

  int fitness();

  List<T> crossover(T other);

  T mutate();

  @Override
  default int compareTo(T o) {
    return o.fitness() - this.fitness();
  }
}
