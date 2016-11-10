package dyvil.tools.nbt.primitive;

import dyvil.tools.nbt.util.NBTInputStream;
import dyvil.tools.nbt.util.NBTOutputStream;

import java.io.IOException;

public class NBTLong extends NBTNumber
{
	protected long value;

	public NBTLong(long value)
	{
		this.value = value;
	}

	@Override
	public byte getType()
	{
		return TYPE_LONG;
	}

	@Override
	public Long getValue()
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
		return 'L';
	}

	@Override
	public void writeNumber(NBTOutputStream output) throws IOException
	{
		output.writeLong(this.value);
	}

	@Override
	public void readNumber(NBTInputStream input) throws IOException
	{
		this.value = input.readLong();
	}
}
