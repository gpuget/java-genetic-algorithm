package fr.oxyl.genetic.core;

public class ByteIndividual extends Individual<Byte> {

  public ByteIndividual(Byte genes) {
    super(genes);
  }

  @Override
  public int genomeSize() {
    return Byte.SIZE;
  }

  @Override
  public String toString() {
    return this.fitness + " " + ByteUtils.toString(this.genome);
  }

}
