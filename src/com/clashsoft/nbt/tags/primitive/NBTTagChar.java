package com.clashsoft.nbt.tags.primitive;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

import java.io.IOException;

public class NBTTagChar extends NamedBinaryTag implements NBTTagPrimitive
{
	protected char value;

	public NBTTagChar(char value)
	{
		this.value = value;
	}

	@Override
	public byte getType()
	{
		return TYPE_CHAR;
	}

	@Override
	public Character getValue()
	{
		return this.value;
	}

	@Override
	public boolean getBool()
	{
		return false;
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
		return this.value;
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
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.value == ((NBTTagChar) that).value;
	}

	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		output.writeChar(this.value);
	}

	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		this.value = input.readChar();
	}

	@Override
	public void toString(String indent, StringBuilder buffer)
	{
		buffer.append('\'').append(this.value).append('\'');
	}
}
