package fr.oxyl.genetic.api;

import java.util.List;

public interface PopulationSelector<T extends Individual<T>> {

  List<T> select(List<T> individuals);

  class DefaultPopulationSelector<T extends Individual<T>> implements PopulationSelector<T> {

    @Override
    public List<T> select(List<T> individuals) {
      return individuals;
    }

  }

}
