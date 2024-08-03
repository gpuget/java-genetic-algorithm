package fr.oxyl.genetic.api;

import java.util.List;

public interface Mutator<T extends Individual<T>> {

  List<T> mutate(List<T> individuals);

}
