package com.clashsoft.nbt.tags.data;

import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

public class NBTTagClass extends NamedBinaryTag
{
	protected Class	clazz;
	
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
		return this.getClazz();
	}
	
	public Class getClazz()
	{
		return this.clazz;
	}
	
	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		output.writeUTF(this.writeString());
	}
	
	@Override
	public void readValue(NBTInputStream input) throws IOException
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
