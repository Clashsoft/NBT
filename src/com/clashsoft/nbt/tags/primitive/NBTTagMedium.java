package com.clashsoft.nbt.tags.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagMedium extends NBTTagNumber
{
	protected int	value;
	
	public NBTTagMedium(String name)
	{
		this(name, 0);
	}
	
	public NBTTagMedium(String name, int value)
	{
		super(TYPE_INT, name);
		this.value = value;
	}
	
	@Override
	public Number getValue()
	{
		return Integer.valueOf(this.getInt());
	}
	
	@Override
	public byte getByte()
	{
		return (byte) this.value;
	}
	
	@Override
	public char getChar()
	{
		return (char) this.value;
	}
	
	@Override
	public short getShort()
	{
		return (short) this.value;
	}
	
	@Override
	public int getInt()
	{
		return this.value;
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
		return 'M';
	}
	
	@Override
	public String writeString()
	{
		return Integer.toString(this.value);
	}
	
	@Override
	public void writeNumber(DataOutput output) throws IOException
	{
		output.write((this.value >>> 16) & 0xFF);
		output.write((this.value >>> 8) & 0xFF);
		output.write((this.value >>> 0) & 0xFF);
	}
	
	@Override
	public void readNumber(String number)
	{
		this.value = Integer.parseInt(number);
	}
	
	@Override
	public void readNumber(DataInput input) throws IOException
	{
		int ch1 = input.readUnsignedByte();
        int ch2 = input.readUnsignedByte();
        int ch3 = input.readUnsignedByte();
        this.value = (ch1 << 16) | (ch2 << 8) | (ch1 << 0);
	}
}
