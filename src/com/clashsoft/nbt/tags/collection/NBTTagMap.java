package com.clashsoft.nbt.tags.collection;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.util.NBTHelper;
import com.clashsoft.nbt.util.NBTParser;

public abstract class NBTTagMap extends NamedBinaryTag implements Iterable<String>
{
	protected Map<String, NamedBinaryTag>	tags;
	
	public NBTTagMap(byte type, String name)
	{
		this(type, name, new HashMap());
	}
	
	public NBTTagMap(byte type, String name, Map<String, NamedBinaryTag> tags)
	{
		super(type, name);
		this.tags = tags;
	}
	
	@Override
	public Object getValue()
	{
		return this.getMap();
	}
	
	public Map<String, NamedBinaryTag> getMap()
	{
		return this.tags;
	}
	
	@Override
	public Iterator<String> iterator()
	{
		return this.getMap().keySet().iterator();
	}
	
	protected NamedBinaryTag setTag(String name, NamedBinaryTag tag)
	{
		tag.setName(name);
		return this.setTag(name, tag);
	}
	
	protected NamedBinaryTag setTag(NamedBinaryTag tag)
	{
		if (this instanceof NBTTagContainer)
		{
			tag.setContainer((NBTTagContainer) this);
		}
		return this.tags.put(tag.getName(), tag);
	}
	
	public boolean hasTag(String name)
	{
		return this.tags.containsKey(name);
	}
	
	public NamedBinaryTag getTag(String name)
	{
		return this.tags.get(name);
	}
	
	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.tags.equals(((NBTTagCompound) that).getValue());
	}
	
	@Override
	public String writeString()
	{
		int len = this.tags.size();
		StringBuilder sb = new StringBuilder(len << 6).append("{");
		NamedBinaryTag value;
		
		for (String key : this.tags.keySet())
		{
			value = this.tags.get(key);
			sb.append(String.valueOf(value)).append(',');
		}
		sb.append("}");
		
		if (len > 0)
		{
			sb.deleteCharAt(sb.length() - 2);
		}
		
		return sb.toString();
	}
	
	@Override
	public void readString(String dataString)
	{
		int pos1 = dataString.indexOf('{') + 1;
		int pos2 = dataString.lastIndexOf('}');
		if (pos1 < 0 || pos2 < 0)
		{
			return;
		}
		
		dataString = dataString.substring(pos1, pos2);
		
		for (String sub : NBTHelper.split(dataString, ','))
		{
			NamedBinaryTag tag = NBTParser.createTag(sub);
			this.setTag(tag);
		}
	}
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		for (String key : this.tags.keySet())
		{
			NamedBinaryTag value = this.tags.get(key);
			value.write(output);
		}
		NBTHelper.END.write(output);
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		while (true)
		{
			NamedBinaryTag nbt = NamedBinaryTag.read(input);
			
			if (nbt == NBTHelper.END)
			{
				break;
			}
			
			this.setTag(nbt);
		}
	}
}
