package fr.oxyl.genetic.core;

public interface ByteUtils {

  static String toString(byte b) {
    return String.format("%08d", Integer.parseInt(Integer.toBinaryString(b & 0xFF)));
  }

}
