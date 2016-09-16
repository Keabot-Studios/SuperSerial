package net.keabotstudios.superserial.containers;

import net.keabotstudios.superserial.Serialization;
import net.keabotstudios.superserial.Type;
import net.keabotstudios.superserial.Type.ContainerType;
import net.keabotstudios.superserial.Type.DataType;

public class SSArray {
	
	public static final ContainerType CONTAINER_TYPE = ContainerType.ARRAY;
	private short nameLength;
	private byte[] name;
	private int size = (DataType.BYTE.getSize() * 2) + DataType.SHORT.getSize() + (DataType.INTEGER.getSize() * 2);
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
	
	
	private SSArray(String name, DataType dataType) {
		setName(name);
		this.dataType = dataType;
	}
	
	public void setName(String name) {
		assert(name.length() < Short.MAX_VALUE);
		if(this.name != null)
			size -= this.name.length;
		this.name = name.getBytes();
		this.nameLength = (short) this.name.length;
		size += this.name.length;
	}
	
	public String getName() {
		return new String(name);
	}
	
	public int writeBytes(byte[] dest, int pointer) {
		pointer = Serialization.write(dest, pointer, CONTAINER_TYPE.getType());
		pointer = Serialization.write(dest, pointer, nameLength);
		pointer = Serialization.write(dest, pointer, name);
		pointer = Serialization.write(dest, pointer, size);
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
		return size;
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
	
	public static SSArray Byte(String name, byte[] data) {
		SSArray array = new SSArray(name, Type.DataType.BYTE);
		array.count = data.length;
		array.data = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}
	
	public static SSArray Short(String name, short[] data) {
		SSArray array = new SSArray(name, Type.DataType.SHORT);
		array.count = data.length;
		array.shortData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}
	
	public static SSArray Character(String name, char[] data) {
		SSArray array = new SSArray(name, Type.DataType.CHARACTER);
		array.count = data.length;
		array.characterData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}
	
	public static SSArray Integer(String name, int[] data) {
		SSArray array = new SSArray(name, Type.DataType.INTEGER);
		array.count = data.length;
		array.integerData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}
	
	public static SSArray Long(String name, long[] data) {
		SSArray array = new SSArray(name, Type.DataType.LONG);
		array.count = data.length;
		array.longData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}
	
	public static SSArray Float(String name, float[] data) {
		SSArray array = new SSArray(name, Type.DataType.FLOAT);
		array.count = data.length;
		array.floatData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}
	
	public static SSArray Double(String name, double[] data) {
		SSArray array = new SSArray(name, Type.DataType.DOUBLE);
		array.count = data.length;
		array.doubleData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}
	
	public static SSArray Boolean(String name, boolean[] data) {
		SSArray array = new SSArray(name, Type.DataType.BOOLEAN);
		array.count = data.length;
		array.booleanData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}

}
