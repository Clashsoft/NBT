package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagBoolean extends NamedBinaryTag
{
	public boolean	value;
	
	public NBTTagBoolean(String name)
	{
		this(name, false);
	}
	
	public NBTTagBoolean(String name, boolean value)
	{
		super(TYPE_BOOLEAN, name);
		this.value = value;
	}
	
	@Override
	public Object getValue()
	{
		return this.value;
	}
	
	@Override
	public String writeValueString(String prefix)
	{
		return this.value ? "t" : "f";
	}
	
	@Override
	public void readValueString(String dataString)
	{
		this.value = "t".equals(dataString);
	}
	
	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.value == ((NBTTagBoolean) that).value;
	}

	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		output.writeBoolean(this.value);
	}

	@Override
	public void readValue(DataInput input) throws IOException
	{
		this.value = input.readBoolean();
	}
}
