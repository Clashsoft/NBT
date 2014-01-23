package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTTagNumber
{
	public double	value;
	
	public NBTTagDouble(String name)
	{
		this(name, 0D);
	}
	
	public NBTTagDouble(String name, double value)
	{
		super(TYPE_DOUBLE, name, value);
		this.value = value;
	}
	
	@Override
	public char getPostfixChar()
	{
		return 'D';
	}
	
	@Override
	public Number readNumber(String number)
	{
		return this.value = Double.parseDouble(number);
	}
	
	@Override
	public void writeNumber(DataOutput output) throws IOException
	{
		output.writeDouble(this.value);
	}
	
	@Override
	public Number readNumber(DataInput input) throws IOException
	{
		return this.value = input.readDouble();
	}
}
