package fr.oxyl.genetic.api;

import fr.oxyl.genetic.core.Individual;
import java.util.function.ToIntFunction;

@FunctionalInterface
public interface FitnessCalculator<T extends Individual<?>> extends ToIntFunction<T> {

  int compute(T individual);

  @Override
  default int applyAsInt(T value) {
    return compute(value);
  }

}
