package fr.oxyl.genetic.recombiner;

import fr.oxyl.genetic.api.Recombiner;
import java.util.ArrayList;
import java.util.List;

public final class ByteRecombiner implements Recombiner<Byte> {

  @Override
  public List<Byte> cut(Byte genome, int[] indexes) {
    var result = new ArrayList<Byte>(indexes.length + 1);

    int mask;
    int previousIndex = 0;
    for (int index : indexes) {
      mask = 0xFF >> (Byte.SIZE - (index - previousIndex));
      mask = mask << Byte.SIZE - index;
      previousIndex = index;
      result.add((byte) (genome & mask));
    }
    mask = 0xFF >> previousIndex;
    result.add((byte) (genome & mask));

    return result;
  }

  @Override
  public Byte recombine(Byte genome1, Byte genome2) {
    return (byte) (genome1 | genome2);
  }

}
