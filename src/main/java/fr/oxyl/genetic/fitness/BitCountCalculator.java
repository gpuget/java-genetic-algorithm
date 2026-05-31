package fr.oxyl.genetic.fitness;

import fr.oxyl.genetic.api.FitnessCalculator;
import fr.oxyl.genetic.core.Individual;

public final class BitCountCalculator<T extends Individual<Byte>> implements FitnessCalculator<T> {

  private final byte target;

  public BitCountCalculator(byte target) {
    this.target = target;
  }

  @Override
  public int compute(T individual) {
    int matchingBits = 0;
    int genomeBits = individual.genome() & 0xFF;
    int targetBits = this.target & 0xFF;
    for (int bitIndex = 0; bitIndex < Byte.SIZE; bitIndex++) {
      if ((genomeBits & 1) == (targetBits & 1)) {
        matchingBits++;
      }
      genomeBits >>= 1;
      targetBits >>= 1;
    }
    return matchingBits;
  }

}
