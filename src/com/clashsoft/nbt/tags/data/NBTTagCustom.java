package com.clashsoft.nbt.tags.data;

import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

public class NBTTagCustom extends NamedBinaryTag
{
	protected Object	value;
	
	public NBTTagCustom(String name)
	{
		this(name, null);
	}
	
	public NBTTagCustom(String name, Object value)
	{
		super(TYPE_CUSTOM, name);
		this.value = value;
	}
	
	@Override
	public Object getValue()
	{
		return this.value;
	}
	
	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		output.writeObject(this.value);
	}
	
	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		this.value = input.readObject();
	}
	
	@Override
	public String writeString()
	{
		return String.format("${%s}$@%x", String.valueOf(this.value), System.identityHashCode(this.value));
	}
	
	@Override
	public void readString(String dataString)
	{
	}
}
