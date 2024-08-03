package fr.oxyl.genetic.core.bytes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BytesIndividualTest {

  @Test
  void fitness() {
    var individual = new BytesIndividual((byte) 0b00000000);
    var individual1 = new BytesIndividual((byte) 0b11111111);
    var individual2 = new BytesIndividual((byte) 0b10010011);

    assertThat(individual.fitness()).isEqualTo(0);
    assertThat(individual1.fitness()).isEqualTo(8);
    assertThat(individual2.fitness()).isEqualTo(4);
  }

  @Test
  void crossover() {
    var parent1 = new BytesIndividual((byte) 0b00000000);
    var parent2 = new BytesIndividual((byte) 0b11111111);

    var actual1 = parent1.crossover(parent1);
    var actual2 = parent2.crossover(parent2);

    assertThat(actual1.getFirst().fitness()).isEqualTo(0);
    assertThat(actual2.getFirst().fitness()).isEqualTo(8);
  }

  @Test
  void mutate() {
    var individual = new BytesIndividual((byte) 0b00000000);

    var actual = individual.mutate();

    assertThat(actual.fitness()).isEqualTo(1);
  }

  @Test
  void compareTo() {
    var individual = new BytesIndividual((byte) 0b00000000);
    var individual1 = new BytesIndividual((byte) 0b11111111);

    assertThat(individual).isGreaterThan(individual1);
  }
}