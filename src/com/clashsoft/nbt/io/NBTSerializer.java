package com.clashsoft.nbt.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.util.NBTParser;

public class NBTSerializer
{
	public static final int	VERSION					= 1;
	
	public static final int COMPRESSED = 1;
	public static final int BUFFERED = 2;
	public static final int BUFFERED2 = 4;
	
	public static final int DEFAULT_FLAGS = BUFFERED | COMPRESSED;
	
	public static NBTOutputStream outputStream(File file, int flags) throws IOException
	{
		return new NBTOutputStream(file, flags);
	}
	
	public static NBTInputStream inputStream(File file, int flags) throws IOException
	{
		return new NBTInputStream(file, flags);
	}
	
	public static NamedBinaryTag load(File in)
	{
		if (in == null || !in.exists())	
		{
			return null;
		}
		
		try
		{
			StringBuilder buf = new StringBuilder((int) in.length() >> 1);
			List<String> lines = Files.readAllLines(in.toPath(), Charset.defaultCharset());
			
			for (String s : lines)
			{
				buf.append(s);
			}
			
			String tag = buf.toString();
			return NBTParser.createTag(tag);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();return null;
		}
	}
	
	public static boolean save(NamedBinaryTag nbt, File out)
	{
		if (nbt == null || out == null)
		{
			return false;
		}
		
		try
		{
			if (!out.exists())
			{
				out.createNewFile();
			}
			
			String tag = nbt.toString();
			Files.write(out.toPath(), Arrays.asList(tag), Charset.defaultCharset());
			
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	@Deprecated
	public static NamedBinaryTag deserialize(File in, boolean compressed)
	{
		return deserialize(in, BUFFERED | (compressed ? 1 : 0));
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
		
		try
		{
			NBTInputStream input = inputStream(in, flags);
			readHeader(input);
			NamedBinaryTag nbt = NamedBinaryTag.read(input);
			input.close();
			return nbt;
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
		return serialize(nbt, out, BUFFERED | (compressed ? 1 : 0));
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
			if (!out.exists())
			{
				out.createNewFile();
			}
			
			NBTOutputStream output = outputStream(out, flags);
			writeHeader(output);
			nbt.write(output);
			output.close();
			
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public static void readHeader(DataInput input) throws IOException
	{
		int ver = input.readInt();
	}
	
	public static void writeHeader(DataOutput output) throws IOException
	{
		output.writeInt(VERSION);
	}
}
