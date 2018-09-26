package net.keabotstudios.superserial;

public class SSException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final String msg;
	
	public SSException(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String getMessage() {
		return msg;
	}

}
