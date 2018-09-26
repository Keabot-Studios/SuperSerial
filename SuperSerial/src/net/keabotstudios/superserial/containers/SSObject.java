package net.keabotstudios.superserial.containers;

import java.util.ArrayList;
import java.util.List;

import net.keabotstudios.superserial.SSException;
import net.keabotstudios.superserial.SSSerialization;
import net.keabotstudios.superserial.SSType.SSContainerType;
import net.keabotstudios.superserial.SSType.SSDataType;

public class SSObject extends SSContainer {
	
	private short fieldCount;
	private List<SSField> fields = new ArrayList<SSField>();
	private short stringCount;
	private List<SSString> strings = new ArrayList<SSString>();
	private short arrayCount;
	private List<SSArray> arrays = new ArrayList<SSArray>();
	private short objectCount;
	private List<SSObject> objects = new ArrayList<SSObject>();
	
	private SSObject() {
		super(SSContainerType.OBJECT);
		this.size += SSDataType.SHORT.getSize() * 4;
	}
	
	public SSObject(String name) {
		this();
		setName(name);
	}

	public List<SSField> getFields() {
		return fields;
	}

	public List<SSString> getStrings() {
		return strings;
	}

	public List<SSArray> getArrays() {
		return arrays;
	}
	
	public List<SSObject> getObjects() {
		return objects;
	}
	
	public SSField getField(String name) {
		for (SSField field : fields)
			if(field.getName().equals(name)) return field;
		throw new SSException("Field does not exist: " + name);
	}
	
	public SSArray getArray(String name) {
		for (SSArray array : arrays)
			if(array.getName().equals(name)) return array;
		throw new SSException("Array does not exist: " + name);
	}
	
	public SSString getString(String name) {
		for (SSString string : strings)
			if(string.getName().equals(name)) return string;
		throw new SSException("String does not exist: " + name);
	}

	public SSObject getObject(String name) {
		for (SSObject object : objects)
			if(object.getName().equals(name)) return object;
		throw new SSException("Object does not exist: " + name);
	}

	public int writeBytes(byte[] dest, int pointer) {
		pointer = SSSerialization.write(dest, pointer, containerType.getType());
		pointer = SSSerialization.write(dest, pointer, nameLength);
		pointer = SSSerialization.write(dest, pointer, name);
		pointer = SSSerialization.write(dest, pointer, size);
		pointer = SSSerialization.write(dest, pointer, fieldCount);
		for(SSField field : fields)
			pointer = field.writeBytes(dest, pointer);
		pointer = SSSerialization.write(dest, pointer, stringCount);
		for(SSString string : strings)
			pointer = string.writeBytes(dest, pointer);
		pointer = SSSerialization.write(dest, pointer, arrayCount);
		for(SSArray array : arrays)
			pointer = array.writeBytes(dest, pointer);
		pointer = SSSerialization.write(dest, pointer, objectCount);
		for(SSObject object : objects)
			pointer = object.writeBytes(dest, pointer);
		return pointer;
	}

	public void addField(SSField field) {
		for(int i = 0; i < fields.size(); i++) {
			if(fields.get(i).getName().equals(field.getName())) {
				throw new SSException("Object \"" + getName() + "\" already contains field \"" + field.getName() + "\"");
			}
		}
		fields.add(field);
		size += field.getSize();
		fieldCount = (short) fields.size();
	}

	public void addString(SSString string) {
		for(int i = 0; i < strings.size(); i++) {
			if(strings.get(i).getName().equals(string.getName())) {
				throw new SSException("Object \"" + getName() + "\" already contains string \"" + string.getName() + "\"");
			}
		}
		strings.add(string);
		size += string.getSize();
		stringCount = (short) strings.size();
	}

	public void addArray(SSArray array) {
		for(int i = 0; i < arrays.size(); i++) {
			if(arrays.get(i).getName().equals(array.getName())) {
				throw new SSException("Object \"" + getName() + "\" already contains array \"" + array.getName() + "\"");
			}
		}
		arrays.add(array);
		size += array.getSize();
		arrayCount = (short) arrays.size();
	}
	
	public void addObject(SSObject object) {
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).getName().equals(object.getName())) {
				throw new SSException("Object \"" + getName() + "\" already contains object \"" + object.getName() + "\"");
			}
		}
		objects.add(object);
		size += object.getSize();
		objectCount = (short) objects.size();
	}
	
	public static SSObject Deserialize(byte[] data, int pointer) {
		SSObject result = new SSObject();
		byte containerType = SSSerialization.readByte(data, pointer);
		pointer += SSDataType.BYTE.getSize();
		assert(containerType == result.containerType.getType());
		result.nameLength = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		result.setName(SSSerialization.readString(data, pointer, result.nameLength));
		pointer += result.nameLength;
		result.size = SSSerialization.readInteger(data, pointer);
		pointer += SSDataType.INTEGER.getSize();
		result.fieldCount = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		for(int i = 0; i < result.fieldCount; i++) {
			SSField field = SSField.Deserialize(data, pointer);
			result.fields.add(field);
			pointer += field.getSize();
		}
		result.stringCount = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		for(int i = 0; i < result.stringCount; i++) {
			SSString string = SSString.Deserialize(data, pointer);
			result.strings.add(string);
			pointer += string.getSize();
		}
		result.arrayCount = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		for(int i = 0; i < result.arrayCount; i++) {
			SSArray array = SSArray.Deserialize(data, pointer);
			result.arrays.add(array);
			pointer += array.getSize();
		}
		result.objectCount = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		for(int i = 0; i < result.objectCount; i++) {
			SSObject object = SSObject.Deserialize(data, pointer);
			result.objects.add(object);
			pointer += object.getSize();
		}
		return result;
	}

}
