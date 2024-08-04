package fr.oxyl.genetic.application;

import fr.oxyl.genetic.application.GeneticAlgorithm.Parameters;
import fr.oxyl.genetic.application.showtime.ShowtimeFileReader;
import fr.oxyl.genetic.application.showtime.ShowtimeIndividualFactory;
import fr.oxyl.genetic.selection.SelectionStrategies;

public class GeneticAlgorithmApplication {

  public static void main(String[] args) {
    var showtimes = ShowtimeFileReader.read("showtimes.txt");
    System.out.println(showtimes);
    var algorithm = GeneticAlgorithm.builder(() -> ShowtimeIndividualFactory.createRandomShowtimeIndividual(showtimes))
        .fitnessCalculator(ShowtimeIndividualFactory.showtimePlannerFitnessCalculator())
        .selectionStrategy(SelectionStrategies.elitism(0.1F))
        .crossoverStrategy(ShowtimeIndividualFactory.showtimePlannerCrossoverStrategy())
        .mutationStrategy(ShowtimeIndividualFactory.swapShowtimeMutation())
        .build();

    var parameters = Parameters.builder()
        .populationSize(10000)
        .generationLimit(100)
        .build();

    algorithm.execute(parameters);
  }

}
