package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTTagNumber extends NamedBinaryTag
{	
	protected Number value;
	
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
	public final String writeValueString(String prefix)
	{
		return this.value.toString() + this.getPostfixChar();
	}
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		this.writeNumber(output);
	}
	
	@Override
	public void readValueString(String dataString)
	{
		this.value = this.readNumber(dataString.substring(0, dataString.indexOf(this.getPostfixChar())));
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
