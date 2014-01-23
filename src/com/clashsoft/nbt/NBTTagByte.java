package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTTagNumber
{
	public byte	value;
	
	public NBTTagByte(String name)
	{
		this(name, (byte) 0);
	}
	
	public NBTTagByte(String name, byte value)
	{
		super(TYPE_BYTE, name, value);
		this.value = value;
	}
	
	@Override
	public char getPostfixChar()
	{
		return 'B';
	}
	
	@Override
	public Number readNumber(String number)
	{
		return this.value = Byte.parseByte(number);
	}
	
	@Override
	public void writeNumber(DataOutput output) throws IOException
	{
		output.writeByte(this.value);
	}
	
	@Override
	public Number readNumber(DataInput input) throws IOException
	{
		return this.value = input.readByte();
	}
}
