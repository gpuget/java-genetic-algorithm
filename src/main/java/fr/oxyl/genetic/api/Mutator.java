package fr.oxyl.genetic.api;

public interface Mutator<T extends Individual<T>> {

  T mutate(T individual);

  class DefaultMutator<T extends Individual<T>> implements Mutator<T> {

    @Override
    public T mutate(T individual) {
      return individual.mutate();
    }

  }

}
