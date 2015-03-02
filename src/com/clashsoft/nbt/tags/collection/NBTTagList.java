package com.clashsoft.nbt.tags.collection;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;
import com.clashsoft.nbt.tags.primitive.*;
import com.clashsoft.nbt.tags.string.NBTTagString;
import com.clashsoft.nbt.util.NBTHelper;
import com.clashsoft.nbt.util.NBTParser;
import com.clashsoft.nbt.util.NBTParserException;

public class NBTTagList extends NamedBinaryTag implements NBTTagContainer<NamedBinaryTag>
{
	public static final String		START	= "(";
	public static final String		END		= ")";
	
	private List<NamedBinaryTag>	tags;
	
	public NBTTagList(String name)
	{
		this(name, 10);
	}
	
	public NBTTagList(String name, int capacity)
	{
		this(name, new ArrayList(capacity));
	}
	
	public NBTTagList(String name, List<NamedBinaryTag> tags)
	{
		super(TYPE_LIST, name);
		this.tags = tags;
	}
	
	@Override
	public List<NamedBinaryTag> getValue()
	{
		return this.tags;
	}
	
	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.tags.equals(((NBTTagList) that).tags);
	}
	
	@Override
	public NamedBinaryTag addTag(NamedBinaryTag tag)
	{
		if (tag != null)
		{
			tag.setContainer(this);
			this.tags.add(tag);
		}
		return null;
	}
	
	public void setTag(int index, NamedBinaryTag tag)
	{
		if (tag != null)
		{
			this.ensureSize(index + 1);
			this.tags.set(index, tag);
		}
	}
	
	@Override
	public void removeTag(NamedBinaryTag tag)
	{
		this.tags.remove(tag);
	}
	
	public NamedBinaryTag removeTag(int index)
	{
		return this.tags.remove(index);
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
	
	public NamedBinaryTag tagAt(int index)
	{
		return this.tags.get(index);
	}
	
	public int indexOf(NamedBinaryTag nbt)
	{
		return this.tags.indexOf(nbt);
	}
	
	private void ensureSize(int size)
	{
		List tags = this.tags;
		
		if (tags instanceof ArrayList)
		{
			((ArrayList) tags).ensureCapacity(size);
		}
		while (tags.size() < size)
		{
			tags.add(null);
		}
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
	
	public static <T> NBTTagList fromArray(String name, T[] args)
	{
		NBTTagList list = new NBTTagList(name, args.length);
		for (int i = 0; i < args.length; i++)
		{
			String tagName = name + i;
			NamedBinaryTag base = NBTParser.wrap(tagName, args[i]);
			if (base != null)
			{
				list.addTag(base);
			}
		}
		return list;
	}
	
	public static NBTTagList fromList(String name, List args)
	{
		NBTTagList list = new NBTTagList(name, args.size());
		for (int i = 0; i < args.size(); i++)
		{
			String tagName = name + i;
			NamedBinaryTag base = NBTParser.wrap(tagName, args.get(i));
			if (base != null)
			{
				list.addTag(base);
			}
		}
		return list;
	}
	
	public <T> T[] toArray(Class<T> arrayType)
	{
		int len = this.size();
		T[] array = (T[]) Array.newInstance(arrayType, len);
		for (int i = 0; i < len; i++)
		{
			array[i] = (T) this.tagAt(i).getValue();
		}
		return array;
	}
	
	public <T> T[] toArray()
	{
		return (T[]) this.tags.toArray();
	}
	
	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		int len = this.size();
		for (int i = 0; i < len; i++)
		{
			NamedBinaryTag value = this.tagAt(i);
			if (value != null)
			{
				output.writeNBT(value);
			}
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
		
		for (int i = 0; i < len; i++)
		{
			value = this.tags.get(i);
			if (value != null)
			{
				sb.append(i).append(':').append(value.writeString()).append(',');
			}
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
		int pos1 = dataString.indexOf(START) + 1;
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
			int index;
			
			try
			{
				index = Integer.parseInt(tag.getName());
			}
			catch (NumberFormatException ex)
			{
				index = this.size();
			}
			this.setTag(index, tag);
		}
	}
}
