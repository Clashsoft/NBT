package com.clashsoft.nbt.io;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class NBTOutputStream extends DataOutputStream
{
	public NBTOutputStream(File file, boolean compressed) throws IOException
	{
		super(getStream(file, compressed));
	}
	
	protected static OutputStream getStream(File file, boolean compressed) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(file);
		if (compressed)
		{
			return new GZIPOutputStream(fos);
		}
		else
		{
			return fos;
		}
	}
	
	/**
	 * Increases the written counter by the specified value
	 * until it reaches Integer.MAX_VALUE.
	 */
	public void incCount(int value)
	{
		int temp = written + value;
		if (temp < 0) {
			temp = Integer.MAX_VALUE;
		}
		written = temp;
	}
	
	public void writeMedium(int v) throws IOException
	{
		out.write((v >>> 16) & 0xFF);
		out.write((v >>> 8) & 0xFF);
		out.write((v >>> 0) & 0xFF);
		incCount(3);
	}
	
	public void writeString(String str) throws IOException
	{
		int len = str.length();
		if (len < 32768)
		{
			this.writeBoolean(true);
			this.writeUTF(str);
		}
		else
		{
			this.writeBoolean(false);
			this.writeInt(len);
			
			for (int i = 0; i < len; i++)
			{
				this.writeChar(str.charAt(i));
			}
		}
	}
}
