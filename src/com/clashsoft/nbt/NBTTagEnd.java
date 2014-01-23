package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NamedBinaryTag
{
	protected NBTTagEnd(String name)
	{
		super(TYPE_END, name);
	}
	
	public NBTTagEnd()
	{
		this("");
	}
	
	@Override
	public Object getValue()
	{
		return null;
	}

	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return false;
	}
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
	}
	
	@Override
	public String writeString()
	{
		return "";
	}
	
	@Override
	public void readString(String dataString)
	{
	}
}
