package fr.oxyl.genetic.core;

import fr.oxyl.genetic.api.Crossover;
import fr.oxyl.genetic.api.Individual;
import fr.oxyl.genetic.api.Mutator;
import fr.oxyl.genetic.api.PopulationEvaluator;
import fr.oxyl.genetic.api.PopulationGenerator;
import fr.oxyl.genetic.api.PopulationSelection;
import java.util.LinkedList;

public class GeneticAlgorithm<T extends Individual<T>> implements Runnable {

  private final Parameters parameters;
  private final PopulationGenerator<T> generator;
  private final PopulationEvaluator<T> evaluator;
  private final PopulationSelection<T> selector;
  private final Crossover<T> crossover;
  private final Mutator<T> mutator;

  private GeneticAlgorithm(Parameters parameters, PopulationGenerator<T> generator, PopulationEvaluator<T> evaluator,
      PopulationSelection<T> selector, Crossover<T> crossover, Mutator<T> mutator) {
    this.parameters = parameters;
    this.generator = generator;
    this.evaluator = evaluator;
    this.selector = selector;
    this.crossover = crossover;
    this.mutator = mutator;
  }

  public static <U extends Individual<U>> GeneticAlgorithm.Builder<U> builder(PopulationGenerator<U> generator) {
    return new Builder<>(generator);
  }

  @Override
  public void run() {
    long start = System.currentTimeMillis();

    var population = this.generator.generate(this.parameters.populationSize());
    int generation = 0;
    while (generation < this.parameters.generationLimit()) {
      var evaluated = this.evaluator.evaluate(population);

      var selection = this.selector.select(evaluated);
      population = new LinkedList<>(selection);

      var offsprings = this.crossover.perform(evaluated.subList(0, evaluated.size() - selection.size()));
      population.addAll(offsprings);

      population = this.mutator.mutate(population);
      generation++;
    }

    var evaluated = this.evaluator.evaluate(population);
    System.out.println("Generation count: " + generation);
    System.out.println("Execution time: " + (System.currentTimeMillis() - start));
    System.out.println("Best individual: " + evaluated.getFirst());
  }

  public static class Parameters {

    private final int populationSize;
    private final int generationLimit;
    private Float elitismRate;
    private Float crossoverProbability;
    private Float mutationProbability;

    protected Parameters(int populationSize, int generationLimit, Float elitismRate, Float crossoverProbability,
        Float mutationProbability) {
      this.populationSize = populationSize;
      this.generationLimit = generationLimit;
      this.elitismRate = elitismRate;
      this.crossoverProbability = crossoverProbability;
      this.mutationProbability = mutationProbability;
    }

    public static ParametersBuilder builder() {
      return new ParametersBuilder();
    }

    public int populationSize() {
      return this.populationSize;
    }

    public int generationLimit() {
      return this.generationLimit;
    }

    public Float elitismRate() {
      return this.elitismRate;
    }

    public Float crossoverProbability() {
      return this.crossoverProbability;
    }

    public Float mutationProbability() {
      return this.mutationProbability;
    }

  }

  public static final class Builder<T extends Individual<T>> {

    private Parameters parameters = Parameters.builder().build();
    private final PopulationGenerator<T> generator;
    private PopulationEvaluator<T> evaluator = new NaturalPopulationEvaluator<>();
    private PopulationSelection<T> selector = ElitismPopulationSelector.create(0.1F);
    private Crossover<T> crossover = ProbabilityCrossover.create(1F);
    private Mutator<T> mutator = ProbabilityMutator.create(0.1F);

    public Builder(PopulationGenerator<T> generator) {
      this.generator = generator;
    }

    public GeneticAlgorithm<T> build() {
      return new GeneticAlgorithm<>(
          this.parameters,
          this.generator,
          this.evaluator,
          this.selector,
          this.crossover,
          this.mutator
      );
    }

    public Builder<T> parameters(Parameters parameters) {
      this.parameters = parameters;

      if (parameters.elitismRate() != null) {
        selector(ElitismPopulationSelector.create(parameters.elitismRate()));
      }
      if (parameters.mutationProbability() != null) {
        crossover(ProbabilityCrossover.create(parameters.mutationProbability()));
      }
      if (parameters.mutationProbability() != null) {
        mutator(ProbabilityMutator.create(parameters.mutationProbability()));
      }

      return this;
    }

    public Builder<T> evaluator(PopulationEvaluator<T> evaluator) {
      this.evaluator = evaluator;
      return this;
    }

    public Builder<T> selector(PopulationSelection<T> selector) {
      this.selector = selector;
      return this;
    }

    public Builder<T> crossover(Crossover<T> crossover) {
      this.crossover = crossover;
      return this;
    }

    public Builder<T> mutator(Mutator<T> mutator) {
      this.mutator = mutator;
      return this;
    }
  }

  public static class ParametersBuilder {

    private int populationSize = 1000;
    private int generationLimit = 1000;
    private Float elitismRate;
    private Float crossoverProbability;
    private Float mutationProbability;

    public Parameters build() {
      return new Parameters(
          this.populationSize,
          this.generationLimit,
          this.elitismRate,
          this.crossoverProbability,
          this.mutationProbability
      );
    }

    public ParametersBuilder populationSize(int populationSize) {
      this.populationSize = populationSize;
      return this;
    }

    public ParametersBuilder generationLimit(int generationLimit) {
      this.generationLimit = generationLimit;
      return this;
    }

    public ParametersBuilder elitismRate(Float elitismRate) {
      this.elitismRate = elitismRate;
      return this;
    }

    public ParametersBuilder crossoverProbability(Float crossoverProbability) {
      this.crossoverProbability = crossoverProbability;
      return this;
    }

    public ParametersBuilder mutationProbability(Float mutationProbability) {
      this.mutationProbability = mutationProbability;
      return this;
    }

  }

}
