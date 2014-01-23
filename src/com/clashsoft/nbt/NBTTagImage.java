package com.clashsoft.nbt;

import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagImage extends NamedBinaryTag
{
	public BufferedImage	image;
	
	public NBTTagImage(String name)
	{
		this(name, null);
	}
	
	public NBTTagImage(String name, BufferedImage image)
	{
		super(TYPE_IMAGE, name);
		this.image = image;
	}
	
	@Override
	public Object getValue()
	{
		return this.image;
	}
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		BufferedImage img = this.image;
		
		if (img == null)
		{
			output.writeInt(-1);
		}
		else
		{
			int t = img.getType();
			int w = img.getWidth();
			int h = img.getHeight();
			
			output.writeInt(w);
			output.writeInt(h);
			
			for (int i = 0; i < w; i++)
			{
				for (int j = 0; j < h; j++)
				{
					output.writeInt(img.getRGB(i, j));
				}
			}
		}
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		int t = input.readInt();
		
		if (t > 0)
		{
			int w = input.readInt();
			int h = input.readInt();
			
			BufferedImage img = new BufferedImage(w, h, t);
			
			for (int i = 0; i < w; i++)
			{
				for (int j = 0; j < h; j++)
				{
					img.setRGB(i, j, input.readInt());
				}
			}
		}
	}
	
	@Override
	public String writeString()
	{
		return null;
	}
	
	@Override
	public void readString(String dataString)
	{
	}
}
