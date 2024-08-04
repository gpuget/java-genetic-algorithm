package fr.oxyl.genetic.recombiner;

import fr.oxyl.genetic.api.Recombiner;
import java.util.ArrayList;
import java.util.List;

public final class ListRecombiner<T> implements Recombiner<List<T>> {

  @Override
  public List<List<T>> cut(List<T> genome, int[] indexes) {
    var result = new ArrayList<List<T>>(indexes.length + 1);

    int previousIndex = 0;
    for (int index : indexes) {
      result.add(genome.subList(previousIndex, index));
      previousIndex = index;
    }
    result.add(genome.subList(previousIndex, genome.size()));

    return result;
  }

  @Override
  public List<T> recombine(List<T> genome1, List<T> genome2) {
    var result = new ArrayList<T>(genome1.size() + genome2.size());
    result.addAll(genome1);
    result.addAll(genome2);
    return result;
  }

}
