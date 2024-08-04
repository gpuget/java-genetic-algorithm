package fr.oxyl.genetic.core;

public class ByteIndividual extends Individual<Byte> {

  protected ByteIndividual(Byte genes) {
    super(genes);
  }

  public static ByteIndividual create(byte genes) {
    return new ByteIndividual(genes);
  }

  @Override
  public String toString() {
    return this.fitness + " " + String.format("%08d", Integer.parseInt(Integer.toBinaryString(this.genome & 0xFF)));
  }

}
