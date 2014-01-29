package com.clashsoft.nbt.tags.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;

public abstract class NBTTagNumber extends NamedBinaryTag implements NBTTagPrimitive
{
	public NBTTagNumber(byte type, String name)
	{
		super(type, name);
	}
	
	@Override
	public abstract Number getValue();
	
	@Override
	public boolean getBool()
	{
		return false;
	}

	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		this.writeNumber(output);
	}
	
	protected abstract char getPostfix();

	@Override
	public String writeString()
	{
		return this.getValue().toString() + this.getPostfix();
	}
	
	public abstract void writeNumber(DataOutput output) throws IOException;

	@Override
	public void readValue(DataInput input) throws IOException
	{
		this.readNumber(input);
	}

	@Override
	public void readString(String dataString)
	{
		int pos = dataString.indexOf(this.getPostfix());
		this.readNumber(pos == -1 ? dataString : dataString.substring(0, pos));
	}
	
	public abstract void readNumber(DataInput input) throws IOException;

	public abstract void readNumber(String number);
}
