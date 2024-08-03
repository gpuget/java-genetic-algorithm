package fr.oxyl.genetic.api;

public interface Individual<T extends Individual<?>> extends Comparable<T> {

  int fitness();

  @Override
  default int compareTo(T o) {
    return o.fitness() - this.fitness();
  }

}
