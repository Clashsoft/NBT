package com.clashsoft.nbt.tags.primitive;

import java.io.IOException;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

public class NBTTagLong extends NBTTagNumber
{
	protected long	value;
	
	public NBTTagLong(String name)
	{
		this(name, 0L);
	}
	
	public NBTTagLong(String name, long value)
	{
		super(TYPE_LONG, name);
		this.value = value;
	}
	
	@Override
	public Long getValue()
	{
		return Long.valueOf(this.getLong());
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
		return this.value;
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
		return 'L';
	}
	
	@Override
	public void writeNumber(NBTOutputStream output) throws IOException
	{
		output.writeLong(this.value);
	}

	@Override
	public void readNumber(NBTInputStream input) throws IOException
	{
		this.value = input.readLong();
	}

	@Override
	public void readNumber(String number)
	{
		this.value = Long.parseLong(number);
	}
}
