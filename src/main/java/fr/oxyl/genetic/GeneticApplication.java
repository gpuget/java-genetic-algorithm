package fr.oxyl.genetic;

import fr.oxyl.genetic.api.GeneticAlgorithm;
import fr.oxyl.genetic.api.GeneticAlgorithm.Parameters;
import fr.oxyl.genetic.core.ElitismPopulationSelector;
import fr.oxyl.genetic.core.showtimes.RandomShowtimeIndividualGenerator;
import fr.oxyl.genetic.core.showtimes.ShowtimeIndividual;

public class GeneticApplication {

  public static void main(String[] args) {
    var algorithm = GeneticAlgorithm.<ShowtimeIndividual>builder()
        .generator(new RandomShowtimeIndividualGenerator())
        .selector(new ElitismPopulationSelector<>(0.1F))
        .build();

    var parameters = Parameters.builder()
        .build();
    algorithm.execute(parameters);
  }

}
