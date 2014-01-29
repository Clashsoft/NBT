package com.clashsoft.nbt.tags.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTTagNumber
{
	protected short	value;
	
	public NBTTagShort(String name)
	{
		this(name, (short) 0);
	}
	
	public NBTTagShort(String name, short value)
	{
		super(TYPE_SHORT, name);
		this.value = value;
	}
	
	@Override
	public Short getValue()
	{
		return Short.valueOf(this.getShort());
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
		return 'S';
	}
	
	@Override
	public void readNumber(String number)
	{
		this.value = Short.parseShort(number);
	}
	
	@Override
	public void writeNumber(DataOutput output) throws IOException
	{
		output.writeShort(this.value);
	}
	
	@Override
	public void readNumber(DataInput input) throws IOException
	{
		this.value = input.readShort();
	}
}
