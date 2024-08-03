package fr.oxyl.genetic.core;

import fr.oxyl.genetic.api.Individual;
import fr.oxyl.genetic.api.PopulationEvaluator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NaturalPopulationEvaluator<T extends Individual> implements PopulationEvaluator<T> {

  @Override
  public List<T> evaluate(List<T> individuals) {
    var list = new ArrayList<>(individuals);
    Collections.sort(list);
    return list;
  }

}
