package fr.oxyl.genetic.core;

import static org.assertj.core.api.Assertions.assertThat;

import fr.oxyl.genetic.api.TestIndividual;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class ElitismPopulationSelectorTest {

  private final ElitismPopulationSelector<TestIndividual> selector = new ElitismPopulationSelector<>(0.5F);

  @Test
  void select() {
    var list = IntStream.rangeClosed(1, 10).mapToObj(TestIndividual::new).toList();

    var actual = this.selector.select(list);

    assertThat(actual)
        .isNotSameAs(list)
        .hasSize(5);
  }
}