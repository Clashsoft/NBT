package com.clashsoft.nbt.tags.primitive;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

import java.io.IOException;

public class NBTTagBoolean extends NamedBinaryTag implements NBTTagPrimitive
{
	protected boolean value;

	public NBTTagBoolean(boolean value)
	{
		this.value = value;
	}

	@Override
	public byte getType()
	{
		return TYPE_BOOLEAN;
	}

	@Override
	public Boolean getValue()
	{
		return this.value;
	}

	@Override
	public boolean getBool()
	{
		return this.value;
	}

	@Override
	public byte getByte()
	{
		return (byte) (this.value ? 1 : 0);
	}

	@Override
	public short getShort()
	{
		return (short) (this.value ? 1 : 0);
	}

	@Override
	public char getChar()
	{
		return this.value ? '1' : '0';
	}

	@Override
	public int getInt()
	{
		return this.value ? 1 : 0;
	}

	@Override
	public long getLong()
	{
		return this.value ? 1L : 0L;
	}

	@Override
	public float getFloat()
	{
		return this.value ? 1F : 0F;
	}

	@Override
	public double getDouble()
	{
		return this.value ? 1D : 0D;
	}

	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.value == ((NBTTagPrimitive) that).getBool();
	}

	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		output.writeBoolean(this.value);
	}

	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		this.value = input.readBoolean();
	}

	@Override
	public void toString(String indent, StringBuilder buffer)
	{
		buffer.append(this.value);
	}
}
