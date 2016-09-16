package net.keabotstudios.superserial;

import java.nio.ByteBuffer;

import net.keabotstudios.superserial.Type.DataType;

public class Serialization {
	
	public static final byte[] HEADER = "SS".getBytes();
	public static final short VERSION = 0x0100;
	
	// WRITING ==================================================================================
	
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
	
	// READING ==================================================================================
	
	public static byte readByte(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.BYTE.getSize());
		return src[pointer];
	}
	
	public static short readShort(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.SHORT.getSize());
		return ByteBuffer.wrap(src, pointer, DataType.SHORT.getSize()).getShort();
	}
	
	public static char readChar(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.CHARACTER.getSize());
		return ByteBuffer.wrap(src, pointer, DataType.CHARACTER.getSize()).getChar();
	}
	
	public static int readInt(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.INTEGER.getSize());
		return ByteBuffer.wrap(src, pointer, DataType.INTEGER.getSize()).getInt();
	}
	
	public static long readLong(byte[] src, int pointer) {
		assert(src.length >= pointer + DataType.LONG.getSize());
		return ByteBuffer.wrap(src, pointer, DataType.LONG.getSize()).getLong();
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
	
	public static String readString(byte[] src, int pointer, int length) {
		assert(src.length >= pointer + length);
		return new String(src, pointer, length);
	}

}
