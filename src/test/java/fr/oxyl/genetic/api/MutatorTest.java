package fr.oxyl.genetic.api;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import fr.oxyl.genetic.api.Mutator.DefaultMutator;
import org.junit.jupiter.api.Test;

class MutatorTest {

  private final Mutator<TestIndividual> mutator = new DefaultMutator<>();

  @Test
  void mate() {
    var individual = mock(TestIndividual.class);

    this.mutator.mutate(individual);

    verify(individual).mutate();
  }

}