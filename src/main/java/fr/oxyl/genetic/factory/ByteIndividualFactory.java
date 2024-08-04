package fr.oxyl.genetic.factory;

import fr.oxyl.genetic.api.CrossoverStrategy;
import fr.oxyl.genetic.core.ByteIndividual;
import fr.oxyl.genetic.crossover.SinglePointCrossoverStrategy;
import fr.oxyl.genetic.crossover.TwoPointsCrossoverStrategy;
import fr.oxyl.genetic.recombiner.ByteRecombiner;
import java.util.concurrent.ThreadLocalRandom;

public interface ByteIndividualFactory {

  static ByteIndividual randomIndividual() {
    byte[] bytes = new byte[1];
    ThreadLocalRandom.current().nextBytes(bytes);
    return new ByteIndividual(bytes[0]);
  }

  static CrossoverStrategy<ByteIndividual> singlePointCrossoverStrategy() {
    return new SinglePointCrossoverStrategy<>(new ByteRecombiner(), ByteIndividual::new);
  }

  static CrossoverStrategy<ByteIndividual> twoPointsCrossoverStrategy() {
    return new TwoPointsCrossoverStrategy<>(new ByteRecombiner(), ByteIndividual::new);
  }

}
