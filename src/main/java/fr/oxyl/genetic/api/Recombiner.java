package fr.oxyl.genetic.api;

import java.util.List;

public interface Recombiner<T> {

  default List<T> cut(T genome, int index) {
    return cut(genome, new int[]{ index });
  }

  List<T> cut(T genome, int[] indexes);

  T recombine(T genome1, T genome2);

}
