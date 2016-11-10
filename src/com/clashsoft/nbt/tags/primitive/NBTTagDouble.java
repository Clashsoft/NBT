package com.clashsoft.nbt.tags.primitive;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

import java.io.IOException;

public class NBTTagDouble extends NBTTagNumber
{
	protected double value;

	public NBTTagDouble(double value)
	{
		this.value = value;
	}

	@Override
	public byte getType()
	{
		return TYPE_DOUBLE;
	}

	@Override
	public Double getValue()
	{
		return this.value;
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
}
