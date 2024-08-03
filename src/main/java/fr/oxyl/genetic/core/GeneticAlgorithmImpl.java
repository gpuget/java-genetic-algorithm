package fr.oxyl.genetic.core;

import fr.oxyl.genetic.api.GeneticAlgorithm;
import fr.oxyl.genetic.api.Individual;
import fr.oxyl.genetic.api.PopulationEvaluator;
import fr.oxyl.genetic.api.PopulationGenerator;

public class GeneticAlgorithmImpl<T extends Individual> implements GeneticAlgorithm {

  private static final int MAX_ITERATION_COUNT = 1_000;
  private static final int DEFAULT_POPULATION_SIZE = 1_000;

  private final PopulationGenerator<T> generator;
  private final PopulationEvaluator<T> evaluator;

  public GeneticAlgorithmImpl(PopulationGenerator<T> generator, PopulationEvaluator<T> evaluator) {
    this.generator = generator;
    this.evaluator = evaluator;
  }

  @Override
  public void run() {
    long start = System.currentTimeMillis();

    var population = this.generator.generate(DEFAULT_POPULATION_SIZE);
    for (int iteration = 0; iteration < (MAX_ITERATION_COUNT - 1); iteration++) {
      var evaluated = this.evaluator.evaluate(population);
    }

    var evaluated = this.evaluator.evaluate(population);
    System.out.println("Best individual: " + evaluated.getFirst());
    System.out.println("Execution time: " + (System.currentTimeMillis() - start));

  }

}
