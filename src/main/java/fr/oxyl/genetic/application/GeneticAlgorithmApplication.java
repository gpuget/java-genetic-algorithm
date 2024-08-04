package fr.oxyl.genetic.application;

import fr.oxyl.genetic.factory.ByteIndividualFactory;
import fr.oxyl.genetic.fitness.ByteIndividualFitnessCalculators;

public class GeneticAlgorithmApplication {

  public static void main(String[] args) {
    GeneticAlgorithm.builder(ByteIndividualFactory::random)
        .fitnessCalculator(ByteIndividualFitnessCalculators.ones())
        .build()
        .execute();
  }

}
