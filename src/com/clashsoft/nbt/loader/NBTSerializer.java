package com.clashsoft.nbt.loader;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import com.clashsoft.nbt.NamedBinaryTag;

public class NBTSerializer
{
	public static final int	VERSION					= 1;
	
	public static boolean	DELETE_COMPRESSED_FILES	= false;
	public static boolean	BYTE_STORAGE			= true;
	
	public static boolean useString()
	{
		return !BYTE_STORAGE;
	}
	
	public static NamedBinaryTag deserialize(File in, boolean compressed)
	{
		if (in == null || !in.exists())
		{
			return null;
		}
		
		try
		{
			if (useString())
			{
				if (compressed)
				{
					File in1 = new File(in.getAbsolutePath() + ".nbt");
					
					if (in1 == null || !in1.exists())
					{
						return null;
					}
					
					in = FileCompressing.decompressFile(in1, in);
				}
				
				List<String> lines = Files.readAllLines(in.toPath(), Charset.defaultCharset());
				return NBTParser.parse(lines);
			}
			else
			{
				DataInputStream input = FileCompressing.inputStream(in, compressed);
				readHeader(input);
				NamedBinaryTag nbt = NamedBinaryTag.read(input);
				input.close();
				return nbt;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public static boolean serialize(NamedBinaryTag nbt, File out, boolean compressed)
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
			
			if (useString())
			{
				BufferedWriter writer = new BufferedWriter(new FileWriter(out));
				writer.write(nbt.createString(""));
				writer.close();
				
				if (compressed)
				{
					FileCompressing.compressFile(out, new File(out.getAbsolutePath() + ".nbt"));
					// out is just a temporary file used for compressing
					if (DELETE_COMPRESSED_FILES)
					{
						out.delete();
					}
				}
			}
			else
			{
				DataOutputStream output = FileCompressing.outputStream(out, compressed);
				writeHeader(output);
				nbt.write(output);
				output.close();
			}
			
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
