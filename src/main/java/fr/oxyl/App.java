package fr.oxyl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App {

  private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

  public static void main(String[] args) {
    int populationSize = 10_000;
    int maxGeneration = 1_000;
    float elitismRate = 0.1F;
    float mutationRate = 0.1F;

    long start = System.currentTimeMillis();
    var population = new ShowtimeIndividualGenerator().generate(populationSize);
    for (int generation = 0; generation < maxGeneration; generation++) {
      var evaluatedPopulation = evaluate(population);

      if (generation < maxGeneration - 1) {
        population = selection(elitismRate, evaluatedPopulation);
        population.addAll(crossover(evaluatedPopulation, population.size()));
        population = mutation(mutationRate, population);
      } else {
        var first = evaluatedPopulation.getFirst();
        System.out.println("Best individual: " + first);
      }
    }
    System.out.println(System.currentTimeMillis() - start);
  }

  private static <T extends Individual<T>> List<T> evaluate(List<T> nextGeneration) {
    var generation = new ArrayList<>(nextGeneration);
    Collections.sort(generation);
    return generation;
  }

  private static <T extends Individual<T>> List<T> selection(float percent, List<T> generation) {
    var nextGeneration = new LinkedList<T>();

    int best = (int) (generation.size() * percent);
    for (int j = 0; j < best; j++) {
      nextGeneration.add(generation.get(j));
    }

    return nextGeneration;
  }

  private static <T extends Individual<T>> List<T> crossover(List<T> generation, int startIndex) {
    var offsprings = new LinkedList<T>();
    offsprings.add(generation.get(0).crossover(generation.get(1)));

    for (int i = startIndex + 1; i < generation.size(); i++) {
      var parent1 = generation.get(RANDOM.nextInt(0, generation.size()));
      var parent2 = generation.get(RANDOM.nextInt(0, generation.size()));
      offsprings.add(parent1.crossover(parent2));
    }

    return offsprings;
  }

  private static <T extends Individual<T>> List<T> mutation(float mutationRate, List<T> generation) {
    return generation.stream()
        .map(individual -> RANDOM.nextDouble() < mutationRate ? individual.mutate() : individual)
        .toList();
  }

  private interface Individual<T extends Individual<?>> extends Comparable<T> {

    T crossover(T other);

    T mutate();

    int fitness();

    @Override
    default int compareTo(T o) {
      return o.fitness() - fitness();
    }

  }

  private record ByteIndividual(byte genes, int fitness) implements Individual<ByteIndividual> {

    private ByteIndividual(byte genes) {
      this(genes, computeFitness(genes));
    }

    private static int computeFitness(byte genes) {
      int fitness = 0;
      int value = genes & 0xFF;
      for (int i = 0; i < 8; i++) {
        if ((value & 0x01) == 0) {
          fitness++;
        }
        value >>= 1;
      }
      return fitness;
    }

    @Override
    public ByteIndividual crossover(ByteIndividual parent2) {
      byte genes = 0x00;
      for (int i = 0; i < 8; i++) {
        var parent = RANDOM.nextBoolean() ? this : parent2;
        int gene = parent.genes() & (0x01 << i);
        genes |= gene;
      }
      return new ByteIndividual(genes);
    }

    @Override
    public ByteIndividual mutate() {
      int gene = RANDOM.nextInt(0, 9);
      byte genes = (byte) ((this.genes & 0xFF) ^ gene);
      return new ByteIndividual(genes);
    }

    @Override
    public String toString() {
      return String.format("%08d", Integer.parseInt(Integer.toBinaryString(this.genes & 0xFF)));
    }

  }

  private record ShowtimeIndividual(List<Showtime> showtimes, int fitness) implements Individual<ShowtimeIndividual> {

    public ShowtimeIndividual(List<Showtime> showtimes) {
      this(showtimes, computeFitness(showtimes));
    }

    private static int computeFitness(List<Showtime> showtimes) {
      int fitness = 0;
      var movies = new LinkedHashSet<Movie>();

      for (int i = 0; i < showtimes.size() - 1; i++) {
        var current = showtimes.get(i);
        var next = showtimes.get(i + 1);

        if (movies.contains(current.movie())) {
          return 0;
        }

        if (!current.time().toLocalDate().isEqual(next.time().toLocalDate())) {
          return 0;
        }

        if (current.time().isAfter(next.time())) {
          return 0;
        }

        if (next.time().isBefore(current.end())) {
          return 0;
        }

        fitness += (int) current.movie().duration().toMinutes();
        fitness -= (int) Duration.between(current.time().plus(current.duration()), next.time()).toMinutes();
        movies.add(current.movie());
        if (i == showtimes.size() - 2) {
          if (movies.contains(next.movie())) {
            return 0;
          }
          fitness += (int) next.movie().duration().toMinutes();
        }
      }

      return fitness;
    }

    @Override
    public ShowtimeIndividual crossover(ShowtimeIndividual other) {
      var showtimes = new LinkedList<Showtime>();

      if (showtimes().isEmpty()) {
        showtimes.addAll(other.showtimes());
      } else {
        int index = RANDOM.nextInt(0, showtimes().size());
        var time = showtimes().get(index).time();
        showtimes().stream().filter(showtime -> !showtime.time().isAfter(time)).forEach(showtimes::add);
        other.showtimes().stream().filter(showtime -> showtime.time().isAfter(time)).forEach(showtimes::add);
      }

      return new ShowtimeIndividual(showtimes);
    }

    @Override
    public ShowtimeIndividual mutate() {
      var showtimes = new LinkedList<>(showtimes());
      if (showtimes().size() > 1) {
        int i = RANDOM.nextInt(0, showtimes().size());
        int j = RANDOM.nextInt(0, showtimes().size());
        Collections.swap(showtimes, i, j);
      }
      return new ShowtimeIndividual(showtimes);
    }

    @Override
    public String toString() {
      return showtimes().stream()
          .map(Showtime::toString)
          .collect(Collectors.joining(",\n", Duration.ofMinutes(fitness()) + " [\n", "\n]"));
    }
  }

  private record Showtime(LocalDateTime time, Movie movie, String room) {

    private static final int DURATION_EXTENSION_IN_MINUTES = 15;

    private Duration duration() {
      return movie.duration().plus(Duration.ofMinutes(DURATION_EXTENSION_IN_MINUTES));
    }

    private LocalDateTime end() {
      return this.time.plus(duration());
    }

    @Override
    public String toString() {
      return "Showtime{" +
             "time=" + time +
             ", end=" + end() +
             ", movie=" + movie +
             ", room='" + room + '\'' +
             '}';
    }
  }

  private record Movie(String name, Duration duration) {

  }

  private interface PopulationGenerator<T extends Individual<T>> {

    List<T> generate(int size);

  }

  private static class ByteIndividualGenerator implements PopulationGenerator<ByteIndividual> {

    @Override
    public List<ByteIndividual> generate(int size) {
      return IntStream.range(0, size)
          .mapToObj(i -> createRandomByteIndividual())
          .toList();
    }

    private static ByteIndividual createRandomByteIndividual() {
      byte[] bytes = new byte[1];
      RANDOM.nextBytes(bytes);
      return new ByteIndividual(bytes[0]);
    }

  }

  private static class ShowtimeIndividualGenerator implements PopulationGenerator<ShowtimeIndividual> {

    @Override
    public List<ShowtimeIndividual> generate(int size) {
      var showtimes = getShowtimes();
      return IntStream.range(0, size)
          .mapToObj(i -> createRandomShowtimeIndividual(showtimes))
          .toList();
    }

    private static List<Showtime> getShowtimes() {
      String csv = """
          10;15;Shaun Of The Dead;1;39
          14;00;Hot Fuzz;2;01
          11;00;The World's End;2;48
          13;00;Scott Pilgrim vs. the World;1;52
          """;
      return Arrays.stream(csv.split("\n"))
          .filter(line -> !line.startsWith("#") && !line.startsWith("//"))
          .map(line -> {
            String[] parts = line.split(";");
            return new Showtime(
                LocalDateTime.of(
                    LocalDate.now(),
                    LocalTime.of(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1])
                    )
                ),
                new Movie(
                    parts[2],
                    Duration.ofMinutes(
                        Integer.parseInt(parts[3]) * 60L + Integer.parseInt(parts[4])
                    )
                ),
                "Room"
            );
          })
          .toList();
    }

    private static List<Showtime> createShowtimes() {
      return Stream.generate(() -> new Movie("Movie " + RANDOM.nextInt(0, 10000),
              Duration.of(RANDOM.nextInt(90, 180), ChronoUnit.MINUTES)))
          .limit(10)
          .flatMap(movie -> Stream.generate(() -> createRandomShowtime(movie)).limit(10))
          .toList();
    }

    private static Showtime createRandomShowtime(Movie movie) {
      var now = LocalDate.now();
      int hour = RANDOM.nextInt(10, 24);
      int minute = RANDOM.nextInt(0, 6) * 10;
      var date = LocalDateTime.of(now, LocalTime.of(hour, minute));
      return new Showtime(date, movie, "Room " + RANDOM.nextInt(0, 10000));
    }


    private static ShowtimeIndividual createRandomShowtimeIndividual(List<Showtime> showtimes) {
      return new ShowtimeIndividual(Stream.generate(() -> showtimes.get(RANDOM.nextInt(0, showtimes.size())))
          .limit(RANDOM.nextInt(1, 10))
          .toList());
    }

  }

}
