package net.keabotstudios.superserial;

import net.keabotstudios.superserial.Type.ContainerType;
import net.keabotstudios.superserial.Type.DataType;

public class Field {
	
	public static final ContainerType CONTAINER_TYPE = ContainerType.FIELD;
	private byte[] name;
	private DataType dataType;
	private byte[] data;
	
	private Field(String name, DataType dataType) {
		setName(name);
		this.dataType = dataType;
		this.data = new byte[dataType.getSize()];
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
		pointer = Serialization.write(dest, pointer, data);
		return pointer;
	}
	
	public int getSize() {
		assert(data.length == dataType.getSize());
		return (DataType.BYTE.getSize() * 2) + DataType.SHORT.getSize() + name.length + data.length;
	}

	public static Field Byte(String name, byte value) {
		Field field = new Field(name, Type.DataType.BYTE);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static Field Short(String name, short value) {
		Field field = new Field(name, Type.DataType.SHORT);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static Field Character(String name, char value) {
		Field field = new Field(name, Type.DataType.CHARACTER);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static Field Integer(String name, int value) {
		Field field = new Field(name, Type.DataType.INTEGER);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static Field Long(String name, long value) {
		Field field = new Field(name, Type.DataType.LONG);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static Field Float(String name, float value) {
		Field field = new Field(name, Type.DataType.FLOAT);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static Field Double(String name, double value) {
		Field field = new Field(name, Type.DataType.DOUBLE);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static Field Boolean(String name, boolean value) {
		Field field = new Field(name, Type.DataType.BOOLEAN);
		Serialization.write(field.data, 0, value);
		return field;
	}

}
