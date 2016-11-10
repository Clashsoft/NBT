package dyvil.tools.nbt.util;

import dyvil.tools.nbt.NamedBinaryTag;

import java.io.*;
import java.util.zip.GZIPOutputStream;

@SuppressWarnings("PointlessBitwiseExpression")
public class NBTOutputStream extends DataOutputStream
{
	protected       ObjectOutputStream objectOutput;
	protected final int                flags;

	public NBTOutputStream(OutputStream os)
	{
		super(os);
		this.flags = -1;
	}

	public NBTOutputStream(File file, int flags) throws IOException
	{
		super(getStream(file, flags));
		this.flags = flags;
	}

	protected static OutputStream getStream(File file, int flags) throws IOException
	{
		OutputStream output = new FileOutputStream(file);
		if ((flags & NBTSerializer.BUFFERED) != 0)
		{
			output = new BufferedOutputStream(output);
		}
		if ((flags & NBTSerializer.COMPRESSED) != 0)
		{
			output = new GZIPOutputStream(output);
		}
		if ((flags & NBTSerializer.BUFFERED2) != 0)
		{
			output = new BufferedOutputStream(output);
		}
		return output;
	}

	/**
	 * Increases the written counter by the specified value until it reaches
	 * Integer.MAX_VALUE.
	 */
	public void incCount(int value)
	{
		int temp = this.written + value;
		if (temp < 0)
		{
			temp = Integer.MAX_VALUE;
		}
		this.written = temp;
	}

	protected void initObjectOutput() throws IOException
	{
		if (this.objectOutput == null)
		{
			this.objectOutput = new ObjectOutputStream(this.out);
		}
	}

	/**
	 * Writes this tag's data to an output stream. At first, the type is written
	 * to the output stream as a byte value. If the type is not END, the tag
	 * name and value are written to the output stream. The value is written by
	 * the {@link NamedBinaryTag#writeValue(NBTOutputStream)} method.
	 *
	 * @param nbt
	 * 	the tag to serialize
	 *
	 * @throws IOException
	 * 	if an exception occurred
	 */
	public void writeNBT(NamedBinaryTag nbt) throws IOException
	{
		byte type = nbt.getType();
		this.writeByte(nbt.getType());

		if (type != NamedBinaryTag.TYPE_END)
		{
			nbt.writeValue(this);
		}
	}

	public void writeEnd() throws IOException
	{
		this.writeByte(NamedBinaryTag.TYPE_END);
	}

	public void writeObject(Object v) throws IOException
	{
		this.initObjectOutput();
		this.objectOutput.writeObject(v);
	}

	public void writeNibble(byte v) throws IOException
	{
		this.out.write(v & 0xF);
		this.incCount(1);
	}

	public void writeMedium(int v) throws IOException
	{
		this.out.write(v >>> 16 & 0xFF);
		this.out.write(v >>> 8 & 0xFF);
		this.out.write(v >>> 0 & 0xFF);
		this.incCount(3);
	}

	public void writeString(String str) throws IOException
	{
		this.writeUTF(str);
	}

	public void writeBooleanArray(boolean[] v) throws IOException
	{
		final int size = v.length;
		final int byteCount = NBTInputStream.ceilDiv(size, 8);

		final byte[] data = new byte[byteCount];

		this.writeInt(size);
		for (int boolIndex = 0, byteIndex = 0, bitIndex = 7; boolIndex < size; boolIndex++)
		{
			if (v[boolIndex])
			{
				data[byteIndex] |= 1 << bitIndex;
			}
			--bitIndex;

			if (bitIndex < 0)
			{
				byteIndex++;
				bitIndex = 7;
			}
		}
		this.out.write(data);
		this.incCount(byteCount);
	}

	public void writeNibbleArray(byte[] v) throws IOException
	{
		final int size = v.length;
		final int byteCount = NBTInputStream.ceilDiv(size, 2);

		final byte[] data = new byte[byteCount];

		this.writeInt(size);
		for (int nibbleIndex = 0, byteIndex = 0, bitIndex = 1; nibbleIndex < size; nibbleIndex++)
		{
			int k1 = bitIndex << 2;
			data[byteIndex] |= v[nibbleIndex] << k1;
			--bitIndex;

			if (bitIndex < 0)
			{
				byteIndex++;
				bitIndex = 1;
			}
		}
		this.out.write(data);
		this.incCount(byteCount);
	}

	public void writeByteArray(byte[] v) throws IOException
	{
		int size = v.length;
		this.writeInt(size);
		for (byte b : v)
		{
			this.out.write(b);
		}
		this.incCount(size);
	}

	public void writeShortArray(short[] v) throws IOException
	{
		int size = v.length;
		this.writeInt(size);
		for (short s : v)
		{
			this.out.write(s >>> 8 & 0xFF);
			this.out.write(s >>> 0 & 0xFF);
		}
		this.incCount(size * 2);
	}

	public void writeCharArray(char[] v) throws IOException
	{
		int size = v.length;
		this.writeInt(size);
		for (char c : v)
		{
			this.out.write(c >>> 8 & 0xFF);
			this.out.write(c >>> 0 & 0xFF);
		}
		this.incCount(size * 2);
	}

	public void writeMediumArray(int[] v) throws IOException
	{
		int size = v.length;
		this.writeInt(size);
		for (int m : v)
		{
			this.out.write(m >>> 16 & 0xFF);
			this.out.write(m >>> 8 & 0xFF);
			this.out.write(m >>> 0 & 0xFF);
		}
		this.incCount(size * 3);
	}

	public void writeIntArray(int[] v) throws IOException
	{
		int size = v.length;
		this.writeInt(size);
		for (int j : v)
		{
			this.out.write(j >>> 24 & 0xFF);
			this.out.write(j >>> 16 & 0xFF);
			this.out.write(j >>> 8 & 0xFF);
			this.out.write(j >>> 0 & 0xFF);
		}
		this.incCount(size * 4);
	}

	public void writeLongArray(long[] v) throws IOException
	{
		int size = v.length;
		this.writeInt(size);
		for (long l : v)
		{
			this.out.write((int) (l >>> 56L & 0xFF));
			this.out.write((int) (l >>> 48L & 0xFF));
			this.out.write((int) (l >>> 40 & 0xFF));
			this.out.write((int) (l >>> 32L & 0xFF));
			this.out.write((int) (l >>> 24L & 0xFF));
			this.out.write((int) (l >>> 16L & 0xFF));
			this.out.write((int) (l >>> 8L & 0xFF));
			this.out.write((int) (l >>> 0L & 0xFF));
		}
		this.incCount(size * 8);
	}

	public void writeFloatArray(float[] v) throws IOException
	{
		int size = v.length;
		this.writeInt(size);
		for (float aV : v)
		{
			int j = Float.floatToIntBits(aV);
			this.out.write(j >>> 24 & 0xFF);
			this.out.write(j >>> 16 & 0xFF);
			this.out.write(j >>> 8 & 0xFF);
			this.out.write(j >>> 0 & 0xFF);
		}
		this.incCount(size * 4);
	}

	public void writeDoubleArray(double[] v) throws IOException
	{
		int size = v.length;
		this.writeInt(size);
		for (double aV : v)
		{
			long l = Double.doubleToLongBits(aV);
			this.out.write((int) (l >>> 56L & 0xFF));
			this.out.write((int) (l >>> 48L & 0xFF));
			this.out.write((int) (l >>> 40 & 0xFF));
			this.out.write((int) (l >>> 32L & 0xFF));
			this.out.write((int) (l >>> 24L & 0xFF));
			this.out.write((int) (l >>> 16L & 0xFF));
			this.out.write((int) (l >>> 8L & 0xFF));
			this.out.write((int) (l >>> 0L & 0xFF));
		}
		this.incCount(size * 8);
	}

	public void writeStringArray(String[] v) throws IOException
	{
		int size = v.length;
		this.writeInt(size);
		for (String s : v)
		{
			this.writeString(s);
		}
	}
}
