package com.clashsoft.nbt.util;

public class NBTParserException extends Exception
{
	private static final long	serialVersionUID	= -2866722424253452640L;

	public NBTParserException()
	{
		super();
	}

	public NBTParserException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public NBTParserException(String message)
	{
		super(message);
	}

	public NBTParserException(Throwable cause)
	{
		super(cause);
	}
}
