package com.clashsoft.nbt.tags.primitive;

import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

public class NBTTagBoolean extends NamedBinaryTag implements NBTTagPrimitive
{
	protected boolean	value;
	
	public NBTTagBoolean(String name)
	{
		this(name, false);
	}
	
	public NBTTagBoolean(String name, boolean value)
	{
		super(TYPE_BOOLEAN, name);
		this.value = value;
	}
	
	@Override
	public Boolean getValue()
	{
		return Boolean.valueOf(this.getBool());
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
	public String writeString()
	{
		return this.value ? "true" : "false";
	}
	
	@Override
	public void readString(String dataString)
	{
		this.value = Boolean.parseBoolean(dataString);
	}
}
