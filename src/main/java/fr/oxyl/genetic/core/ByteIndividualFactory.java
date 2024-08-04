package fr.oxyl.genetic.core;

import fr.oxyl.genetic.api.CrossoverStrategy;
import fr.oxyl.genetic.api.FitnessCalculator;
import fr.oxyl.genetic.api.MutationStrategy;
import fr.oxyl.genetic.crossover.CrossoverStrategies;
import fr.oxyl.genetic.fitness.BitCountCalculator;
import fr.oxyl.genetic.mutation.MutationStrategies;
import fr.oxyl.genetic.recombiner.ByteRecombiner;
import java.util.concurrent.ThreadLocalRandom;

public interface ByteIndividualFactory {

  static ByteIndividual createRandomIndividual() {
    byte[] bytes = new byte[1];
    ThreadLocalRandom.current().nextBytes(bytes);
    return new ByteIndividual(bytes[0]);
  }

  static FitnessCalculator<ByteIndividual> countBitCalculator(int b) {
    return new BitCountCalculator<>((byte) b);
  }

  static CrossoverStrategy<ByteIndividual> singlePointCrossover() {
    return CrossoverStrategies.singlePointCrossoverStrategy(new ByteRecombiner(), ByteIndividual::new);
  }

  static CrossoverStrategy<ByteIndividual> twoPointsCrossover() {
    return CrossoverStrategies.twoPointsCrossoverStrategy(new ByteRecombiner(), ByteIndividual::new);
  }

  static MutationStrategy<ByteIndividual> randomBitMutation() {
    return MutationStrategies.randomBitMutationStrategy(ByteIndividual::new);
  }

}
