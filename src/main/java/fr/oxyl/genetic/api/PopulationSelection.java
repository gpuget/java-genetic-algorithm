package fr.oxyl.genetic.api;

import java.util.List;

public interface PopulationSelection<T extends Individual<T>> {

  List<T> select(List<T> individuals);

}
