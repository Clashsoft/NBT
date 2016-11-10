package dyvil.tools.nbt.collection;

import dyvil.tools.nbt.NamedBinaryTag;
import dyvil.tools.nbt.primitive.*;
import dyvil.tools.nbt.util.NBTInputStream;
import dyvil.tools.nbt.util.NBTOutputStream;
import dyvil.tools.nbt.util.NBTParser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NBTList extends NamedBinaryTag implements Iterable<NamedBinaryTag>
{
	private List<NamedBinaryTag> tags;

	public NBTList()
	{
		this(10);
	}

	public NBTList(int capacity)
	{
		this(new ArrayList<>(capacity));
	}

	public NBTList(List<NamedBinaryTag> tags)
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
		return this.tags.equals(((NBTList) that).tags);
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

	public NamedBinaryTag getTag(int index)
	{
		return this.tags.get(index);
	}

	public void clear()
	{
		this.tags.clear();
	}

	public void addTag(NamedBinaryTag tag)
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
		this.addTag(new NBTBoolean(value));
	}

	@Deprecated
	public void addNibble(byte value)
	{
		this.addTag(new NBTNibble(value));
	}

	@Deprecated
	public void addByte(byte value)
	{
		this.addTag(new NBTByte(value));
	}

	@Deprecated
	public void addShort(short value)
	{
		this.addTag(new NBTShort(value));
	}

	@Deprecated
	public void addChar(char value)
	{
		this.addTag(new NBTChar(value));
	}

	@Deprecated
	public void addMedium(int value)
	{
		this.addTag(new NBTMedium(value));
	}

	@Deprecated
	public void addInteger(int value)
	{
		this.addTag(new NBTInteger(value));
	}

	@Deprecated
	public void addLong(long value)
	{
		this.addTag(new NBTLong(value));
	}

	@Deprecated
	public void addFloat(float value)
	{
		this.addTag(new NBTFloat(value));
	}

	@Deprecated
	public void addDouble(double value)
	{
		this.addTag(new NBTDouble(value));
	}

	@Deprecated
	public void addString(String value)
	{
		this.addTag(new NBTString(value));
	}

	@Deprecated
	public void addTagList(NBTList list)
	{
		this.addTag(list);
	}

	@Deprecated
	public void addTagCompound(NBTMap compound)
	{
		this.addTag(compound);
	}

	@Deprecated
	public void addTagArray(NBTArray array)
	{
		this.addTag(array);
	}

	public static <T> NBTList fromArray(T[] args)
	{
		final NBTList list = new NBTList(args.length);

		for (T arg : args)
		{
			final NamedBinaryTag tag = NBTParser.wrap(arg);
			if (tag != null)
			{
				list.addTag(tag);
			}
		}

		return list;
	}

	public static NBTList fromList(List<?> args)
	{
		final NBTList list = new NBTList(args.size());

		for (Object arg : args)
		{
			final NamedBinaryTag tag = NBTParser.wrap(arg);
			if (tag != null)
			{
				list.addTag(tag);
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
			array[i] = (T) this.getTag(i).getValue();
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
			NamedBinaryTag value = this.getTag(i);
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

			this.addTag(nbt);
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
