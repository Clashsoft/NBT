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
	 * Increases the written counter by the specified value until it reaches
	 * Integer.MAX_VALUE.
	 */
	public void incCount(int value)
	{
		int temp = written + value;
		if (temp < 0)
		{
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
	
	public void writeBooleanArray(boolean[] v) throws IOException
	{
		int len = v.length;
		this.writeInt(len);
		
		int len1 = (len / 8) + ((len & 7) != 0 ? 1 : 0);
		byte[] data = new byte[len1];
		
		for (int i = 0, j = 0, k = 7; i < len; i++)
		{
			if (v[i])
			{
				data[j] |= 1 << k--;
			}
			
			if (k < 0)
			{
				j++;
				k = 7;
			}
		}
		
		this.writeByteArray(data);
	}
	
	public void writeByteArray(byte[] v) throws IOException
	{
		int len = v.length;
		this.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			byte b = v[i];
			out.write(b);
		}
		incCount(len);
	}
	
	public void writeShortArray(short[] v) throws IOException
	{
		int len = v.length;
		this.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			short s = v[i];
			out.write((s >>> 8) & 0xFF);
			out.write((s >>> 0) & 0xFF);
		}
		incCount(len * 2);
	}
	
	public void writeCharArray(char[] v) throws IOException
	{
		int len = v.length;
		this.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			int c = v[i];
			out.write((c >>> 8) & 0xFF);
			out.write((c >>> 0) & 0xFF);
		}
		incCount(len * 2);
	}
	
	public void writeMediumArray(int[] v) throws IOException
	{
		int len = v.length;
		this.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			int m = v[i];
			out.write((m >>> 16) & 0xFF);
			out.write((m >>> 8) & 0xFF);
			out.write((m >>> 0) & 0xFF);
		}
		incCount(len * 3);
	}
	
	public void writeIntArray(int[] v) throws IOException
	{
		int len = v.length;
		this.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			int j = v[i];
			out.write((j >>> 24) & 0xFF);
			out.write((j >>> 16) & 0xFF);
			out.write((j >>> 8) & 0xFF);
			out.write((j >>> 0) & 0xFF);
		}
		incCount(len * 4);
	}
	
	public void writeLongArray(long[] v) throws IOException
	{
		int len = v.length;
		this.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			long l = v[i];
			out.write((int) ((l >>> 56L) & 0xFF));
			out.write((int) ((l >>> 48L) & 0xFF));
			out.write((int) ((l >>> 40) & 0xFF));
			out.write((int) ((l >>> 32L) & 0xFF));
			out.write((int) ((l >>> 24L) & 0xFF));
			out.write((int) ((l >>> 16L) & 0xFF));
			out.write((int) ((l >>> 8L) & 0xFF));
			out.write((int) ((l >>> 0L) & 0xFF));
		}
		incCount(len * 8);
	}
	
	public void writeFloatArray(float[] v) throws IOException
	{
		int len = v.length;
		this.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			int j = Float.floatToIntBits(v[i]);
			out.write((j >>> 24) & 0xFF);
			out.write((j >>> 16) & 0xFF);
			out.write((j >>> 8) & 0xFF);
			out.write((j >>> 0) & 0xFF);
		}
		incCount(len * 4);
	}
	
	public void writeDoubleArray(double[] v) throws IOException
	{
		int len = v.length;
		this.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			long l = Double.doubleToLongBits(v[i]);
			out.write((int) ((l >>> 56L) & 0xFF));
			out.write((int) ((l >>> 48L) & 0xFF));
			out.write((int) ((l >>> 40) & 0xFF));
			out.write((int) ((l >>> 32L) & 0xFF));
			out.write((int) ((l >>> 24L) & 0xFF));
			out.write((int) ((l >>> 16L) & 0xFF));
			out.write((int) ((l >>> 8L) & 0xFF));
			out.write((int) ((l >>> 0L) & 0xFF));
		}
		incCount(len * 8);
	}
	
	public void writeStringArray(String[] v) throws IOException
	{
		int len = v.length;
		this.writeInt(len);
		for (int i = 0; i < len; i++)
		{
			String s = v[i];
			this.writeString(s);
		}
	}
}
