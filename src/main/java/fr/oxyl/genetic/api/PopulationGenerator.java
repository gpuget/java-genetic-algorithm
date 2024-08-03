package fr.oxyl.genetic.api;

import java.util.List;

public interface PopulationGenerator<T extends Individual<T>> {

  List<T> generate(int count);

}
