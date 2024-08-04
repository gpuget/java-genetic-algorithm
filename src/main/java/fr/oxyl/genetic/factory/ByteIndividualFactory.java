package fr.oxyl.genetic.factory;

import fr.oxyl.genetic.core.ByteIndividual;
import java.util.concurrent.ThreadLocalRandom;

public interface ByteIndividualFactory {

  static ByteIndividual random() {
    byte[] bytes = new byte[1];
    ThreadLocalRandom.current().nextBytes(bytes);
    return ByteIndividual.create(bytes[0]);
  }

}
