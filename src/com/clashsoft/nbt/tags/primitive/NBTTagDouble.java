package com.clashsoft.nbt.tags.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTTagNumber
{
	protected double	value;
	
	public NBTTagDouble(String name)
	{
		this(name, 0D);
	}
	
	public NBTTagDouble(String name, double value)
	{
		super(TYPE_DOUBLE, name);
		this.value = value;
	}
	
	@Override
	public Double getValue()
	{
		return Double.valueOf(this.getDouble());
	}
	
	@Override
	public byte getByte()
	{
		return (byte) this.value;
	}
	
	@Override
	public short getShort()
	{
		return (short) this.value;
	}

	@Override
	public char getChar()
	{
		return (char) this.value;
	}
	
	@Override
	public int getInt()
	{
		return (int) this.value;
	}
	
	@Override
	public long getLong()
	{
		return (long) this.value;
	}
	
	@Override
	public float getFloat()
	{
		return (float) this.value;
	}
	
	@Override
	public double getDouble()
	{
		return this.value;
	}
	
	@Override
	public char getPostfix()
	{
		return 'D';
	}
	
	@Override
	public void writeNumber(DataOutput output) throws IOException
	{
		output.writeDouble(this.value);
	}
	
	@Override
	public void readNumber(String number)
	{
		this.value = Double.parseDouble(number);
	}
	
	@Override
	public void readNumber(DataInput input) throws IOException
	{
		this.value = input.readDouble();
	}
}
