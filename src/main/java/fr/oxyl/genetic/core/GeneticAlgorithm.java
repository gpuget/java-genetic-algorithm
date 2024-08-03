package fr.oxyl.genetic.core;

import fr.oxyl.genetic.api.Crossover;
import fr.oxyl.genetic.api.Individual;
import fr.oxyl.genetic.api.Mutator;
import fr.oxyl.genetic.api.PopulationEvaluator;
import fr.oxyl.genetic.api.PopulationGenerator;
import fr.oxyl.genetic.api.PopulationSelection;
import java.util.LinkedList;

public class GeneticAlgorithm<T extends Individual<T>> implements Runnable {

  private static final int MAX_ITERATION_COUNT = 10_000;
  private static final int POPULATION_SIZE = 1_000;

  private final PopulationGenerator<T> generator;
  private final PopulationEvaluator<T> evaluator;
  private final PopulationSelection<T> selector;
  private final Crossover<T> crossover;
  private final Mutator<T> mutator;

  public GeneticAlgorithm(PopulationGenerator<T> generator, PopulationEvaluator<T> evaluator,
      PopulationSelection<T> selector, Crossover<T> crossover, Mutator<T> mutator) {
    this.generator = generator;
    this.evaluator = evaluator;
    this.selector = selector;
    this.crossover = crossover;
    this.mutator = mutator;
  }

  @Override
  public void run() {
    long start = System.currentTimeMillis();

    var population = this.generator.generate(POPULATION_SIZE);
    for (int iteration = 0; iteration < MAX_ITERATION_COUNT; iteration++) {
      var evaluated = this.evaluator.evaluate(population);

      var selection = this.selector.select(evaluated);
      population = new LinkedList<>(selection);

      var offsprings = this.crossover.perform(evaluated.subList(0, evaluated.size() - selection.size()));
      population.addAll(offsprings);

      population = this.mutator.mutate(population);
    }

    var evaluated = this.evaluator.evaluate(population);
    System.out.println("Execution time: " + (System.currentTimeMillis() - start));
    System.out.println("Best individual: " + evaluated.getFirst());
  }

}
