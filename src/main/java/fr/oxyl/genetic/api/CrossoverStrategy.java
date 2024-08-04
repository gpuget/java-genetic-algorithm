package fr.oxyl.genetic.api;

import java.util.Collection;
import java.util.List;

@FunctionalInterface
public interface CrossoverStrategy<T> {

  Collection<T> mate(List<T> parents);

}
