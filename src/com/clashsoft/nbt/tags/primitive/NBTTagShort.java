package com.clashsoft.nbt.tags.primitive;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

import java.io.IOException;

public class NBTTagShort extends NBTTagNumber
{
	protected short value;

	public NBTTagShort(short value)
	{
		this.value = value;
	}

	@Override
	public byte getType()
	{
		return TYPE_SHORT;
	}

	@Override
	public Short getValue()
	{
		return this.getShort();
	}

	@Override
	public byte getByte()
	{
		return (byte) this.value;
	}

	@Override
	public short getShort()
	{
		return this.value;
	}

	@Override
	public char getChar()
	{
		return (char) this.value;
	}

	@Override
	public int getInt()
	{
		return this.value;
	}

	@Override
	public long getLong()
	{
		return this.value;
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
		return 'S';
	}

	@Override
	public void writeNumber(NBTOutputStream output) throws IOException
	{
		output.writeShort(this.value);
	}

	@Override
	public void readNumber(NBTInputStream input) throws IOException
	{
		this.value = input.readShort();
	}

	@Override
	public void readNumber(String number)
	{
		this.value = Short.parseShort(number);
	}
}
