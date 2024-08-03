package fr.oxyl.genetic.api.individuals.generators;

import fr.oxyl.genetic.api.individuals.Individual;
import fr.oxyl.genetic.api.individuals.Population;
import java.util.function.Supplier;

public interface PopulationGenerator<T extends Population<U>, U extends Individual> extends Supplier<T> {

  Population<U> generate(int count);

}
