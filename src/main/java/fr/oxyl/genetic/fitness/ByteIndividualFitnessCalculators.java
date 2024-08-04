package fr.oxyl.genetic.fitness;

import fr.oxyl.genetic.api.FitnessCalculator;
import fr.oxyl.genetic.core.ByteIndividual;

public interface ByteIndividualFitnessCalculators {

  private static FitnessCalculator<ByteIndividual> byteValue(int byteValue) {
    return individual -> {
      int fitness = 0;
      int value = individual.genome() & 0xFF;
      for (int i = 0; i < 8; i++) {
        if ((value & byteValue) == 1) {
          fitness++;
        }
        value >>= 1;
      }
      return fitness;
    };
  }

  static FitnessCalculator<ByteIndividual> zeros() {
    return byteValue(0);
  }

  static FitnessCalculator<ByteIndividual> ones() {
    return byteValue(1);
  }

}
