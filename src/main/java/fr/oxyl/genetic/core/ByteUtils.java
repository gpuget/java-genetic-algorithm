package fr.oxyl.genetic.core;

public interface ByteUtils {

  static String toString(byte b) {
    return String.format("%0" + Byte.SIZE + "d", Integer.parseInt(Integer.toBinaryString(b & 0xFF)));
  }

}
