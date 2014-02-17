package com.clashsoft.nbt.tags.string;

import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;
import com.clashsoft.nbt.util.NBTParser;

public class NBTTagLongString extends NamedBinaryTag
{
	public String	value;
	
	public NBTTagLongString(String name)
	{
		this(name, "");
	}
	
	public NBTTagLongString(String name, String value)
	{
		super(TYPE_STRING_LONG, name);
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
		return this.value.equals(((NBTTagLongString) that).value);
	}
	
	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		int len = this.value.length();
		for (int i = 0; i < len; i++)
		{
			output.writeChar(this.value.charAt(i));
		}
	}

	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		int len = input.readInt();
		char[] chars = new char[len];
		for (int i = 0; i < len; i++)
		{
			chars[i] = input.readChar();
		}
		this.value = new String(chars);
	}

	@Override
	public String writeString()
	{
		return "\"" + this.value.toString() + "\"";
	}
	
	@Override
	public void readString(String dataString)
	{
		this.value = NBTParser.parseString(dataString);
	}
}
