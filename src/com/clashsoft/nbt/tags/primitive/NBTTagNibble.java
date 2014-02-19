package com.clashsoft.nbt.tags.primitive;

import java.io.IOException;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

public class NBTTagNibble extends NBTTagNumber
{
	protected byte	value;
	
	public NBTTagNibble(String name)
	{
		this(name, (byte) 0);
	}
	
	public NBTTagNibble(String name, byte value)
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
	
	@Override
	public void readNumber(String number)
	{
		this.value = Byte.parseByte(number);
	}
}
