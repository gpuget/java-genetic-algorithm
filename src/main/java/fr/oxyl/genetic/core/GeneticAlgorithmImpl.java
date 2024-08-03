package fr.oxyl.genetic.core;

import fr.oxyl.genetic.api.GeneticAlgorithm;
import fr.oxyl.genetic.api.individuals.Individual;
import fr.oxyl.genetic.api.individuals.generators.PopulationGenerator;

public class GeneticAlgorithmImpl<T extends Individual> implements GeneticAlgorithm {

  private static final int MAX_ITERATION_COUNT = 1_000;
  private static final int DEFAULT_POPULATION_SIZE = 1_000;

  private final PopulationGenerator<T> populationGenerator;

  public GeneticAlgorithmImpl(PopulationGenerator<T> populationGenerator) {
    this.populationGenerator = populationGenerator;
  }

  @Override
  public void run() {
    var population = this.populationGenerator.generate(DEFAULT_POPULATION_SIZE);
    for (int iteration = 0; iteration < MAX_ITERATION_COUNT; iteration++) {
      // do nothing
    }
  }

}
