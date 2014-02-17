package com.clashsoft.nbt.tags.string;

import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;
import com.clashsoft.nbt.util.NBTParser;

public class NBTTagString extends NamedBinaryTag
{
	protected String	value;
	
	public NBTTagString(String name)
	{
		this(name, "");
	}
	
	public NBTTagString(String name, String value)
	{
		super(TYPE_STRING, name);
		this.value = value;
	}
	
	@Override
	public String getValue()
	{
		return this.getString();
	}
	
	public String getString()
	{
		return this.value;
	}
	
	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.value.equals(((NBTTagString) that).value);
	}
	
	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		output.writeString(this.value);
	}

	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		this.value = input.readString();
	}

	@Override
	public String writeString()
	{
		return "\"" + this.value + "\"";
	}
	
	@Override
	public void readString(String dataString)
	{
		this.value = NBTParser.parseString(dataString);
	}
}
