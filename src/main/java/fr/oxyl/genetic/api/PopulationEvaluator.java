package fr.oxyl.genetic.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface PopulationEvaluator<T extends Individual<T>> {

  List<T> evaluate(List<T> individuals);

  class DefaultPopulationEvaluator<T extends Individual<T>> implements PopulationEvaluator<T> {

    @Override
    public List<T> evaluate(List<T> individuals) {
      var list = new ArrayList<>(individuals);
      Collections.sort(list);
      return list;
    }
  }

}
