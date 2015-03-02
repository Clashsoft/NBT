package com.clashsoft.nbt.tags.collection;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;
import com.clashsoft.nbt.tags.primitive.*;
import com.clashsoft.nbt.tags.string.NBTTagString;
import com.clashsoft.nbt.util.NBTHelper;
import com.clashsoft.nbt.util.NBTParser;
import com.clashsoft.nbt.util.NBTParserException;

public class NBTTagSet extends NamedBinaryTag implements NBTTagContainer<NamedBinaryTag>
{
	public static final String		START	= "<";
	public static final String		END		= ">";
	
	protected Set<NamedBinaryTag>	tags;
	
	public NBTTagSet(String name)
	{
		this(name, 10);
	}
	
	public NBTTagSet(String name, int capacity)
	{
		this(name, new HashSet(capacity));
	}
	
	public NBTTagSet(String name, Set<NamedBinaryTag> tags)
	{
		super(TYPE_SET, name);
		this.tags = tags;
	}
	
	@Override
	public Set<NamedBinaryTag> getValue()
	{
		return this.tags;
	}
	
	@Override
	public NamedBinaryTag addTag(NamedBinaryTag tag)
	{
		tag.setContainer(this);
		this.tags.add(tag);
		return null;
	}
	
	public void addTag(String name, NamedBinaryTag tag)
	{
		tag.setName(name);
		this.addTag(tag);
	}
	
	@Override
	public void removeTag(NamedBinaryTag tag)
	{
		this.tags.remove(tag);
	}
	
	@Override
	public boolean canAddTag(String name)
	{
		return true;
	}
	
	@Override
	public Iterator iterator()
	{
		return this.tags.iterator();
	}
	
	@Override
	public int size()
	{
		return this.tags.size();
	}
	
	@Override
	public void clear()
	{
		this.tags.clear();
	}
	
	public void addBoolean(String name, boolean value)
	{
		this.addTag(new NBTTagBoolean(name, value));
	}
	
	public void addNibble(String name, byte value)
	{
		this.addTag(new NBTTagNibble(name, value));
	}
	
	public void addByte(String name, byte value)
	{
		this.addTag(new NBTTagByte(name, value));
	}
	
	public void addShort(String name, short value)
	{
		this.addTag(new NBTTagShort(name, value));
	}
	
	public void addChar(String name, char value)
	{
		this.addTag(new NBTTagChar(name, value));
	}
	
	public void addMedium(String name, int value)
	{
		this.addTag(new NBTTagMedium(name, value));
	}
	
	public void addInteger(String name, int value)
	{
		this.addTag(new NBTTagInteger(name, value));
	}
	
	public void addLong(String name, long value)
	{
		this.addTag(new NBTTagLong(name, value));
	}
	
	public void addFloat(String name, float value)
	{
		this.addTag(new NBTTagFloat(name, value));
	}
	
	public void addDouble(String name, double value)
	{
		this.addTag(new NBTTagDouble(name, value));
	}
	
	public void addString(String name, String value)
	{
		this.addTag(new NBTTagString(name, value));
	}
	
	public void addTagList(NBTTagList list)
	{
		this.addTag(list);
	}
	
	public void addTagSet(NBTTagSet set)
	{
		this.addTag(set);
	}
	
	public void addTagCompound(NBTTagCompound compound)
	{
		this.addTag(compound);
	}
	
	public void addTagArray(NBTTagArray array)
	{
		this.addTag(array);
	}
	
	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		for (NamedBinaryTag value : this.tags)
		{
			output.writeNBT(value);
		}
		output.writeEnd();
	}
	
	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		while (true)
		{
			NamedBinaryTag nbt = input.readNBT();
			
			if (nbt == NBTHelper.END)
			{
				break;
			}
			
			this.addTag(nbt);
		}
	}
	
	@Override
	public String writeString()
	{
		int len = this.tags.size();
		StringBuilder sb = new StringBuilder(len << 6).append(START);
		NamedBinaryTag value;
		
		for (NamedBinaryTag tag : this.tags)
		{
			sb.append(tag.writeString()).append(',');
		}
		sb.append(END);
		
		if (len > 0)
		{
			sb.deleteCharAt(sb.length() - 2);
		}
		
		return sb.toString();
	}
	
	@Override
	public void readString(String dataString) throws NBTParserException
	{
		int pos1 = dataString.indexOf(START) + START.length();
		int pos2 = dataString.lastIndexOf(END);
		if (pos1 < 0 || pos2 < 0)
		{
			return;
		}
		
		dataString = dataString.substring(pos1, pos2);
		
		List<String> tags = NBTHelper.split(dataString, ',');
		int len = tags.size();
		
		for (int i = 0; i < len; i++)
		{
			String sub = tags.get(i);
			NamedBinaryTag tag = NBTParser.createTag(sub);
			this.addTag(tag);
		}
	}
}
