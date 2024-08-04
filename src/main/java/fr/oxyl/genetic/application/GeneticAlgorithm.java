package fr.oxyl.genetic.application;

import fr.oxyl.genetic.api.CrossoverStrategy;
import fr.oxyl.genetic.api.FitnessCalculator;
import fr.oxyl.genetic.api.IndividualFactory;
import fr.oxyl.genetic.api.MutationStrategy;
import fr.oxyl.genetic.api.SelectionStrategy;
import fr.oxyl.genetic.core.Individual;
import fr.oxyl.genetic.core.Population;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public final class GeneticAlgorithm<T extends Individual<?>> {

  private final IndividualFactory<T> individualFactory;
  private final FitnessCalculator<T> fitnessCalculator;
  private final SelectionStrategy<T> selectionStrategy;
  private final CrossoverStrategy<T> crossoverStrategy;
  private final MutationStrategy<T> mutationStrategy;

  private GeneticAlgorithm(IndividualFactory<T> individualFactory, FitnessCalculator<T> fitnessCalculator,
      SelectionStrategy<T> selectionStrategy, CrossoverStrategy<T> crossoverStrategy,
      MutationStrategy<T> mutationStrategy) {
    this.individualFactory = individualFactory;
    this.fitnessCalculator = fitnessCalculator;
    this.selectionStrategy = selectionStrategy;
    this.crossoverStrategy = crossoverStrategy;
    this.mutationStrategy = mutationStrategy;
  }

  public static <U extends Individual<?>> GeneticAlgorithm.Builder<U> builder(IndividualFactory<U> individualFactory) {
    return new Builder<>(individualFactory);
  }

  public void execute(Parameters parameters) {
    long startTime = System.currentTimeMillis();

    var population = Population.create(parameters.populationSize(), this.individualFactory);
    int generation = 0;
    Optional<T> best;
    do {
      best = population.computeFitness(this.fitnessCalculator);

      var selection = this.selectionStrategy.select(population.individuals());
      var offsprings = crossover(parameters.crossoverProbability(), parameters.populationSize(), selection);
      var mutants = mutation(parameters.mutationProbability(), offsprings);

      population = Population.create(mutants);
      generation++;
    } while (generation < parameters.generationLimit() && best.get().fitness() < parameters.fitnessTarget());
    best = population.computeFitness(this.fitnessCalculator);

    System.out.println("Generation count: " + generation);
    System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
    System.out.println("Best individual: " + best.get());
  }

  private List<T> crossover(float probability, int populationSize, List<T> individuals) {
    if (individuals.isEmpty()) {
      return individuals;
    }

    var offsprings = new LinkedList<T>();
    if (individuals.size() > 1) {
      var bestOffsprings = this.crossoverStrategy.mate(individuals.get(0), individuals.get(2));
      offsprings.addAll(bestOffsprings);
    }

    var random = ThreadLocalRandom.current();
    while (offsprings.size() < populationSize) {
      var parent1 = individuals.get(random.nextInt(0, individuals.size()));
      var parent2 = individuals.get(random.nextInt(0, individuals.size()));

      if (random.nextFloat() < probability) {
        offsprings.addAll(this.crossoverStrategy.mate(parent1, parent2));
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
        .map(individual -> random.nextFloat() < probability ? this.mutationStrategy.mutate(individual) : individual)
        .toList();
  }

  public void execute() {
    execute(Parameters.DEFAULT);
  }

  public static class Builder<T extends Individual<?>> {

    private final IndividualFactory<T> individualFactory;
    private FitnessCalculator<T> fitnessCalculator = individual -> 0;
    private SelectionStrategy<T> selectionStrategy = individuals -> individuals;
    private CrossoverStrategy<T> crossoverStrategy = List::of;
    private MutationStrategy<T> mutationStrategy = individual -> individual;

    private Builder(IndividualFactory<T> individualFactory) {
      this.individualFactory = individualFactory;
    }

    public GeneticAlgorithm<T> build() {
      return new GeneticAlgorithm<>(
          this.individualFactory,
          this.fitnessCalculator,
          this.selectionStrategy,
          this.crossoverStrategy,
          this.mutationStrategy
      );
    }

    public Builder<T> fitnessCalculator(FitnessCalculator<T> fitnessCalculator) {
      this.fitnessCalculator = fitnessCalculator;
      return this;
    }

    public Builder<T> selectionStrategy(SelectionStrategy<T> selectionStrategy) {
      this.selectionStrategy = selectionStrategy;
      return this;
    }

    public Builder<T> crossoverStrategy(CrossoverStrategy<T> crossoverStrategy) {
      this.crossoverStrategy = crossoverStrategy;
      return this;
    }

    public Builder<T> mutationStrategy(MutationStrategy<T> mutationStrategy) {
      this.mutationStrategy = mutationStrategy;
      return this;
    }

  }

  public record Parameters(
      int populationSize,
      int generationLimit,
      int fitnessTarget,
      float crossoverProbability,
      float mutationProbability
  ) {

    public Parameters {
      populationSize = Math.max(populationSize, 1);
      generationLimit = Math.max(generationLimit, 0);
      crossoverProbability = Math.min(Math.max(crossoverProbability, 0F), 1F);
      mutationProbability = Math.min(Math.max(mutationProbability, 0F), 1F);
    }

    private static final Parameters DEFAULT = Parameters.builder().build();

    public static Builder builder() {
      return new Builder();
    }

    public static class Builder {

      private int populationSize = 1_000;
      private int generationLimit = 1_000;
      private int fitnessTarget = Integer.MAX_VALUE;
      private float crossoverProbability = 0.9F;
      private float mutationProbability = 0.1F;

      public Parameters build() {
        return new Parameters(
            this.populationSize,
            this.generationLimit,
            this.fitnessTarget,
            this.crossoverProbability,
            this.mutationProbability
        );
      }

      public Builder populationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
      }

      public Builder generationLimit(int generationLimit) {
        this.generationLimit = generationLimit;
        return this;
      }

      public Builder fitnessTarget(int fitnessTarget) {
        this.fitnessTarget = fitnessTarget;
        return this;
      }

      public Builder crossoverProbability(float crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
        return this;
      }

      public Builder mutationProbability(float mutationProbability) {
        this.mutationProbability = mutationProbability;
        return this;
      }

    }

  }

}
