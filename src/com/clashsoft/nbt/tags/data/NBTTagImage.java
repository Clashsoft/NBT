package com.clashsoft.nbt.tags.data;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;

public class NBTTagImage extends NamedBinaryTag
{
	protected BufferedImage	image;
	
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
	public BufferedImage getValue()
	{
		return this.getImage();
	}
	
	public BufferedImage getImage()
	{
		return this.image;
	}
	
	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		BufferedImage img = this.image;
		
		if (img == null)
		{
			output.writeInt(-1);
			return;
		}
		
		int t = img.getType();
		int w = img.getWidth();
		int h = img.getHeight();
		
		output.writeInt(t);
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
	
	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		int t = input.readInt();
		
		if (t >= 0)
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
