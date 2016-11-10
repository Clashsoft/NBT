package dyvil.tools.nbt.primitive;

import dyvil.tools.nbt.NamedBinaryTag;
import dyvil.tools.nbt.util.NBTInputStream;
import dyvil.tools.nbt.util.NBTOutputStream;

import java.io.IOException;

public class NBTString extends NamedBinaryTag
{
	protected String value;

	public NBTString(String value)
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
		return this.value.equals(((NBTString) that).value);
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
