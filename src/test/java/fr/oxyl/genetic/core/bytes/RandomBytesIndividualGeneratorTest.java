package fr.oxyl.genetic.core.bytes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RandomBytesIndividualGeneratorTest {

  private final RandomBytesIndividualGenerator generator = new RandomBytesIndividualGenerator();

  @Test
  void generate() {
    var actual = this.generator.generate(10);

    assertThat(actual).hasSize(10);
  }

}