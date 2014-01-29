package com.clashsoft.nbt.tags.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;

public class NBTTagChar extends NamedBinaryTag implements NBTTagPrimitive
{
	protected char	value;
	
	public NBTTagChar(String name)
	{
		this(name, (char) 0);
	}
	
	public NBTTagChar(String name, char value)
	{
		super(TYPE_STRING, name);
		this.value = value;
	}
	
	@Override
	public Character getValue()
	{
		return Character.valueOf(this.getChar());
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
		return (double) this.value;
	}

	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.value == ((NBTTagChar) that).value;
	}
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		output.writeChar(this.value);
	}

	@Override
	public String writeString()
	{
		return "'" + this.value + "'";
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		this.value = input.readChar();
	}

	@Override
	public void readString(String dataString)
	{
		this.value = dataString.charAt(1);
	}
}
