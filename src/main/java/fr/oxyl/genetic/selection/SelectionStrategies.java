package fr.oxyl.genetic.selection;

import fr.oxyl.genetic.api.SelectionStrategy;
import fr.oxyl.genetic.core.Individual;

public interface SelectionStrategies {

  static <U extends Individual<?>> SelectionStrategy<U> elitism(float rate) {
    return new ElitismSelectionStrategy<>(rate);
  }

  static <U extends Individual<?>> SelectionStrategy<U> rouletteWheel() {
    throw new UnsupportedOperationException();
  }

  static <U extends Individual<?>> SelectionStrategy<U> tournament() {
    throw new UnsupportedOperationException();
  }

  static <U extends Individual<?>> SelectionStrategy<U> rank() {
    throw new UnsupportedOperationException();
  }

}
