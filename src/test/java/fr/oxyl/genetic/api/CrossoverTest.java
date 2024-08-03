package fr.oxyl.genetic.api;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import fr.oxyl.genetic.api.Crossover.DefaultCrossover;
import org.junit.jupiter.api.Test;

class CrossoverTest {

  private final Crossover<TestIndividual> crossover = new DefaultCrossover<>();

  @Test
  void mate() {
    var parent1 = mock(TestIndividual.class);
    var parent2 = mock(TestIndividual.class);

    this.crossover.mate(parent1, parent2);

    verify(parent1).crossover(parent2);
  }

}