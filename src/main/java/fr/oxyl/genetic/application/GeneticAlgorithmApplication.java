package fr.oxyl.genetic.application;

import fr.oxyl.genetic.application.GeneticAlgorithm.Parameters;
import fr.oxyl.genetic.factory.ByteIndividualFactory;

public class GeneticAlgorithmApplication {

  public static void main(String[] args) {
    var algorithm = GeneticAlgorithm.builder(ByteIndividualFactory::randomIndividual)
        .fitnessCalculator(ByteIndividualFactory.countBitOneCalculator())
        .crossoverStrategy(ByteIndividualFactory.twoPointsCrossover())
        .mutationStrategy(ByteIndividualFactory.randomBitMutation())
        .build();

    var parameters = Parameters.builder()
        .fitnessTarget(8)
        .build();

    algorithm.execute(parameters);
  }

}
