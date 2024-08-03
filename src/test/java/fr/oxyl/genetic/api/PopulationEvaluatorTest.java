package fr.oxyl.genetic.api;

import static org.assertj.core.api.Assertions.assertThat;

import fr.oxyl.genetic.api.PopulationEvaluator.DefaultPopulationEvaluator;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class PopulationEvaluatorTest {

  private final PopulationEvaluator<TestIndividual> evaluator = new DefaultPopulationEvaluator<>();

  @Test
  void evaluate() {
    var list = IntStream.rangeClosed(1, 10).mapToObj(TestIndividual::new).toList();

    var actual = this.evaluator.evaluate(list);

    assertThat(actual)
        .isNotSameAs(list)
        .isSorted();
  }

}