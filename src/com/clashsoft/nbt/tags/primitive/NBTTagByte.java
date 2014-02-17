package com.clashsoft.nbt.tags.primitive;

import java.io.IOException;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

public class NBTTagByte extends NBTTagNumber
{
	protected byte	value;
	
	public NBTTagByte(String name)
	{
		this(name, (byte) 0);
	}
	
	public NBTTagByte(String name, byte value)
	{
		super(TYPE_BYTE, name);
		this.value = value;
	}
	
	@Override
	public Byte getValue()
	{
		return Byte.valueOf(this.getByte());
	}
	
	@Override
	public byte getByte()
	{
		return this.value;
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
		return (double) this.value;
	}
	
	@Override
	public char getPostfix()
	{
		return 'B';
	}
	
	@Override
	public void writeNumber(NBTOutputStream output) throws IOException
	{
		output.writeByte(this.value);
	}
	
	@Override
	public void readNumber(NBTInputStream input) throws IOException
	{
		this.value = input.readByte();
	}

	@Override
	public void readNumber(String number)
	{
		this.value = Byte.parseByte(number);
	}
}
