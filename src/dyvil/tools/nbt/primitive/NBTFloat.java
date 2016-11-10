package dyvil.tools.nbt.primitive;

import dyvil.tools.nbt.util.NBTInputStream;
import dyvil.tools.nbt.util.NBTOutputStream;
import dyvil.tools.nbt.NamedBinaryTag;

import java.io.IOException;

public class NBTFloat extends NBTNumber
{
	protected float value;

	public NBTFloat(float value)
	{
		this.value = value;
	}

	@Override
	public byte getType()
	{
		return NamedBinaryTag.TYPE_FLOAT;
	}

	@Override
	public Float getValue()
	{
		return this.value;
	}

	@Override
	public byte getByte()
	{
		return (byte) this.value;
	}

	@Override
	public short getShort()
	{
		return (short) this.value;
	}

	@Override
	public char getChar()
	{
		return (char) this.value;
	}

	@Override
	public int getInt()
	{
		return (int) this.value;
	}

	@Override
	public long getLong()
	{
		return (long) this.value;
	}

	@Override
	public float getFloat()
	{
		return this.value;
	}

	@Override
	public double getDouble()
	{
		return this.value;
	}

	@Override
	public char getPostfix()
	{
		return 'F';
	}

	@Override
	public void writeNumber(NBTOutputStream output) throws IOException
	{
		output.writeFloat(this.value);
	}

	@Override
	public void readNumber(NBTInputStream input) throws IOException
	{
		this.value = input.readFloat();
	}
}
