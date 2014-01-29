package com.clashsoft.nbt.tags.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTTagNumber
{
	protected float	value;
	
	public NBTTagFloat(String name)
	{
		this(name, 0F);
	}
	
	public NBTTagFloat(String name, float value)
	{
		super(TYPE_FLOAT, name);
		this.value = value;
	}
	
	@Override
	public Float getValue()
	{
		return Float.valueOf(this.getFloat());
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
		return this.value;
	}
	
	@Override
	public double getDouble()
	{
		return (double) this.value;
	}
	
	@Override
	public char getPostfix()
	{
		return 'F';
	}
	
	@Override
	public void writeNumber(DataOutput output) throws IOException
	{
		output.writeFloat(this.value);
	}
	
	@Override
	public void readNumber(String number)
	{
		this.value = Float.parseFloat(number);
	}
	
	@Override
	public void readNumber(DataInput input) throws IOException
	{
		this.value = input.readFloat();
	}
}
