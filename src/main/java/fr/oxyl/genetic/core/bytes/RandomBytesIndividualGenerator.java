package fr.oxyl.genetic.core.bytes;

import fr.oxyl.genetic.api.PopulationGenerator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class RandomBytesIndividualGenerator implements PopulationGenerator<BytesIndividual> {

  private static BytesIndividual createRandomByteIndividual() {
    byte[] bytes = new byte[1];
    ThreadLocalRandom.current().nextBytes(bytes);
    return new BytesIndividual(bytes[0]);
  }

  @Override
  public List<BytesIndividual> generate(int size) {
    return IntStream.range(0, size)
        .mapToObj(i -> createRandomByteIndividual())
        .toList();
  }

}
