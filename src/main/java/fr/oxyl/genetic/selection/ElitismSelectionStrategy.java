package fr.oxyl.genetic.selection;

import fr.oxyl.genetic.api.SelectionStrategy;
import fr.oxyl.genetic.core.Individual;
import java.util.LinkedList;
import java.util.List;

public final class ElitismSelectionStrategy<T extends Individual<?>> implements SelectionStrategy<T> {

  private final float rate;

  public ElitismSelectionStrategy(float rate) {
    this.rate = Math.min(rate, 1F);
  }

  @Override
  public List<T> select(List<T> individuals) {
    var selection = new LinkedList<T>();

    int best = (int) (individuals.size() * this.rate);
    for (int j = 0; j < best; j++) {
      selection.add(individuals.get(j));
    }

    return selection;
  }
}
