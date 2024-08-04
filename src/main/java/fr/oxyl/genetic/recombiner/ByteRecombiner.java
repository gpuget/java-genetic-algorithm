package fr.oxyl.genetic.recombiner;

import fr.oxyl.genetic.api.Recombiner;
import fr.oxyl.genetic.core.ByteUtils;
import java.util.ArrayList;
import java.util.List;

public final class ByteRecombiner implements Recombiner<Byte> {

  @Override
  public List<Byte> cut(Byte genome, int[] indexes) {
    String binaryString = ByteUtils.toString(genome);
    List<String> segments = new ArrayList<>(indexes.length + 1);
    int startIndex = 0;
    for (int index : indexes) {
      segments.add(binaryString.substring(startIndex, index + 1));
      startIndex = index;
    }
    segments.add(binaryString.substring(startIndex));

    return segments.stream()
        .map(segment -> (byte) Integer.parseInt(segment, 2))
        .toList();
  }

  @Override
  public Byte recombine(Byte genome1, Byte genome2) {
    return (byte) (genome1 | genome2);
  }

}
