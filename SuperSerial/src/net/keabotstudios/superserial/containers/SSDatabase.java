package net.keabotstudios.superserial.containers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.keabotstudios.superserial.Serialization;
import net.keabotstudios.superserial.Type.ContainerType;
import net.keabotstudios.superserial.Type.DataType;

public class SSDatabase {

	public static final byte[] HEADER = "SSDB".getBytes();
	public static final ContainerType CONTAINER_TYPE = ContainerType.DATABASE;
	private short nameLength;
	private byte[] name;
	private int size = DataType.BYTE.getSize() + (DataType.SHORT.getSize() * 2) + DataType.INTEGER.getSize() + HEADER.length;
	private short objectCount;
	public List<SSObject> objects = new ArrayList<SSObject>();
	
	private final int sizeOffset = 1 + 2 + 4;

	private SSDatabase() {}

	public SSDatabase(String name) {
		setName(name);
	}

	public void setName(String name) {
		assert (name.length() < Short.MAX_VALUE);
		if (this.name != null)
			size -= this.name.length;
		this.name = name.getBytes();
		this.nameLength = (short) this.name.length;
		size += this.name.length;
	}

	public String getName() {
		return new String(name);
	}

	public int writeBytes(byte[] dest, int pointer) {
		pointer = Serialization.write(dest, pointer, HEADER);
		pointer = Serialization.write(dest, pointer, CONTAINER_TYPE.getType());
		pointer = Serialization.write(dest, pointer, nameLength);
		pointer = Serialization.write(dest, pointer, name);
		pointer = Serialization.write(dest, pointer, size);

		pointer = Serialization.write(dest, pointer, objectCount);
		for (SSObject object : objects) {
			pointer = object.writeBytes(dest, pointer);
		}
		return pointer;
	}

	public int getSize() {
		return size;
	}

	public void addObject(SSObject object) {
		objects.add(object);
		size += object.getSize();
		objectCount = (short) objects.size();
	}

	public static SSDatabase Deserialize(byte[] data) {
		int pointer = 0;
		SSDatabase result;

		String header = Serialization.readString(data, pointer, 4);
		pointer += HEADER.length;
		byte containerType = Serialization.readByte(data, pointer);
		pointer += DataType.BYTE.getSize();
		assert (Arrays.equals(header.getBytes(), HEADER) && containerType == CONTAINER_TYPE.getType());

		result = new SSDatabase();
		result.nameLength = Serialization.readShort(data, pointer);
		pointer += DataType.SHORT.getSize();
		result.setName(Serialization.readString(data, pointer, result.nameLength));
		pointer += result.nameLength;
		result.size = Serialization.readInt(data, pointer);
		pointer += DataType.INTEGER.getSize();
		
		result.objectCount = Serialization.readShort(data, pointer);
		pointer += DataType.SHORT.getSize();
		
		int[] pointerRef = new int[] { pointer };
		for(int i = 0; i < result.objectCount; i++) {
			result.objects.add(SSObject.Deserialize(data, pointerRef));
		}
		return result;
	}
}
