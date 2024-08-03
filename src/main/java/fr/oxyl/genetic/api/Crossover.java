package fr.oxyl.genetic.api;

import java.util.List;

public interface Crossover<T extends Individual<T>> {

  List<T> perform(List<T> individuals);

}
