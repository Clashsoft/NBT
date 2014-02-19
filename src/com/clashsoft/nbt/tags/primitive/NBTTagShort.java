package com.clashsoft.nbt.tags.primitive;

import java.io.IOException;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

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
