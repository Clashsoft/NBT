package com.clashsoft.nbt.tags.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;

public class NBTTagClass extends NamedBinaryTag
{
	public Class	clazz;
	
	public NBTTagClass(String name)
	{
		this(name, Object.class);
	}
	
	public NBTTagClass(String name, Class clazz)
	{
		super(TYPE_CLASS, name);
		this.clazz = clazz;
	}
	
	@Override
	public Class getValue()
	{
		return this.clazz;
	}
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		output.writeUTF(this.writeString());
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		String name = input.readUTF();
		this.readString(name);
	}
	
	@Override
	public String writeString()
	{
		String name = this.clazz == null ? "null" : this.clazz.getName();
		return "class " + name;
	}
	
	@Override
	public void readString(String dataString)
	{
		dataString = dataString.substring(6);
		if ("null".equals(dataString))
		{
			this.clazz = null;
		}
		else
		{
			try
			{
				this.clazz = Class.forName(dataString, false, ClassLoader.getSystemClassLoader());
			}
			catch (ClassNotFoundException ex)
			{
				this.clazz = null;
			}
		}
	}
}