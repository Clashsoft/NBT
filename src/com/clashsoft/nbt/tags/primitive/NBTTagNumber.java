package com.clashsoft.nbt.tags.primitive;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

import java.io.IOException;

public abstract class NBTTagNumber extends NamedBinaryTag implements NBTTagPrimitive
{
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
	public void toString(String indent, StringBuilder buffer)
	{
		buffer.append(this.getValue()).append(this.getPostfix());
	}

	public abstract void writeNumber(NBTOutputStream output) throws IOException;

	public abstract void readNumber(NBTInputStream input) throws IOException;
}
