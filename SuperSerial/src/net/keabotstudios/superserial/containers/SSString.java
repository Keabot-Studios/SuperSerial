package net.keabotstudios.superserial.containers;

import net.keabotstudios.superserial.Serialization;
import net.keabotstudios.superserial.Type.ContainerType;
import net.keabotstudios.superserial.Type.DataType;

public class SSString {
	
	public static final ContainerType CONTAINER_TYPE = ContainerType.STRING;
	private short nameLength;
	private byte[] name;
	private int size = DataType.BYTE.getSize() + (DataType.SHORT.getSize() * 2) + DataType.INTEGER.getSize();
	private short length = 0;
	private char[] data;
	
	public SSString(String name, String data) {
		setName(name);
		assert(data.length() < Short.MAX_VALUE);
		this.data = data.toCharArray();
		this.length = (short) this.data.length;
		this.size += (this.length * DataType.CHARACTER.getSize());
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
		pointer = Serialization.write(dest, pointer, length);
		pointer = Serialization.write(dest, pointer, data);
		return pointer;
	}
	
	public int getSize() {
		return size;
	}
	
}
