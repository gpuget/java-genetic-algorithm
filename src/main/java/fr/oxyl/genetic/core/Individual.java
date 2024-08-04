package fr.oxyl.genetic.core;

public abstract class Individual<T> {

  protected final T genome;
  protected int fitness;

  protected Individual(T genome) {
    this.genome = genome;
  }

  public int fitness() {
    return fitness;
  }

  public T genome() {
    return this.genome;
  }

  public void setFitness(int fitness) {
    this.fitness = fitness;
  }

  public abstract int genomeSize();

}
