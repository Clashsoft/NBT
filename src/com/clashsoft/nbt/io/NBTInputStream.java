package com.clashsoft.nbt.io;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class NBTInputStream extends DataInputStream
{
	public NBTInputStream(File file, boolean compressed) throws IOException
	{
		super(getStream(file, compressed));
	}
	
	protected static InputStream getStream(File file, boolean compressed) throws IOException
	{
		FileInputStream fis = new FileInputStream(file);
		if (compressed)
		{
			return new GZIPInputStream(fis);
		}
		else
		{
			return fis;
		}
	}
	
	public int readMedium() throws IOException
	{
		int ch1 = in.read();
		int ch2 = in.read();
		int ch3 = in.read();
		if ((ch1 | ch2 | ch3) < 0)
			throw new EOFException();
		return (ch1 << 16) + (ch2 << 8) + (ch3 << 0);
	}
	
	public String readString() throws IOException
	{
		boolean b = this.readBoolean();
		if (b)
		{
			return this.readUTF();
		}
		else
		{
			int len = this.readInt();
			char[] chars = new char[len];
			
			for (int i = 0; i < len; i++)
			{
				chars[i] = this.readChar();
			}
			
			return String.valueOf(chars);
		}
	}
}
