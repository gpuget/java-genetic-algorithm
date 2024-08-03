package fr.oxyl.genetic.application;

import fr.oxyl.genetic.core.GeneticAlgorithm;
import fr.oxyl.genetic.core.showtimes.RandomShowtimeIndividualGenerator;

public class GeneticApplication {

  public static void main(String[] args) {
    var runnable = GeneticAlgorithm.builder(new RandomShowtimeIndividualGenerator())
        .build();
    runnable.run();
  }

}
