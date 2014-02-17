package com.clashsoft.nbt.tags;

import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

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
	public void writeValue(NBTOutputStream output) throws IOException
	{
	}
	
	@Override
	public void readValue(NBTInputStream input) throws IOException
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
