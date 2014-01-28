package com.clashsoft.nbt.tags.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;

public abstract class NBTTagNumber extends NamedBinaryTag
{
	protected Number	value;
	
	public NBTTagNumber(byte type, String name, Number value)
	{
		super(type, name);
		this.value = value;
	}
	
	@Override
	public Number getValue()
	{
		return this.value;
	}
	
	@Override
	public String writeString()
	{
		return this.value.toString() + this.getPostfixChar();
	}
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		this.writeNumber(output);
	}
	
	@Override
	public void readString(String dataString)
	{
		int pos = dataString.indexOf(this.getPostfixChar());
		this.value = this.readNumber(pos == -1 ? dataString : dataString.substring(0, pos));
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		this.value = this.readNumber(input);
	}
	
	public abstract char getPostfixChar();
	
	public abstract void writeNumber(DataOutput output) throws IOException;
	
	public abstract Number readNumber(String number);
	
	public abstract Number readNumber(DataInput input) throws IOException;
}
