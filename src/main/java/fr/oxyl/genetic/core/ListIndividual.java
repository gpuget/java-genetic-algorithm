package fr.oxyl.genetic.core;

import java.util.List;

public class ListIndividual<T> extends Individual<List<T>> {

  protected ListIndividual(List<T> genome) {
    super(List.copyOf(genome));
  }

  @Override
  public int genomeSize() {
    return this.genome.size();
  }

}
