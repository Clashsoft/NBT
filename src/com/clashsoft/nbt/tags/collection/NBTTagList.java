package com.clashsoft.nbt.tags.collection;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;
import com.clashsoft.nbt.tags.primitive.*;
import com.clashsoft.nbt.tags.string.NBTTagString;
import com.clashsoft.nbt.util.NBTParser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NBTTagList extends NamedBinaryTag implements Iterable<NamedBinaryTag>
{
	private List<NamedBinaryTag> tags;

	public NBTTagList()
	{
		this(10);
	}

	public NBTTagList(int capacity)
	{
		this(new ArrayList<>(capacity));
	}

	public NBTTagList(List<NamedBinaryTag> tags)
	{
		this.tags = tags;
	}

	@Override
	public byte getType()
	{
		return TYPE_LIST;
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

	public int size()
	{
		return this.tags.size();
	}

	@Override
	public Iterator<NamedBinaryTag> iterator()
	{
		return this.tags.iterator();
	}

	public NamedBinaryTag get(int index)
	{
		return this.tags.get(index);
	}

	public void clear()
	{
		this.tags.clear();
	}

	public void add(NamedBinaryTag tag)
	{
		this.tags.add(tag);
	}

	public void setTag(int index, NamedBinaryTag tag)
	{
		this.ensureSize(index + 1);
		this.tags.set(index, tag);
	}

	public void remove(NamedBinaryTag tag)
	{
		this.tags.remove(tag);
	}

	public NamedBinaryTag removeAt(int index)
	{
		return this.tags.remove(index);
	}

	public int indexOf(NamedBinaryTag nbt)
	{
		return this.tags.indexOf(nbt);
	}

	private void ensureSize(int size)
	{
		if (this.tags instanceof ArrayList)
		{
			((ArrayList) this.tags).ensureCapacity(size);
		}
	}

	@Deprecated
	public void addBoolean(boolean value)
	{
		this.add(new NBTTagBoolean(value));
	}

	@Deprecated
	public void addNibble(byte value)
	{
		this.add(new NBTTagNibble(value));
	}

	@Deprecated
	public void addByte(byte value)
	{
		this.add(new NBTTagByte(value));
	}

	@Deprecated
	public void addShort(short value)
	{
		this.add(new NBTTagShort(value));
	}

	@Deprecated
	public void addChar(char value)
	{
		this.add(new NBTTagChar(value));
	}

	@Deprecated
	public void addMedium(int value)
	{
		this.add(new NBTTagMedium(value));
	}

	@Deprecated
	public void addInteger(int value)
	{
		this.add(new NBTTagInteger(value));
	}

	@Deprecated
	public void addLong(long value)
	{
		this.add(new NBTTagLong(value));
	}

	@Deprecated
	public void addFloat(float value)
	{
		this.add(new NBTTagFloat(value));
	}

	@Deprecated
	public void addDouble(double value)
	{
		this.add(new NBTTagDouble(value));
	}

	@Deprecated
	public void addString(String value)
	{
		this.add(new NBTTagString(value));
	}

	@Deprecated
	public void addTagList(NBTTagList list)
	{
		this.add(list);
	}

	@Deprecated
	public void addTagCompound(NBTTagCompound compound)
	{
		this.add(compound);
	}

	@Deprecated
	public void addTagArray(NBTTagArray array)
	{
		this.add(array);
	}

	public static <T> NBTTagList fromArray(T[] args)
	{
		final NBTTagList list = new NBTTagList(args.length);

		for (T arg : args)
		{
			final NamedBinaryTag tag = NBTParser.wrap(arg);
			if (tag != null)
			{
				list.add(tag);
			}
		}

		return list;
	}

	public static NBTTagList fromList(List<?> args)
	{
		final NBTTagList list = new NBTTagList(args.size());

		for (Object arg : args)
		{
			final NamedBinaryTag tag = NBTParser.wrap(arg);
			if (tag != null)
			{
				list.add(tag);
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
			array[i] = (T) this.get(i).getValue();
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
			NamedBinaryTag value = this.get(i);
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
			final NamedBinaryTag nbt = input.readNBT();
			if (nbt == null)
			{
				return;
			}

			this.add(nbt);
		}
	}

	@Override
	public void toString(String indent, StringBuilder buffer)
	{
		final int size = this.tags.size();
		if (size == 0)
		{
			buffer.append("[]");
			return;
		}

		buffer.append("[ ");

		this.tags.get(0).toString(indent, buffer);
		for (int i = 1; i < size; i++)
		{
			buffer.append(", ");
			this.tags.get(i).toString(indent, buffer);
		}

		buffer.append(" ]");
	}
}
