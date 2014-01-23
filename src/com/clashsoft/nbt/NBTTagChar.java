package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagChar extends NamedBinaryTag
{
	public char	value;
	
	public NBTTagChar(String name)
	{
		this(name, (char) 0);
	}
	
	public NBTTagChar(String name, char value)
	{
		super(TYPE_STRING, name);
		this.value = value;
	}
	
	@Override
	public Character getValue()
	{
		return this.value;
	}
	
	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.value == ((NBTTagChar) that).value;
	}
	
	@Override
	public String writeString()
	{
		return "'" + this.value + "'";
	}
	
	@Override
	public void readString(String dataString)
	{
		this.value = dataString.charAt(1);
	}
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		output.writeChar(this.value);
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		this.value = input.readChar();
	}
}
