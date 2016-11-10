package com.clashsoft.nbt.tags.string;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

import java.io.IOException;

public class NBTTagString extends NamedBinaryTag
{
	protected String value;

	public NBTTagString(String value)
	{
		this.value = value;
	}

	@Override
	public byte getType()
	{
		return TYPE_STRING;
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
	public void toString(String indent, StringBuilder buffer)
	{
		buffer.append('"').append(this.value).append('"');
	}
}
