package fr.oxyl.genetic.api;

import java.util.List;

public interface PopulationEvaluator<T extends Individual> {

  List<T> evaluate(List<T> individuals);

}
