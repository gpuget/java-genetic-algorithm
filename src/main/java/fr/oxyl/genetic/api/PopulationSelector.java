package fr.oxyl.genetic.api;

import java.util.List;

public interface PopulationSelector<T extends Individual<T>> {

  List<T> select(List<T> individuals);

}
