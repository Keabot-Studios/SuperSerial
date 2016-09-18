package net.keabotstudios.superserial.containers;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.keabotstudios.superserial.SSSerialization;
import net.keabotstudios.superserial.SSType.SSContainerType;
import net.keabotstudios.superserial.SSType.SSDataType;

public class SSDatabase extends SSContainer {

	public static final byte[] HEADER = "SSDB".getBytes();
	public static final short VERSION = 0x0010;
	private short objectCount;
	public List<SSObject> objects = new ArrayList<SSObject>();

	private SSDatabase() {
		super(SSContainerType.DATABASE);
		this.size += (SSDataType.SHORT.getSize() * 2) + HEADER.length;
	}

	public SSDatabase(String name) {
		this();
		setName(name);
	}

	public int writeBytes(byte[] dest, int pointer) {
		pointer = SSSerialization.write(dest, pointer, HEADER);
		pointer = SSSerialization.write(dest, pointer, VERSION);
		pointer = SSSerialization.write(dest, pointer, containerType.getType());
		pointer = SSSerialization.write(dest, pointer, nameLength);
		pointer = SSSerialization.write(dest, pointer, name);
		pointer = SSSerialization.write(dest, pointer, size);
		
		pointer = SSSerialization.write(dest, pointer, objectCount);
		for (SSObject object : objects) {
			pointer = object.writeBytes(dest, pointer);
		}
		return pointer;
	}

	public void addObject(SSObject object) {
		objects.add(object);
		size += object.getSize();
		objectCount = (short) objects.size();
	}
	
	public SSObject getObject(String name) {
		for (SSObject object : objects)
			if(object.getName().equals(name)) return object;
		System.err.println("[ERROR]Object does not exist: " + name);
		return null;
	}

	public static SSDatabase Deserialize(byte[] data) {
		int pointer = 0;
		SSDatabase result = new SSDatabase();
		byte[] header = new byte[HEADER.length];
		SSSerialization.readBytes(data, pointer, header);
		pointer += HEADER.length;
		if(!Arrays.equals(header, HEADER)) {
			System.err.println("[ERROR]Invalid data type!");
			return null;
		}
		short version = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		if(version != VERSION) {
			System.err.println("[ERROR]Invalid SSDatabase version! Read: " + version + ", Expected: " + VERSION);
			return null;
		}
		byte containerType = SSSerialization.readByte(data, pointer);
		pointer += SSDataType.BYTE.getSize();	
		assert(containerType == result.containerType.getType());
		result.nameLength = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		result.setName(SSSerialization.readString(data, pointer, result.nameLength));
		pointer += result.nameLength;
		result.size = SSSerialization.readInteger(data, pointer);
		pointer += SSDataType.INTEGER.getSize();
		result.objectCount = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		for(int i = 0; i < result.objectCount; i++) {
			SSObject object = SSObject.Deserialize(data, pointer);
			result.objects.add(object);
			pointer += object.getSize();
		}
		return result;
	}
	
	public boolean serializeToFile(String path, ClassLoader loader) {
		byte[] data = new byte[getSize()];
		writeBytes(data, 0);
		try {
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
			stream.write(data);
			stream.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
