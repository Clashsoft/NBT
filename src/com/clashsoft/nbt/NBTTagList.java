package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.clashsoft.nbt.util.NBTHelper;

public class NBTTagList extends NamedBinaryTag implements NBTTagContainer<NamedBinaryTag>
{
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
	public NamedBinaryTag addTag(NamedBinaryTag tag)
	{
		tag.setContainer(this);
		this.tags.add(tag);
		return null;
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
	
	public void addTag(String name, NamedBinaryTag tag)
	{
		tag.setName(name);
		this.addTag(tag);
	}
	
	public void setTag(int index, NamedBinaryTag tag)
	{
		this.ensureSize(index + 1);
		this.tags.set(index, tag);
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
	
	public int tagCount()
	{
		return this.tags.size();
	}
	
	public NamedBinaryTag tagAt(int index)
	{
		return this.tags.get(index);
	}
	
	public int indexOf(NamedBinaryTag nbt)
	{
		return this.tags.indexOf(nbt);
	}
	
	public NamedBinaryTag removeTag(int index)
	{
		return this.tags.remove(index);
	}
	
	@Override
	public Iterator iterator()
	{
		return this.tags.iterator();
	}
	
	public void addBoolean(String name, boolean value)
	{
		this.addTag(new NBTTagBoolean(name, value));
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
			NamedBinaryTag base = NBTHelper.wrap(tagName, args[i]);
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
			NamedBinaryTag base = NBTHelper.wrap(tagName, args.get(i));
			if (base != null)
			{
				list.addTag(base);
			}
		}
		return list;
	}
	
	public <T> T[] toArray(Class<T> arrayType)
	{
		T[] array = (T[]) Array.newInstance(arrayType, this.tags.size());
		for (int i = 0; i < this.tagCount(); i++)
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
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.tags.equals(((NBTTagList) that).tags);
	}
	
	@Override
	public String writeString()
	{
		int len = this.tags.size();
		StringBuilder sb = new StringBuilder(len << 6).append("\n[");
		
		NamedBinaryTag value;
		for (int key = 0; key < len; key++)
		{
			value = this.tags.get(key);
			sb.append("\n").append(key).append(':').append(value.toString()).append(',');
		}
		sb.append("\n]");
		
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
		
		List<String> tags = NBTHelper.split(dataString, ',');
		int len = tags.size();
		this.ensureSize(len);
		
		for (int i = 0; i < len; i++)
		{
			String sub = tags.get(i);
			NamedBinaryTag tag = NBTHelper.createTag(sub);
			int index = Integer.parseInt(tag.getName());
			this.setTag(index, tag);
		}
	}
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		for (int i = 0; i < this.tagCount(); i++)
		{
			NamedBinaryTag value = this.tagAt(i);
			value.write(output);
		}
		END.write(output);
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		while (true)
		{
			NamedBinaryTag nbt = NamedBinaryTag.read(input);
			
			if (nbt == NamedBinaryTag.END)
			{
				break;
			}
			
			this.addTag(nbt);
		}
	}
}
