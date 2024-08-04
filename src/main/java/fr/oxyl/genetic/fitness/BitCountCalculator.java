package fr.oxyl.genetic.fitness;

import fr.oxyl.genetic.api.FitnessCalculator;
import fr.oxyl.genetic.core.Individual;

public final class BitCountCalculator<T extends Individual<Byte>> implements FitnessCalculator<T> {

  private final byte value;

  public BitCountCalculator(byte value) {
    this.value = value;
  }

  @Override
  public int compute(T individual) {
    int fitness = 0;
    int value = individual.genome() & 0xFF;
    for (int i = 0; i < 8; i++) {
      if ((value & this.value) == 1) {
        fitness++;
      }
      value >>= 1;
    }
    return fitness;
  }

}
