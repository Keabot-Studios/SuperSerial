package net.keabotstudios.superserial;

public class Type {
	public static enum ContainerType {
		UNKNOWN((byte) 0),
		FIELD((byte) 1),
		ARRAY((byte) 2),
		OBJECT((byte) 3);
		
		private final byte type;
		
		private ContainerType(byte type) {
			this.type = type;
		}
		
		public byte getType() {
			return type;
		}
		
		public static ContainerType getTypeFromByte(byte b) {
			for(ContainerType ct : ContainerType.values()) {
				if(ct.getType() == b) {
					return ct;
				}
			}
			return UNKNOWN;
		}
	}
	
	public static enum DataType {
		UNKNOWN((byte) 0, 0),
		BYTE((byte) 1, Byte.BYTES),
		SHORT((byte) 2, Short.BYTES),
		CHARACTER((byte) 3, Character.BYTES),
		INTEGER((byte) 4, Integer.BYTES),
		LONG((byte) 5, Long.BYTES),
		FLOAT((byte) 6, Float.BYTES),
		DOUBLE((byte) 7, Double.BYTES),
		BOOLEAN((byte) 8, 1);
		
		private final byte type;
		private final int size;
		
		private DataType(byte type, int size) {
			this.type = type;
			this.size = size;
		}
		
		public byte getType() {
			return type;
		}
		
		public int getSize() {
			if(this == UNKNOWN)
				assert(false);
			return size;
		}
		
		public static DataType getTypeFromByte(byte b) {
			for(DataType ct : DataType.values()) {
				if(ct.getType() == b) {
					return ct;
				}
			}
			return UNKNOWN;
		}
	}

}
