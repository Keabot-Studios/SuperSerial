package net.keabotstudios.superserial;

import net.keabotstudios.superserial.Type.DataType;

public class Serialization {
	
	public static final byte[] HEADER = "SS".getBytes();
	public static final short VERSION = 0x0100;
	
	public static int write(byte[] dest, int pointer, byte value) {
		assert(dest.length >= pointer + DataType.BYTE.getSize());
		dest[pointer++] = value;
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, short value) {
		assert(dest.length >= pointer + DataType.SHORT.getSize());
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, char value) {
		assert(dest.length >= pointer + DataType.CHARACTER.getSize());
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, int value) {
		assert(dest.length >= pointer + DataType.INTEGER.getSize());
		dest[pointer++] = (byte) ((value >> 24) & 0xff);
		dest[pointer++] = (byte) ((value >> 16) & 0xff);
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, long value) {
		assert(dest.length >= pointer + DataType.LONG.getSize());
		dest[pointer++] = (byte) ((value >> 56) & 0xff);
		dest[pointer++] = (byte) ((value >> 48) & 0xff);
		dest[pointer++] = (byte) ((value >> 40) & 0xff);
		dest[pointer++] = (byte) ((value >> 32) & 0xff);
		dest[pointer++] = (byte) ((value >> 24) & 0xff);
		dest[pointer++] = (byte) ((value >> 16) & 0xff);
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, float value) {
		assert(dest.length >= pointer + DataType.FLOAT.getSize());
		int data = Float.floatToIntBits(value);
		return write(dest, pointer, data);
	}
	
	public static int write(byte[] dest, int pointer, double value) {
		assert(dest.length >= pointer + DataType.DOUBLE.getSize());
		long data = Double.doubleToLongBits(value);
		return write(dest, pointer, data);
	}
	
	public static int write(byte[] dest, int pointer, boolean value) {
		assert(dest.length >= pointer + DataType.BOOLEAN.getSize());
		dest[pointer++] = (byte) (value ? 1 : 0);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, String string) {
		assert(dest.length >= pointer + DataType.SHORT.getSize() + (string.length() * DataType.BYTE.getSize()));
		pointer = write(dest, pointer, (short) string.length());
		return write(dest, pointer, string.getBytes());
	}
	
	public static int write(byte[] dest, int pointer, byte[] src) {
		assert(dest.length >= pointer + (DataType.BYTE.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, short[] src) {
		assert(dest.length >= pointer + (DataType.SHORT.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, char[] src) {
		assert(dest.length >= pointer + (DataType.CHARACTER.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, int[] src) {
		assert(dest.length >= pointer + (DataType.INTEGER.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, long[] src) {
		assert(dest.length >= pointer + (DataType.LONG.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, float[] src) {
		assert(dest.length >= pointer + (DataType.FLOAT.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, double[] src) {
		assert(dest.length >= pointer + (DataType.DOUBLE.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, boolean[] src) {
		assert(dest.length >= pointer + (DataType.BOOLEAN.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static byte readByte(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.BYTE.getSize());
		return src[pointer];
	}
	
	public static short readShort(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.SHORT.getSize());
		return (short) (src[pointer] << 8 | src[pointer + 1] << 0);
	}
	
	public static char readChar(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.CHARACTER.getSize());
		return (char) (src[pointer] << 8 | src[pointer + 1] << 0);
	}
	
	public static int readInt(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.INTEGER.getSize());
		return (int) ((src[pointer] << 24) | (src[pointer + 1] << 16) | (src[pointer + 2] << 8) | (src[pointer + 3] << 0));
	}
	
	public static long readLong(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.LONG.getSize());
		return (long) ((src[pointer] << 56) | (src[pointer + 1] << 48) | (src[pointer + 2] << 40) | (src[pointer + 3] << 32) |
						(src[pointer + 4] << 24) | (src[pointer + 5] << 16) | (src[pointer + 6] << 8) | (src[pointer + 7] << 0));
	}
	
	public static float readFloat(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.FLOAT.getSize());
		return Float.intBitsToFloat(readInt(src, pointer));
	}
	
	public static double readDouble(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.DOUBLE.getSize());
		return Double.longBitsToDouble(readLong(src, pointer));
	}
	
	public static boolean readBoolean(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.BOOLEAN.getSize());
		assert(src[pointer] == 0 || src[pointer] == 1);
		return src[pointer] != 0;
	}

}
