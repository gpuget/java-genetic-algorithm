package fr.oxyl.genetic.factory;

import fr.oxyl.genetic.api.CrossoverStrategy;
import fr.oxyl.genetic.api.FitnessCalculator;
import fr.oxyl.genetic.api.MutationStrategy;
import fr.oxyl.genetic.core.ByteIndividual;
import fr.oxyl.genetic.crossover.CrossoverStrategies;
import fr.oxyl.genetic.fitness.BitCountCalculator;
import fr.oxyl.genetic.mutation.RandomBitMutationStrategy;
import fr.oxyl.genetic.recombiner.ByteRecombiner;
import java.util.concurrent.ThreadLocalRandom;

public interface ByteIndividualFactory {

  static ByteIndividual randomIndividual() {
    byte[] bytes = new byte[1];
    ThreadLocalRandom.current().nextBytes(bytes);
    return new ByteIndividual(bytes[0]);
  }

  static FitnessCalculator<ByteIndividual> countBitZeroCalculator() {
    return new BitCountCalculator<>((byte) 0x00);
  }

  static FitnessCalculator<ByteIndividual> countBitOneCalculator() {
    return new BitCountCalculator<>((byte) 0x01);
  }

  static CrossoverStrategy<ByteIndividual> singlePointCrossover() {
    return CrossoverStrategies.singlePointCrossoverStrategy(new ByteRecombiner(), ByteIndividual::new);
  }

  static CrossoverStrategy<ByteIndividual> twoPointsCrossover() {
    return CrossoverStrategies.twoPointsCrossoverStrategy(new ByteRecombiner(), ByteIndividual::new);
  }

  static MutationStrategy<ByteIndividual> randomBitMutation() {
    return new RandomBitMutationStrategy<>(ByteIndividual::new);
  }

}
