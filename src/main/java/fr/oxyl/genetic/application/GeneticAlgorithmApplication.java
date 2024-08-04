package fr.oxyl.genetic.application;

import fr.oxyl.genetic.application.GeneticAlgorithm.Parameters;
import fr.oxyl.genetic.application.showtime.ShowtimeFileReader;
import fr.oxyl.genetic.application.showtime.ShowtimeIndividualFactory;
import fr.oxyl.genetic.selection.SelectionStrategies;

public class GeneticAlgorithmApplication {

  public static void main(String[] args) {
    var showtimes = ShowtimeFileReader.read("showtimes.txt");
    var algorithm = GeneticAlgorithm.builder(() -> ShowtimeIndividualFactory.createRandomShowtimeIndividual(showtimes))
        .fitnessCalculator(ShowtimeIndividualFactory.showtimePlannerFitnessCalculator())
        .selectionStrategy(SelectionStrategies.elitism(0.1F))
        .crossoverStrategy(ShowtimeIndividualFactory.showtimePlannerCrossoverStrategy())
        .mutationStrategy(ShowtimeIndividualFactory.swapListMutationStrategy())
        .build();

    var parameters = Parameters.builder()
        .populationSize(1000)
        .generationLimit(1000)
        .build();

    algorithm.execute(parameters);
  }

}
