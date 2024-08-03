package fr.oxyl.genetic.api;

import java.util.List;

public record TestIndividual(int fitness) implements Individual<TestIndividual> {

  @Override
  public List<TestIndividual> crossover(TestIndividual other) {
    return List.of(this, other);
  }

  @Override
  public TestIndividual mutate() {
    return this;
  }

}
