package com.clashsoft.nbt.tags.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;

public class NBTTagBoolean extends NamedBinaryTag
{
	protected boolean	value;
	
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
		return Boolean.valueOf(this.getBool());
	}
	
	public boolean getBool()
	{
		return this.value;
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
	public String writeString()
	{
		return this.value ? "true" : "false";
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		this.value = input.readBoolean();
	}
	
	@Override
	public void readString(String dataString)
	{
		this.value = Boolean.parseBoolean(dataString);
	}
}
