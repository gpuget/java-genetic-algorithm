package fr.oxyl.genetic.core;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

import fr.oxyl.genetic.api.PopulationGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GeneticAlgorithmTest {

  @Mock
  private PopulationGenerator<?> populationGenerator;
  @InjectMocks
  private GeneticAlgorithm<?> geneticAlgorithm;


  @Test
  void run() {
    this.geneticAlgorithm.run();

    verify(this.populationGenerator).generate(anyInt());
  }

}