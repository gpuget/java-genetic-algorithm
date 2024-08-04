package fr.oxyl.genetic.application;

import fr.oxyl.genetic.application.GeneticAlgorithm.Parameters;
import fr.oxyl.genetic.selection.SelectionStrategies;
import fr.oxyl.genetic.application.showtime.ShowtimeIndividualFactory;

public class GeneticAlgorithmApplication {

  public static void main(String[] args) {
    var algorithm = GeneticAlgorithm.builder(ShowtimeIndividualFactory::createRandomShowtimeIndividual)
        .fitnessCalculator(ShowtimeIndividualFactory.showtimePlannerFitnessCalculator())
        .selectionStrategy(SelectionStrategies.elitism(0.1F))
        .crossoverStrategy(ShowtimeIndividualFactory.singlePointCrossover())
        .mutationStrategy(ShowtimeIndividualFactory.swapShowtimeMutation())
        .build();

    var parameters = Parameters.builder()
        .generationLimit(10_000)
        .build();

    algorithm.execute(parameters);
  }

}
