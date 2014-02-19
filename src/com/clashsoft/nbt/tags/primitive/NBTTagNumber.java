package com.clashsoft.nbt.tags.primitive;

import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

public abstract class NBTTagNumber extends NamedBinaryTag implements NBTTagPrimitive
{
	public NBTTagNumber(byte type, String name)
	{
		super(type, name);
	}
	
	@Override
	public abstract Number getValue();
	
	@Override
	public boolean getBool()
	{
		return false;
	}
	
	protected abstract char getPostfix();
	
	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		this.writeNumber(output);
	}
	
	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		this.readNumber(input);
	}
	
	@Override
	public String writeString()
	{
		return this.getValue().toString() + this.getPostfix();
	}
	
	@Override
	public void readString(String dataString)
	{
		int pos = dataString.indexOf(this.getPostfix());
		this.readNumber(pos == -1 ? dataString : dataString.substring(0, pos));
	}
	
	public abstract void writeNumber(NBTOutputStream output) throws IOException;
	
	public abstract void readNumber(NBTInputStream input) throws IOException;
	
	public abstract void readNumber(String number);
}
