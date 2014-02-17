package com.clashsoft.nbt.tags.data;

import java.io.File;
import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

public class NBTTagFile extends NamedBinaryTag
{
	protected File	file;
	
	public NBTTagFile(String name)
	{
		this(name, null);
	}
	
	public NBTTagFile(String name, File file)
	{
		super(TYPE_FILE, name);
		this.file = file;
	}
	
	@Override
	public File getValue()
	{
		return this.getFile();
	}
	
	public File getFile()
	{
		return this.file;
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
		String name = this.file == null ? "null" : this.file.getPath();
		return "file " + name;
	}
	
	@Override
	public void readString(String dataString)
	{
		dataString = dataString.substring(5);
		if ("null".equals(dataString))
		{
			this.file = null;
		}
		else
		{
			this.file = new File(dataString);
		}
	}
}
