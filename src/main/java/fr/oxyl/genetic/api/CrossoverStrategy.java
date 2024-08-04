package fr.oxyl.genetic.api;

import fr.oxyl.genetic.core.Individual;
import java.util.Collection;

@FunctionalInterface
public interface CrossoverStrategy<T extends Individual<?>> {

  Collection<T> mate(T parent1, T parent2);

}
