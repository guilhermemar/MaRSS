package net.marss.database;

public class ManagerException extends Exception{
	public static final int SOURCE_ALREADY_REGISTERED = -3;
	public static final int ERROR_UNDEFINED = -2;
	
	public static final String SOURCE_ALREADY_REGISTERED_MESSAGE = "Item existente";
	public static final String ERROR_UNDEFINED_MESSAGE = "Undefined error";
	
	private int code;
	
	public ManagerException (int code, String message)
	{
		super (message);
		this.code = code;
	}
	
	public int getCode ()
	{
		return this.code;
	}
}


