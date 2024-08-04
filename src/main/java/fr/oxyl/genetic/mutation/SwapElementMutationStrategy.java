package fr.oxyl.genetic.mutation;

import fr.oxyl.genetic.api.MutationStrategy;
import fr.oxyl.genetic.core.ListIndividual;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class SwapElementMutationStrategy<T, U extends ListIndividual<T>> implements MutationStrategy<U> {

  private final Function<List<T>, U> factory;

  SwapElementMutationStrategy(Function<List<T>, U> factory) {
    this.factory = factory;
  }

  @Override
  public U mutate(U individual) {
    var random = ThreadLocalRandom.current();
    var genome = new LinkedList<>(individual.genome());
    if (individual.genome().size() > 1) {
      int i = random.nextInt(0, individual.genome().size());
      int j = random.nextInt(0, individual.genome().size());
      Collections.swap(genome, i, j);
    }
    return this.factory.apply(individual.genome());
  }

}
