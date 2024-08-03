package fr.oxyl.genetic.core.bytes;

import fr.oxyl.genetic.api.Individual;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public record BytesIndividual(byte genes, int fitness) implements Individual<BytesIndividual> {

  public BytesIndividual(byte genes) {
    this(genes, computeFitness(genes));
  }

  private static int computeFitness(byte genes) {
    int fitness = 0;
    int value = genes & 0xFF;
    for (int i = 0; i < 8; i++) {
      if ((value & 0x01) == 1) {
        fitness++;
      }
      value >>= 1;
    }
    return fitness;
  }

  @Override
  public List<BytesIndividual> crossover(BytesIndividual parent2) {
    var random = ThreadLocalRandom.current();
    byte genes = 0x00;
    for (int i = 0; i < 8; i++) {
      var parent = random.nextBoolean() ? this : parent2;
      int gene = parent.genes() & (0x01 << i);
      genes |= gene;
    }
    return Collections.singletonList(new BytesIndividual(genes));
  }

  @Override
  public BytesIndividual mutate() {
    var random = ThreadLocalRandom.current();
    int gene = random.nextInt(0, 5);
    byte genes = (byte) ((this.genes & 0xFF) ^ (int) (Math.pow(2, gene)));
    return new BytesIndividual(genes);
  }

  @Override
  public String toString() {
    return String.format("%08d", Integer.parseInt(Integer.toBinaryString(this.genes & 0xFF)));
  }

}
