package com.clashsoft.nbt.io;

import java.io.*;
import java.util.zip.GZIPInputStream;

import com.clashsoft.nbt.util.NBTHelper;

public class NBTInputStream extends DataInputStream
{
	public NBTInputStream(InputStream is)
	{
		super(is);
	}
	
	public NBTInputStream(File file, boolean compressed) throws IOException
	{
		this(getStream(file, compressed));
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
	
	public boolean[] readBooleanArray() throws IOException
	{
		int len = this.readInt();
		int len1 = NBTHelper.ceil(len / 8F);
		boolean[] bools = new boolean[len];
		byte[] data = new byte[len1];
		
		this.in.read(data);
		for (int i = 0, j = 0, k = 7; i < len; i++)
		{
			int l = 1 << k;
			bools[i] = (data[j] & l) != 0;
			
			k--;
			if (k < 0)
			{
				j++;
				k = 7;
			}
		}
		return bools;
	}
	
	public byte[] readNibbleArray() throws IOException
	{
		int len = this.readInt();
		int len1 = NBTHelper.ceil(len / 2F);
		byte[] nibbles = new byte[len];
		byte[] data = new byte[len1];
		
		this.in.read(data);
		for (int i = 0, j = 0, k = 1; i < len; i++)
		{
			int l = k << 2;
			nibbles[i] |= data[j] >> l & 0xF;
			--k;
			
			if (k < 0)
			{
				j++;
				k = 1;
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
