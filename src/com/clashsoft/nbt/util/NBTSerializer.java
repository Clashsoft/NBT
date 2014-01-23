package com.clashsoft.nbt.util;

import java.io.*;

import com.clashsoft.nbt.NamedBinaryTag;

public class NBTSerializer
{
	public static final int	VERSION					= 1;
	
	public static boolean	DELETE_COMPRESSED_FILES	= false;
	
	public static NamedBinaryTag deserialize(File in, boolean compressed)
	{
		if (in == null || !in.exists())
		{
			return null;
		}
		
		try
		{	
			DataInputStream input = FileCompressing.inputStream(in, compressed);
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
			
			DataOutputStream output = FileCompressing.outputStream(out, compressed);
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
