package com.clashsoft.nbt.tags.primitive;

import java.io.IOException;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

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
	public void writeNumber(NBTOutputStream output) throws IOException
	{
		output.writeDouble(this.value);
	}
	
	@Override
	public void readNumber(NBTInputStream input) throws IOException
	{
		this.value = input.readDouble();
	}
	
	@Override
	public void readNumber(String number)
	{
		this.value = Double.parseDouble(number);
	}
}
