package com.clashsoft.nbt.tags.primitive;

import java.io.IOException;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

public class NBTTagInteger extends NBTTagNumber
{
	protected int	value;
	
	public NBTTagInteger(String name)
	{
		this(name, 0);
	}
	
	public NBTTagInteger(String name, int value)
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
		return 'I';
	}
	
	@Override
	public String writeString()
	{
		return "" + this.value;
	}
	
	@Override
	public void writeNumber(NBTOutputStream output) throws IOException
	{
		output.writeInt(this.value);
	}
	
	@Override
	public void readNumber(NBTInputStream input) throws IOException
	{
		this.value = input.readInt();
	}
	
	@Override
	public void readNumber(String number)
	{
		this.value = Integer.parseInt(number);
	}
}
