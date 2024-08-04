package fr.oxyl.genetic.mutation;

import fr.oxyl.genetic.api.MutationStrategy;
import fr.oxyl.genetic.core.ListIndividual;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class RemoveListMutationStrategy<T, U extends ListIndividual<T>> implements MutationStrategy<U> {

  private final Function<List<T>, U> factory;

  RemoveListMutationStrategy(Function<List<T>, U> factory) {
    this.factory = factory;
  }

  @Override
  public U mutate(U individual) {
    var random = ThreadLocalRandom.current();
    var genome = new LinkedList<>(individual.genome());
    int gene = random.nextInt(0, individual.genomeSize());
    genome.remove(gene);
    return this.factory.apply(genome);
  }

}
