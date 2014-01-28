package com.clashsoft.nbt.tags.string;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.util.NBTParser;

public class NBTTagString extends NamedBinaryTag
{
	public String	value;
	
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
		return this.value;
	}
	
	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.value.equals(((NBTTagString) that).value);
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
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		output.writeUTF(this.value);
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		this.value = input.readUTF();
	}
}
