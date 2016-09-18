package net.keabotstudios.superserial.containers;

import net.keabotstudios.superserial.SSSerialization;
import net.keabotstudios.superserial.SSType;
import net.keabotstudios.superserial.SSType.SSContainerType;
import net.keabotstudios.superserial.SSType.SSDataType;

public class SSField extends SSContainer {
	
	private SSDataType dataType;
	private byte[] data;
	
	private SSField(String name, SSDataType dataType) {
		this();
		setName(name);
		this.dataType = dataType;
		this.data = new byte[dataType.getSize()];
		this.size += data.length;
	}
	
	private SSField() {
		super(SSContainerType.FIELD);
		this.size += SSDataType.BYTE.getSize();
	}
	
	public int writeBytes(byte[] dest, int pointer) {
		pointer = SSSerialization.write(dest, pointer, containerType.getType());
		pointer = SSSerialization.write(dest, pointer, nameLength);
		pointer = SSSerialization.write(dest, pointer, name);
		pointer = SSSerialization.write(dest, pointer, size);
		pointer = SSSerialization.write(dest, pointer, dataType.getType());
		pointer = SSSerialization.write(dest, pointer, data);
		return pointer;
	}

	public static SSField Deserialize(byte[] data, int pointer) {
		SSField result = new SSField();
		byte containerType = SSSerialization.readByte(data, pointer);
		pointer += SSDataType.BYTE.getSize();
		assert (containerType == result.containerType.getType());
		result.nameLength = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		result.setName(SSSerialization.readString(data, pointer, result.nameLength));
		pointer += result.nameLength;
		result.size = SSSerialization.readInteger(data, pointer);
		pointer += SSDataType.INTEGER.getSize();
		result.dataType = SSDataType.getTypeFromByte(SSSerialization.readByte(data, pointer));
		pointer += SSDataType.BYTE.getSize();
		result.data = new byte[result.dataType.getSize()];
		SSSerialization.readBytes(data, pointer, result.data);
		pointer += result.dataType.getSize();
		return result;
	}

	public static SSField Byte(String name, byte value) {
		SSField field = new SSField(name, SSType.SSDataType.BYTE);
		SSSerialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Short(String name, short value) {
		SSField field = new SSField(name, SSType.SSDataType.SHORT);
		SSSerialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Character(String name, char value) {
		SSField field = new SSField(name, SSType.SSDataType.CHARACTER);
		SSSerialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Integer(String name, int value) {
		SSField field = new SSField(name, SSType.SSDataType.INTEGER);
		SSSerialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Long(String name, long value) {
		SSField field = new SSField(name, SSType.SSDataType.LONG);
		SSSerialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Float(String name, float value) {
		SSField field = new SSField(name, SSType.SSDataType.FLOAT);
		SSSerialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Double(String name, double value) {
		SSField field = new SSField(name, SSType.SSDataType.DOUBLE);
		SSSerialization.write(field.data, 0, value);
		return field;
	}

	public static SSField Boolean(String name, boolean value) {
		SSField field = new SSField(name, SSType.SSDataType.BOOLEAN);
		SSSerialization.write(field.data, 0, value);
		return field;
	}

}
