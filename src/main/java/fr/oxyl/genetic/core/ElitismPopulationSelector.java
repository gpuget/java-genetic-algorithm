package fr.oxyl.genetic.core;

import fr.oxyl.genetic.api.Individual;
import fr.oxyl.genetic.api.PopulationSelection;
import java.util.LinkedList;
import java.util.List;

public class ElitismPopulationSelector<T extends Individual<T>> implements PopulationSelection<T> {

  private final float rate;

  public ElitismPopulationSelector(float rate) {
    this.rate = rate;
  }

  @Override
  public List<T> select(List<T> individuals) {
    var nextGeneration = new LinkedList<T>();

    int best = (int) (individuals.size() * this.rate);
    for (int j = 0; j < best; j++) {
      nextGeneration.add(individuals.get(j));
    }

    return nextGeneration;
  }

}
