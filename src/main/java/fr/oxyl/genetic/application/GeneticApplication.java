package fr.oxyl.genetic.application;

import fr.oxyl.genetic.core.ElitismPopulationSelector;
import fr.oxyl.genetic.core.GeneticAlgorithm;
import fr.oxyl.genetic.core.NaturalPopulationEvaluator;
import fr.oxyl.genetic.core.ProbabilityCrossover;
import fr.oxyl.genetic.core.ProbabilityMutator;
import fr.oxyl.genetic.core.bytes.RandomBytesIndividualGenerator;

public class GeneticApplication {

  public static void main(String[] args) {
    var runnable = new GeneticAlgorithm<>(
        new RandomBytesIndividualGenerator(),
        new NaturalPopulationEvaluator<>(),
        new ElitismPopulationSelector<>(0.1F),
        new ProbabilityCrossover<>(1.0F),
        new ProbabilityMutator<>(0.1F)
    );

    runnable.run();
  }

}
