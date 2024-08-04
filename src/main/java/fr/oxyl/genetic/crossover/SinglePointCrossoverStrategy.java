package fr.oxyl.genetic.crossover;

import fr.oxyl.genetic.api.Recombiner;
import fr.oxyl.genetic.core.Individual;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Stream;

public final class SinglePointCrossoverStrategy<T, U extends Individual<T>> extends AbtractCrossoverStrategy<T, U> {

  SinglePointCrossoverStrategy(Recombiner<T> recombiner,
      Function<T, U> factory) {
    super(recombiner, factory);
  }

  @Override
  public Stream<T> offspringsGenomes(U parent1, U parent2) {
    int index = ThreadLocalRandom.current().nextInt(0, Math.min(parent1.genomeSize(), parent2.genomeSize()));
    var genomes1 = this.recombiner.cut(parent1.genome(), index);
    var genomes2 = this.recombiner.cut(parent2.genome(), index);
    return Stream.of(
        this.recombiner.recombine(genomes1.get(0), genomes2.get(1)),
        this.recombiner.recombine(genomes2.get(0), genomes1.get(1))
    );
  }

}
