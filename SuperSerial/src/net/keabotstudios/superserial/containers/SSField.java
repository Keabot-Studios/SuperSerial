package net.keabotstudios.superserial.containers;

import net.keabotstudios.superserial.Serialization;
import net.keabotstudios.superserial.Type;
import net.keabotstudios.superserial.Type.ContainerType;
import net.keabotstudios.superserial.Type.DataType;

public class SSField {
	
	public static final ContainerType CONTAINER_TYPE = ContainerType.FIELD;
	private short nameLength;
	private byte[] name;
	private int size = (DataType.BYTE.getSize() * 2) + DataType.SHORT.getSize() + DataType.INTEGER.getSize();
	private DataType dataType;
	private byte[] data;
	
	private SSField(String name, DataType dataType) {
		setName(name);
		this.dataType = dataType;
		this.data = new byte[dataType.getSize()];
		this.size += data.length;
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
		pointer = Serialization.write(dest, pointer, data);
		return pointer;
	}
	
	public int getSize() {
		assert(data.length == dataType.getSize());
		return size;
	}

	public static SSField Byte(String name, byte value) {
		SSField field = new SSField(name, Type.DataType.BYTE);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Short(String name, short value) {
		SSField field = new SSField(name, Type.DataType.SHORT);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Character(String name, char value) {
		SSField field = new SSField(name, Type.DataType.CHARACTER);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Integer(String name, int value) {
		SSField field = new SSField(name, Type.DataType.INTEGER);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Long(String name, long value) {
		SSField field = new SSField(name, Type.DataType.LONG);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Float(String name, float value) {
		SSField field = new SSField(name, Type.DataType.FLOAT);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Double(String name, double value) {
		SSField field = new SSField(name, Type.DataType.DOUBLE);
		Serialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Boolean(String name, boolean value) {
		SSField field = new SSField(name, Type.DataType.BOOLEAN);
		Serialization.write(field.data, 0, value);
		return field;
	}

}
