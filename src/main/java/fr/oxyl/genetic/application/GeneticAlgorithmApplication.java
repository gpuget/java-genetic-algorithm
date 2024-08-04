package fr.oxyl.genetic.application;

import fr.oxyl.genetic.application.GeneticAlgorithm.Parameters;
import fr.oxyl.genetic.factory.ByteIndividualFactory;
import fr.oxyl.genetic.fitness.ByteIndividualFitnessCalculators;

public class GeneticAlgorithmApplication {

  public static void main(String[] args) {
    var algorithm = GeneticAlgorithm.builder(ByteIndividualFactory::randomIndividual)
        .fitnessCalculator(ByteIndividualFitnessCalculators.ones())
        .crossoverStrategy(ByteIndividualFactory.twoPointsCrossoverStrategy())
        .build();
    var parameters = Parameters.builder()
        .fitnessTarget(8)
        .build();
    algorithm.execute(parameters);
  }

}
