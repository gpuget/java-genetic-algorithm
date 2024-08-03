package fr.oxyl.genetic.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fr.oxyl.genetic.api.GeneticAlgorithm.Parameters;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GeneticAlgorithmTest {

  @Mock
  private PopulationGenerator<TestIndividual> generator;
  @Mock
  private PopulationEvaluator<TestIndividual> evaluator;
  @Mock
  private PopulationSelector<TestIndividual> selector;
  @Mock
  private Crossover<TestIndividual> crossover;
  @Mock
  private Mutator<TestIndividual> mutator;
  @InjectMocks
  private GeneticAlgorithm<TestIndividual> geneticAlgorithm;

  @Test
  void execute_noPopulation_noGeneration() {
    var parameters = Parameters.builder().build();
    when(this.generator.generate(anyInt())).thenReturn(Collections.emptyList());

    this.geneticAlgorithm.execute(parameters);

    verify(this.generator).generate(anyInt());
    verify(this.evaluator, never()).evaluate(anyList());
    verify(this.selector, never()).select(anyList());
    verify(this.crossover, never()).mate(any(), any());
    verify(this.mutator, never()).mutate(any());
  }

  @Test
  void execute() {
    var individuals = IntStream.rangeClosed(1, 10)
        .mapToObj(TestIndividual::new)
        .toList();
    var sorted = individuals
        .stream()
        .sorted()
        .toList();
    when(this.generator.generate(anyInt()))
        .thenReturn(individuals);
    when(this.evaluator.evaluate(anyList()))
        .thenReturn(sorted);
    when(this.selector.select(anyList()))
        .thenReturn(sorted);
    when(this.crossover.mate(any(), any()))
        .thenReturn(List.of(sorted.getFirst()));
    var parameters = Parameters.builder()
        .populationSize(10)
        .generationLimit(10)
        .crossoverProbability(1F)
        .mutationProbability(1F)
        .build();

    var actual = this.geneticAlgorithm.execute(parameters);

    verify(this.generator).generate(anyInt());
    verify(this.evaluator, times(10 + 1)).evaluate(anyList());
    verify(this.selector, times(10)).select(anyList());
    verify(this.crossover, times(10 * 10)).mate(any(), any());
    verify(this.mutator, times(10 * 10)).mutate(any());
    assertThat(actual.get().fitness()).isEqualTo(10);
  }

  @Test
  void execute_noCrossover() {
    var individuals = IntStream.rangeClosed(1, 10)
        .mapToObj(TestIndividual::new)
        .toList();
    var sorted = individuals
        .stream()
        .sorted()
        .toList();
    when(this.generator.generate(anyInt()))
        .thenReturn(individuals);
    when(this.evaluator.evaluate(anyList()))
        .thenReturn(sorted);
    when(this.selector.select(anyList()))
        .thenReturn(sorted);
    when(this.crossover.mate(any(), any()))
        .thenReturn(List.of(sorted.getFirst()));
    var parameters = Parameters.builder()
        .populationSize(10)
        .generationLimit(10)
        .crossoverProbability(0F)
        .mutationProbability(1F)
        .build();

    this.geneticAlgorithm.execute(parameters);

    verify(this.generator).generate(anyInt());
    verify(this.evaluator, times(10 + 1)).evaluate(anyList());
    verify(this.selector, times(10)).select(anyList());
    verify(this.crossover, times(10 * 1)).mate(any(), any());
    verify(this.mutator, times(10 * 10)).mutate(any());
  }

  @Test
  void execute_noMutation() {
    var individuals = IntStream.rangeClosed(1, 10)
        .mapToObj(TestIndividual::new)
        .toList();
    var sorted = individuals
        .stream()
        .sorted()
        .toList();
    when(this.generator.generate(anyInt()))
        .thenReturn(individuals);
    when(this.evaluator.evaluate(anyList()))
        .thenReturn(sorted);
    when(this.selector.select(anyList()))
        .thenReturn(sorted);
    when(this.crossover.mate(any(), any()))
        .thenReturn(List.of(sorted.getFirst()));
    var parameters = Parameters.builder()
        .populationSize(10)
        .generationLimit(10)
        .crossoverProbability(1F)
        .mutationProbability(0F)
        .build();

    this.geneticAlgorithm.execute(parameters);

    verify(this.generator).generate(anyInt());
    verify(this.evaluator, times(10 + 1)).evaluate(anyList());
    verify(this.selector, times(10)).select(anyList());
    verify(this.crossover, times(10 * 10)).mate(any(), any());
    verify(this.mutator, times(0)).mutate(any());
  }

}