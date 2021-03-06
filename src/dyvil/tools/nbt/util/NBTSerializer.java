package dyvil.tools.nbt.util;

import dyvil.tools.nbt.NamedBinaryTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;

public class NBTSerializer
{
	public static final int VERSION = 1;

	public static final int BUFFERED   = 1;
	public static final int COMPRESSED = 2;
	public static final int BUFFERED2  = 4;

	public static final int DEFAULT_FLAGS = BUFFERED | COMPRESSED;

	public static NBTOutputStream outputStream(File file, int flags) throws IOException
	{
		return new NBTOutputStream(file, flags);
	}

	public static NBTInputStream inputStream(File file, int flags) throws IOException
	{
		return new NBTInputStream(file, flags);
	}

	@Deprecated
	public static NamedBinaryTag deserialize(File in, boolean compressed)
	{
		int flags = BUFFERED;
		if (compressed)
			flags |= COMPRESSED;
		return deserialize(in, flags);
	}

	public static NamedBinaryTag deserialize(File in)
	{
		return deserialize(in, DEFAULT_FLAGS);
	}

	public static NamedBinaryTag deserialize(File in, int flags)
	{
		if (in == null || !in.exists())
		{
			return null;
		}

		try (NBTInputStream input = inputStream(in, flags))
		{
			readHeader(input);

			return input.readNBT();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	@Deprecated
	public static boolean serialize(NamedBinaryTag nbt, File out, boolean compressed)
	{
		int flags = BUFFERED;
		if (compressed)
			flags |= COMPRESSED;
		return serialize(nbt, out, flags);
	}

	public static boolean serialize(NamedBinaryTag nbt, File out)
	{
		return serialize(nbt, out, DEFAULT_FLAGS);
	}

	public static boolean serialize(NamedBinaryTag nbt, File out, int flags)
	{
		if (nbt == null || out == null)
		{
			return false;
		}

		try
		{
			if (!out.exists() && !out.createNewFile())
			{
				return false;
			}

			NBTOutputStream output = outputStream(out, flags);
			writeHeader(output);
			output.writeNBT(nbt);
			output.close();

			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	private static void readHeader(DataInput input) throws IOException
	{
		input.readInt();
	}

	private static void writeHeader(DataOutput output) throws IOException
	{
		output.writeInt(VERSION);
	}
}
