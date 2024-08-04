package fr.oxyl.genetic.api;

import fr.oxyl.genetic.core.Individual;
import java.util.List;

@FunctionalInterface
public interface SelectionStrategy<T extends Individual> {

  List<T> select(List<T> individuals);

}
