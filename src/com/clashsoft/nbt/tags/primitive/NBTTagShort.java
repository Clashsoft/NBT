package com.clashsoft.nbt.tags.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTTagNumber
{
	public short	value;
	
	public NBTTagShort(String name)
	{
		this(name, (short) 0);
	}
	
	public NBTTagShort(String name, short value)
	{
		super(TYPE_SHORT, name, value);
		this.value = value;
	}
	
	@Override
	public char getPostfixChar()
	{
		return 'S';
	}
	
	@Override
	public Number readNumber(String number)
	{
		return this.value = Short.parseShort(number);
	}
	
	@Override
	public void writeNumber(DataOutput output) throws IOException
	{
		output.writeShort(this.value);
	}
	
	@Override
	public Number readNumber(DataInput input) throws IOException
	{
		return this.value = input.readShort();
	}
}
