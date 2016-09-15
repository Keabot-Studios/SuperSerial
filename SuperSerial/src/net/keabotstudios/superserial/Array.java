package net.keabotstudios.superserial;

import net.keabotstudios.superserial.Type.ContainerType;
import net.keabotstudios.superserial.Type.DataType;

public class Array {
	
	public static final ContainerType CONTAINER_TYPE = ContainerType.ARRAY;
	private byte[] name;
	private DataType dataType;
	private int count;
	private byte[] data;
	
	private short[] shortData;
	private char[] characterData;
	private int[] integerData;
	private long[] longData;
	private float[] floatData;
	private double[] doubleData;
	private boolean[] booleanData;
	
	
	private Array(String name, DataType dataType) {
		setName(name);
		this.dataType = dataType;
	}
	
	public void setName(String name) {
		assert(name.length() < Short.MAX_VALUE);
		this.name = name.getBytes();
	}
	
	public int getBytes(byte[] dest, int pointer) {
		pointer = Serialization.write(dest, pointer, CONTAINER_TYPE.getType());
		pointer = Serialization.write(dest, pointer, (short) name.length);
		pointer = Serialization.write(dest, pointer, name);
		pointer = Serialization.write(dest, pointer, dataType.getType());
		pointer = Serialization.write(dest, pointer, count);
		switch(dataType) {
		case BYTE:
			pointer = Serialization.write(dest, pointer, data);
			break;
		case SHORT:
			pointer = Serialization.write(dest, pointer, shortData);
			break;
		case CHARACTER:
			pointer = Serialization.write(dest, pointer, characterData);
			break;
		case INTEGER:
			pointer = Serialization.write(dest, pointer, integerData);
			break;
		case LONG:
			pointer = Serialization.write(dest, pointer, longData);
			break;
		case FLOAT:
			pointer = Serialization.write(dest, pointer, floatData);
			break;
		case DOUBLE:
			pointer = Serialization.write(dest, pointer, doubleData);
			break;
		case BOOLEAN:
			pointer = Serialization.write(dest, pointer, booleanData);
			break;
		case UNKNOWN:
			break;
		}
		return pointer;
	}
	
	public int getSize() {
		return (DataType.BYTE.getSize() * 2) + DataType.SHORT.getSize() + name.length + DataType.INTEGER.getSize() + (getArrayLength() * dataType.getSize());
	}
	
	private int getArrayLength() {
		switch(dataType) {
		case BYTE:
			return data.length;
		case SHORT:
			return shortData.length;
		case CHARACTER:
			return characterData.length;
		case INTEGER:
			return integerData.length;
		case LONG:
			return longData.length;
		case FLOAT:
			return floatData.length;
		case DOUBLE:
			return doubleData.length;
		case BOOLEAN:
			return booleanData.length;
		default:
		case UNKNOWN:
			return 0;
		}
	}
	
	public static Array Byte(String name, byte[] data) {
		Array array = new Array(name, Type.DataType.BYTE);
		array.count = data.length;
		array.data = data;
		return array;
	}
	
	public static Array Short(String name, short[] data) {
		Array array = new Array(name, Type.DataType.SHORT);
		array.count = data.length;
		array.shortData = data;
		return array;
	}
	
	public static Array Character(String name, char[] data) {
		Array array = new Array(name, Type.DataType.CHARACTER);
		array.count = data.length;
		array.characterData = data;
		return array;
	}
	
	public static Array Integer(String name, int[] data) {
		Array array = new Array(name, Type.DataType.INTEGER);
		array.count = data.length;
		array.integerData = data;
		return array;
	}
	
	public static Array Long(String name, long[] data) {
		Array array = new Array(name, Type.DataType.LONG);
		array.count = data.length;
		array.longData = data;
		return array;
	}
	
	public static Array Float(String name, float[] data) {
		Array array = new Array(name, Type.DataType.FLOAT);
		array.count = data.length;
		array.floatData = data;
		return array;
	}
	
	public static Array Double(String name, double[] data) {
		Array array = new Array(name, Type.DataType.DOUBLE);
		array.count = data.length;
		array.doubleData = data;
		return array;
	}
	
	public static Array Boolean(String name, boolean[] data) {
		Array array = new Array(name, Type.DataType.BOOLEAN);
		array.count = data.length;
		array.booleanData = data;
		return array;
	}

}
