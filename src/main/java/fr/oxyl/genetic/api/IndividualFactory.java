package fr.oxyl.genetic.api;

import fr.oxyl.genetic.core.Individual;
import java.util.function.Supplier;

@FunctionalInterface
public interface IndividualFactory<T extends Individual> extends Supplier<T> {

}
