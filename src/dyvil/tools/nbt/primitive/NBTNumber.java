package dyvil.tools.nbt.primitive;

import dyvil.tools.nbt.NamedBinaryTag;
import dyvil.tools.nbt.util.NBTInputStream;
import dyvil.tools.nbt.util.NBTOutputStream;

import java.io.IOException;

public abstract class NBTNumber extends NamedBinaryTag implements NBTPrimitive
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
