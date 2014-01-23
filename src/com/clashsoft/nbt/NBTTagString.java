package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

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
	public String writeValueString(String prefix)
	{
		return "\"" + this.value.replace("\"", "\\\"") + "\"";
	}
	
	@Override
	public void readValueString(String dataString)
	{
		this.value = dataString.substring(1, dataString.length() - 1).replace("\\\"", "\"");
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
