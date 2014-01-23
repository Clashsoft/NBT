package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTTagNumber
{
	public float	value;
	
	public NBTTagFloat(String name, float value)
	{
		super(TYPE_FLOAT, name, value);
		this.value = value;
	}
	
	@Override
	public char getPostfixChar()
	{
		return 'F';
	}
	
	@Override
	public Number readNumber(String number)
	{
		return Float.parseFloat(number);
	}

	@Override
	public void writeNumber(DataOutput output) throws IOException
	{
		output.writeFloat(this.value);
	}

	@Override
	public Number readNumber(DataInput input) throws IOException
	{
		return this.value = input.readFloat();
	}
}
