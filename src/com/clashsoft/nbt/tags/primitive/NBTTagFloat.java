package com.clashsoft.nbt.tags.primitive;

import java.io.IOException;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

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
		return this.value;
	}
	
	@Override
	public char getPostfix()
	{
		return 'F';
	}
	
	@Override
	public void writeNumber(NBTOutputStream output) throws IOException
	{
		output.writeFloat(this.value);
	}
	
	@Override
	public void readNumber(NBTInputStream input) throws IOException
	{
		this.value = input.readFloat();
	}
	
	@Override
	public void readNumber(String number)
	{
		this.value = Float.parseFloat(number);
	}
}
