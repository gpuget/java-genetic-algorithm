package fr.oxyl.genetic.mutation;

import fr.oxyl.genetic.api.MutationStrategy;
import fr.oxyl.genetic.core.Individual;
import fr.oxyl.genetic.core.ListIndividual;
import java.util.List;
import java.util.function.Function;

public interface MutationStrategies {

  static <U extends Individual<Byte>> MutationStrategy<U> randomBitMutationStrategy(Function<Byte, U> factory) {
    return new RandomBitMutationStrategy<>(factory);
  }

  static <T, U extends ListIndividual<T>> MutationStrategy<U> removeListMutationStrategy(Function<List<T>, U> factory) {
    return new RemoveListMutationStrategy<>(factory);
  }

  static <T, U extends ListIndividual<T>> MutationStrategy<U> swapListMutationStrategy(Function<List<T>, U> factory) {
    return new SwapElementMutationStrategy<>(factory);
  }

}
