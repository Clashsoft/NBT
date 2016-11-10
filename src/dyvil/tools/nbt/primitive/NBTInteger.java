package dyvil.tools.nbt.primitive;

import dyvil.tools.nbt.util.NBTInputStream;
import dyvil.tools.nbt.util.NBTOutputStream;

import java.io.IOException;

public class NBTInteger extends NBTNumber
{
	protected int value;

	public NBTInteger(int value)
	{
		this.value = value;
	}

	@Override
	public byte getType()
	{
		return TYPE_INT;
	}

	@Override
	public Integer getValue()
	{
		return this.value;
	}

	@Override
	public byte getByte()
	{
		return (byte) this.value;
	}

	@Override
	public char getChar()
	{
		return (char) this.value;
	}

	@Override
	public short getShort()
	{
		return (short) this.value;
	}

	@Override
	public int getInt()
	{
		return this.value;
	}

	@Override
	public long getLong()
	{
		return this.value;
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
		return 'I';
	}

	@Override
	public void writeNumber(NBTOutputStream output) throws IOException
	{
		output.writeInt(this.value);
	}

	@Override
	public void readNumber(NBTInputStream input) throws IOException
	{
		this.value = input.readInt();
	}
}
