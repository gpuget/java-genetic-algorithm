package fr.oxyl.genetic.api;

import fr.oxyl.genetic.core.Individual;

@FunctionalInterface
public interface MutationStrategy<T extends Individual<?>> {

  T mutate(T individual);

}
