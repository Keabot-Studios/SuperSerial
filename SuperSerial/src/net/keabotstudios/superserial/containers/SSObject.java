package net.keabotstudios.superserial.containers;

import java.util.ArrayList;
import java.util.List;

import net.keabotstudios.superserial.Serialization;
import net.keabotstudios.superserial.Type.ContainerType;
import net.keabotstudios.superserial.Type.DataType;

public class SSObject {
	
	public static final ContainerType CONTAINER_TYPE = ContainerType.OBJECT;
	private short nameLength;
	private byte[] name;
	private int size = DataType.BYTE.getSize() + (DataType.SHORT.getSize() * 4) + DataType.INTEGER.getSize();
	private short fieldCount;
	private List<SSField> fields = new ArrayList<SSField>();
	private short stringCount;
	private List<SSString> strings = new ArrayList<SSString>();
	private short arrayCount;
	private List<SSArray> arrays = new ArrayList<SSArray>();
	
	private SSObject() {}
	
	public SSObject(String name) {
		setName(name);
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
		pointer = Serialization.write(dest, pointer, fieldCount);
		for(SSField field : fields)
			pointer = field.writeBytes(dest, pointer);
		pointer = Serialization.write(dest, pointer, stringCount);
		for(SSString string : strings)
			pointer = string.writeBytes(dest, pointer);
		pointer = Serialization.write(dest, pointer, arrayCount);
		for(SSArray array : arrays)
			pointer = array.writeBytes(dest, pointer);
		return pointer;
	}

	public int getSize() {
		return size;
	}

	public void addField(SSField field) {
		fields.add(field);
		size += field.getSize();
		fieldCount = (short) fields.size();
	}

	public void addString(SSString string) {
		strings.add(string);
		size += string.getSize();
		stringCount = (short) strings.size();
	}

	public void addArray(SSArray array) {
		arrays.add(array);
		size += array.getSize();
		arrayCount = (short) arrays.size();
	}
	
	public static SSObject Deserialize(byte[] data, int[] pointerRef) {
		int pointer = pointerRef[0];
		SSObject result;
		byte containerType = Serialization.readByte(data, pointer);
		assert(containerType == CONTAINER_TYPE.getType());
		result = new SSObject();
		result.nameLength = Serialization.readShort(data, pointer);
		pointer += DataType.SHORT.getSize();
		result.setName(Serialization.readString(data, pointer, result.nameLength));
		pointer += result.nameLength;
		result.size = Serialization.readInt(data, pointer);
		pointer += DataType.INTEGER.getSize();
		result.fieldCount = Serialization.readShort(data, pointer);
		pointer += DataType.SHORT.getSize();
		// fields
		result.stringCount = Serialization.readShort(data, pointer);
		pointer += DataType.SHORT.getSize();
		// strings
		result.arrayCount = Serialization.readShort(data, pointer);
		pointer += DataType.SHORT.getSize();
		// arrays
		pointerRef[0] = pointer;
		return result;
	}

}
