package fr.oxyl.genetic.mutation;

import fr.oxyl.genetic.api.MutationStrategy;
import fr.oxyl.genetic.core.Individual;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public final class RandomBitMutationStrategy<U extends Individual<Byte>> implements MutationStrategy<U> {

  private final Function<Byte, U> factory;

  public RandomBitMutationStrategy(Function<Byte, U> factory) {
    this.factory = factory;
  }

  @Override
  public U mutate(U individual) {
    var random = ThreadLocalRandom.current();
    int gene = random.nextInt(0, Byte.SIZE + 1);
    byte genes = (byte) ((individual.genome() & 0xFF) ^ (int) (Math.pow(2, gene)));
    return this.factory.apply(genes);
  }

}
