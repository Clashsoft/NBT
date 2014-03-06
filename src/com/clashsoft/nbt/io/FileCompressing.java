package com.clashsoft.nbt.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FileCompressing
{
	public static File compressFile(File source, File dest)
	{
		byte[] buffer = new byte[1024];
		GZIPOutputStream gzos = null;
		FileInputStream in = null;
		
		try
		{
			if (!dest.exists())
			{
				dest.createNewFile();
			}
			
			gzos = new GZIPOutputStream(new FileOutputStream(dest));
			in = new FileInputStream(source);
			
			int len;
			while ((len = in.read(buffer)) > 0)
			{
				gzos.write(buffer, 0, len);
			}
			
			// System.out.println("Successfully compressed " + old + " to " +
			// newFile);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return source;
		}
		finally
		{
			if (gzos != null)
			{
				try
				{
					gzos.close();
				}
				catch (IOException ex)
				{
					;
				}
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException ex)
				{
					;
				}
			}
		}
		return dest;
	}
	
	public static File decompressFile(File source, File dest)
	{
		byte[] buffer = new byte[1024];
		GZIPInputStream gzis = null;
		FileOutputStream out = null;
		
		try
		{
			if (!dest.exists())
			{
				dest.createNewFile();
			}
			
			gzis = new GZIPInputStream(new FileInputStream(source));
			out = new FileOutputStream(dest);
			
			int len;
			while ((len = gzis.read(buffer)) > 0)
			{
				out.write(buffer, 0, len);
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return source;
		}
		finally
		{
			if (gzis != null)
			{
				try
				{
					gzis.close();
				}
				catch (IOException ex)
				{
					;
				}
			}
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException ex)
				{
					;
				}
			}
		}
		
		return dest;
	}
}
