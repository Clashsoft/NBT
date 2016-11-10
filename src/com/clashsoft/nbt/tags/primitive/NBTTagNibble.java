package com.clashsoft.nbt.tags.primitive;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

import java.io.IOException;

public class NBTTagNibble extends NBTTagNumber
{
	protected byte value;

	public NBTTagNibble(byte value)
	{
		this.value = value;
	}

	@Override
	public byte getType()
	{
		return TYPE_NIBBLE;
	}

	@Override
	public Byte getValue()
	{
		return this.getByte();
	}

	@Override
	public byte getByte()
	{
		return this.value;
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
		return 'N';
	}

	@Override
	public void writeNumber(NBTOutputStream output) throws IOException
	{
		output.writeNibble(this.value);
	}

	@Override
	public void readNumber(NBTInputStream input) throws IOException
	{
		this.value = input.readNibble();
	}
}
