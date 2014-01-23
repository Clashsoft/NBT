package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTTagNumber
{
	public long	value;
	
	public NBTTagLong(String name, long value)
	{
		super(TYPE_LONG, name, value);
		this.value = value;
	}
	
	@Override
	public char getPostfixChar()
	{
		return 'L';
	}
	
	@Override
	public Number readNumber(String number)
	{
		return Long.parseLong(number);
	}

	@Override
	public void writeNumber(DataOutput output) throws IOException
	{
		output.writeLong(this.value);
	}

	@Override
	public Number readNumber(DataInput input) throws IOException
	{
		return this.value = input.readLong();
	}
}
