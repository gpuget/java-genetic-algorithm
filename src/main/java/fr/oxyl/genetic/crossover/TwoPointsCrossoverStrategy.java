package fr.oxyl.genetic.crossover;

import fr.oxyl.genetic.api.Recombiner;
import fr.oxyl.genetic.core.Individual;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Stream;

public final class TwoPointsCrossoverStrategy<T, U extends Individual<T>> extends AbtractCrossoverStrategy<T, U> {

  TwoPointsCrossoverStrategy(Recombiner<T> recombiner,
      Function<T, U> factory) {
    super(recombiner, factory);
  }

  @Override
  public Stream<T> offspringsGenomes(U parent1, U parent2) {
    var random = ThreadLocalRandom.current();
    int min = Math.min(parent1.genomeSize(), parent2.genomeSize());
    int index1 = random.nextInt(0, min);
    int index2;
    do {
      index2 = random.nextInt(0, min);
    } while (index2 == index1 && min > 1);
    int[] indexes = new int[]{ index1, index2 };
    Arrays.sort(indexes);

    var genomes1 = this.recombiner.cut(parent1.genome(), indexes);
    var genomes2 = this.recombiner.cut(parent2.genome(), indexes);
    return Stream.of(
        this.recombiner.recombine(this.recombiner.recombine(genomes1.get(0), genomes2.get(1)), genomes1.get(2)),
        this.recombiner.recombine(this.recombiner.recombine(genomes2.get(0), genomes1.get(1)), genomes2.get(2))
    );
  }

}
