package fr.oxyl.genetic.api;

import fr.oxyl.genetic.api.Crossover.DefaultCrossover;
import fr.oxyl.genetic.api.Mutator.DefaultMutator;
import fr.oxyl.genetic.api.PopulationEvaluator.DefaultPopulationEvaluator;
import fr.oxyl.genetic.api.PopulationSelector.DefaultPopulationSelector;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm<T extends Individual<T>> {

  private final PopulationGenerator<T> generator;
  private final PopulationEvaluator<T> evaluator;
  private final PopulationSelector<T> selector;
  private final Crossover<T> crossover;
  private final Mutator<T> mutator;

  private GeneticAlgorithm(PopulationGenerator<T> generator, PopulationEvaluator<T> evaluator,
      PopulationSelector<T> selector, Crossover<T> crossover, Mutator<T> mutator) {
    this.generator = generator;
    this.evaluator = evaluator;
    this.selector = selector;
    this.crossover = crossover;
    this.mutator = mutator;
  }

  public static <U extends Individual<U>> GeneticAlgorithm.Builder<U> builder() {
    return new Builder<>();
  }

  public Optional<T> execute(Parameters parameters) {
    long start = System.currentTimeMillis();

    var population = this.generator.generate(parameters.populationSize());
    if (population.isEmpty()) {
      System.err.println("No individual has been generated: abort");
      return Optional.empty();
    }

    int generation = 0;
    T best = null;
    do {
      var evaluated = this.evaluator.evaluate(population);
      best = evaluated.getFirst();

      var selection = this.selector.select(evaluated);
      var offsprings = crossover(parameters.crossoverProbability(), parameters.populationSize(), selection);

      population = mutation(parameters.mutationProbability(), offsprings);
      generation++;
    } while (generation < parameters.generationLimit() && best.fitness() < parameters.fitnessTarget());

    var evaluated = this.evaluator.evaluate(population);
    best = evaluated.getFirst();
    System.out.println("Generation count: " + generation);
    System.out.println("Execution time: " + (System.currentTimeMillis() - start));
    System.out.println("Best individual: " + best);
    return Optional.of(best);
  }

  private List<T> crossover(float probability, int populationSize, List<T> individuals) {
    if (individuals.isEmpty()) {
      return individuals;
    }

    var offsprings = new LinkedList<T>();
    if (individuals.size() > 1) {
      var bestOffsprings = this.crossover.mate(individuals.get(0), individuals.get(1));
      offsprings.addAll(bestOffsprings);
    }

    var random = ThreadLocalRandom.current();
    while (offsprings.size() < populationSize) {
      var parent1 = individuals.get(random.nextInt(0, individuals.size()));
      var parent2 = individuals.get(random.nextInt(0, individuals.size()));

      if (random.nextFloat() < probability) {
        offsprings.addAll(this.crossover.mate(parent1, parent2));
      } else {
        offsprings.add(parent1);
        offsprings.add(parent2);
      }
    }

    while (offsprings.size() != populationSize) {
      offsprings.remove(random.nextInt(0, offsprings.size()));
    }

    return offsprings;
  }

  private List<T> mutation(float probability, List<T> individuals) {
    var random = ThreadLocalRandom.current();
    return individuals.stream()
        .map(individual -> random.nextFloat() < probability ? this.mutator.mutate(individual) : individual)
        .toList();
  }

  public static class Parameters {

    private final int populationSize;
    private final int generationLimit;
    private final int fitnessTarget;
    private final float crossoverProbability;
    private final float mutationProbability;

    protected Parameters(int populationSize, int generationLimit, int fitnessTarget, float crossoverProbability,
        float mutationProbability) {
      this.populationSize = populationSize;
      this.generationLimit = generationLimit;
      this.fitnessTarget = fitnessTarget;
      this.crossoverProbability = Math.min(crossoverProbability, 1F);
      this.mutationProbability = Math.min(mutationProbability, 1F);
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

    public int fitnessTarget() {
      return this.fitnessTarget;
    }

    public Float crossoverProbability() {
      return this.crossoverProbability;
    }

    public Float mutationProbability() {
      return this.mutationProbability;
    }

  }

  public static final class Builder<T extends Individual<T>> {

    private PopulationGenerator<T> generator = PopulationGenerator.empty();
    private PopulationEvaluator<T> evaluator = new DefaultPopulationEvaluator<>();
    private PopulationSelector<T> selector = new DefaultPopulationSelector<>();
    private Crossover<T> crossover = new DefaultCrossover<>();
    private Mutator<T> mutator = new DefaultMutator<>();

    public GeneticAlgorithm<T> build() {
      return new GeneticAlgorithm<>(
          this.generator,
          this.evaluator,
          this.selector,
          this.crossover,
          this.mutator
      );
    }

    public Builder<T> generator(PopulationGenerator<T> generator) {
      this.generator = generator;
      return this;
    }

    public Builder<T> evaluator(PopulationEvaluator<T> evaluator) {
      this.evaluator = evaluator;
      return this;
    }

    public Builder<T> selector(PopulationSelector<T> selector) {
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
    private int fitnessTarget = Integer.MAX_VALUE;
    private float crossoverProbability = 0.9F;
    private float mutationProbability = 0.05F;

    public Parameters build() {
      return new Parameters(
          this.populationSize,
          this.generationLimit,
          this.fitnessTarget,
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

    public ParametersBuilder fitnessTarget(int fitnessTarget) {
      this.fitnessTarget = fitnessTarget;
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
