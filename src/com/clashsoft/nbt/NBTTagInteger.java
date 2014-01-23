package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInteger extends NBTTagNumber
{
	public int	value;
	
	public NBTTagInteger(String name)
	{
		this(name, 0);
	}
	
	public NBTTagInteger(String name, int value)
	{
		super(TYPE_INT, name, value);
		this.value = value;
	}
	
	@Override
	public char getPostfixChar()
	{
		return 'I';
	}
	
	@Override
	public String writeString()
	{
		return "" + this.value;
	}
	
	@Override
	public Number readNumber(String number)
	{
		return this.value = Integer.parseInt(number);
	}
	
	@Override
	public void writeNumber(DataOutput output) throws IOException
	{
		output.writeInt(this.value);
	}
	
	@Override
	public Number readNumber(DataInput input) throws IOException
	{
		return this.value = input.readInt();
	}
}
