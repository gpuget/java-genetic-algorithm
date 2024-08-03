package fr.oxyl.genetic.api;

import java.util.List;

public interface PopulationGenerator<T extends Individual<T>> {

  static <U extends Individual<U>> PopulationGenerator<U> empty() {
    return count -> List.of();
  }

  List<T> generate(int count);

}
