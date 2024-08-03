package fr.oxyl.genetic.application;

import fr.oxyl.genetic.core.GeneticAlgorithm;
import fr.oxyl.genetic.core.showtimes.RandomShowtimeIndividualGenerator;
import fr.oxyl.genetic.core.showtimes.ShowtimeIndividual;

public class GeneticApplication {

  public static void main(String[] args) {
    GeneticAlgorithm.<ShowtimeIndividual>builder()
        .generator(new RandomShowtimeIndividualGenerator())
        .build()
        .run();
  }

}
