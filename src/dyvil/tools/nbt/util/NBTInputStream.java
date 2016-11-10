package dyvil.tools.nbt.util;

import dyvil.tools.nbt.NamedBinaryTag;

import java.io.*;
import java.util.zip.GZIPInputStream;

@SuppressWarnings( { "PointlessBitwiseExpression", "ResultOfMethodCallIgnored" })
public class NBTInputStream extends DataInputStream
{
	protected       ObjectInputStream objectInput;
	protected final int               flags;

	public NBTInputStream(InputStream is)
	{
		super(is);
		this.flags = -1;
	}

	public NBTInputStream(File file, int flags) throws IOException
	{
		super(getStream(file, flags));
		this.flags = flags;
	}

	protected static int ceilDiv(int denom, int num)
	{
		int result = denom / num;

		if (result > result * num)
		{
			result++;
		}

		return result;
	}

	protected static InputStream getStream(File file, int flags) throws IOException
	{
		InputStream input = new FileInputStream(file);
		if ((flags & NBTSerializer.BUFFERED) != 0)
		{
			input = new BufferedInputStream(input);
		}
		if ((flags & NBTSerializer.COMPRESSED) != 0)
		{
			input = new GZIPInputStream(input);
		}
		if ((flags & NBTSerializer.BUFFERED2) != 0)
		{
			input = new BufferedInputStream(input);
		}
		return input;
	}

	protected void initObjectInput() throws IOException
	{
		if (this.objectInput == null)
		{
			this.objectInput = new ObjectInputStream(this.in);
		}
	}

	/**
	 * Reads a new tag from this input stream. At first, a byte is read from
	 * this input stream that represents the tag type. If the type is END, {@code null} is returned.
	 * Otherwise, a UTF string is read from this input stream and set as the
	 * name. Using the name and the type, a new tag object is created. Then the
	 * object reads it's data from the input stream using the
	 * {@link NamedBinaryTag#readValue(NBTInputStream)} method.
	 *
	 * @return the new NBT object
	 *
	 * @throws IOException
	 * 	if an exception occurred
	 */
	public NamedBinaryTag readNBT() throws IOException
	{
		final byte type = this.readByte();
		if (type == NamedBinaryTag.TYPE_END)
		{
			return null;
		}

		final NamedBinaryTag nbt = NBTParser.createFromType(type);
		if (nbt != null)
		{
			nbt.readValue(this);
		}

		return nbt;
	}

	public Object readObject() throws IOException
	{
		this.initObjectInput();
		try
		{
			return this.objectInput.readObject();
		}
		catch (ClassNotFoundException ex)
		{
			throw new IOException(ex);
		}
	}

	public byte readNibble() throws IOException
	{
		int ch1 = this.in.read();
		if (ch1 < 0)
		{
			throw new EOFException();
		}
		return (byte) (ch1 & 0xF);
	}

	public int readMedium() throws IOException
	{
		int ch1 = this.in.read();
		int ch2 = this.in.read();
		int ch3 = this.in.read();
		if ((ch1 | ch2 | ch3) < 0)
		{
			throw new EOFException();
		}
		return (ch1 << 16) + (ch2 << 8) + (ch3 << 0);
	}

	public String readString() throws IOException
	{
		return this.readUTF();
	}

	public boolean[] readBooleanArray() throws IOException
	{
		final int size = this.readInt();
		final int byteCount = ceilDiv(size, 8);

		final boolean[] bools = new boolean[size];
		final byte[] data = new byte[byteCount];

		this.in.read(data);
		for (int boolIndex = 0, byteIndex = 0, bitIndex = 7; boolIndex < size; boolIndex++)
		{
			int l = 1 << bitIndex;
			bools[boolIndex] = (data[byteIndex] & l) != 0;
			--bitIndex;

			if (bitIndex < 0)
			{
				byteIndex++;
				bitIndex = 7;
			}
		}
		return bools;
	}

	public byte[] readNibbleArray() throws IOException
	{
		final int size = this.readInt();
		final int byteCount = ceilDiv(size, 2);

		final byte[] nibbles = new byte[size];
		final byte[] data = new byte[byteCount];

		this.in.read(data);

		for (int nibbleIndex = 0, byteIndex = 0, bitIndex = 1; nibbleIndex < size; nibbleIndex++)
		{
			int l = bitIndex << 2;
			nibbles[nibbleIndex] |= data[byteIndex] >> l & 0xF;
			--bitIndex;

			if (bitIndex < 0)
			{
				byteIndex++;
				bitIndex = 1;
			}
		}
		return nibbles;
	}

	public byte[] readByteArray() throws IOException
	{
		int len = this.readInt();
		byte[] v = new byte[len];

		for (int i = 0; i < len; i++)
		{
			v[i] = this.readByte();
		}
		return v;
	}

	public short[] readShortArray() throws IOException
	{
		int len = this.readInt();
		short[] v = new short[len];

		for (int i = 0; i < len; i++)
		{
			v[i] = this.readShort();
		}
		return v;
	}

	public char[] readCharArray() throws IOException
	{
		int len = this.readInt();
		char[] v = new char[len];

		for (int i = 0; i < len; i++)
		{
			v[i] = this.readChar();
		}
		return v;
	}

	public int[] readMediumArray() throws IOException
	{
		int len = this.readInt();
		int[] v = new int[len];

		for (int i = 0; i < len; i++)
		{
			v[i] = this.readMedium();
		}
		return v;
	}

	public int[] readIntArray() throws IOException
	{
		int len = this.readInt();
		int[] v = new int[len];

		for (int i = 0; i < len; i++)
		{
			v[i] = this.readInt();
		}
		return v;
	}

	public long[] readLongArray() throws IOException
	{
		int len = this.readInt();
		long[] v = new long[len];

		for (int i = 0; i < len; i++)
		{
			v[i] = this.readLong();
		}
		return v;
	}

	public float[] readFloatArray() throws IOException
	{
		int len = this.readInt();
		float[] v = new float[len];

		for (int i = 0; i < len; i++)
		{
			v[i] = this.readFloat();
		}
		return v;
	}

	public double[] readDoubleArray() throws IOException
	{
		int len = this.readInt();
		double[] v = new double[len];

		for (int i = 0; i < len; i++)
		{
			v[i] = this.readDouble();
		}
		return v;
	}

	public String[] readStringArray() throws IOException
	{
		int len = this.readInt();
		String[] v = new String[len];

		for (int i = 0; i < len; i++)
		{
			v[i] = this.readString();
		}
		return v;
	}
}
