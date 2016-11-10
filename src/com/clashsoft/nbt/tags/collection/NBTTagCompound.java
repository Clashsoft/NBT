package com.clashsoft.nbt.tags.collection;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;
import com.clashsoft.nbt.tags.primitive.*;
import com.clashsoft.nbt.tags.string.NBTTagString;
import com.clashsoft.nbt.util.NBTParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NBTTagCompound extends NamedBinaryTag implements Iterable<String>
{
	protected Map<String, NamedBinaryTag> tags;

	public NBTTagCompound()
	{
		this(new HashMap<>());
	}

	public NBTTagCompound(Map<String, NamedBinaryTag> tags)
	{
		this.tags = tags;
	}

	@Override
	public byte getType()
	{
		return TYPE_COMPOUND;
	}

	@Override
	public Map<String, NamedBinaryTag> getValue()
	{
		return this.tags;
	}

	public int size()
	{
		return this.tags.size();
	}

	public void clear()
	{
		this.tags.clear();
	}

	@Override
	public Iterator<String> iterator()
	{
		return this.getValue().keySet().iterator();
	}

	public void setTag(String name, NamedBinaryTag tag)
	{
		this.tags.put(name, tag);
	}

	public boolean hasTag(String name)
	{
		return this.tags.containsKey(name);
	}

	public NamedBinaryTag getTag(String name)
	{
		return this.tags.get(name);
	}

	public void setBoolean(String name, boolean value)
	{
		this.setTag(name, new NBTTagBoolean(value));
	}

	public void setNibble(String name, byte value)
	{
		this.setTag(name, new NBTTagNibble(value));
	}

	public void setByte(String name, byte value)
	{
		this.setTag(name, new NBTTagByte(value));
	}

	public void setShort(String name, short value)
	{
		this.setTag(name, new NBTTagShort(value));
	}

	public void setChar(String name, char value)
	{
		this.setTag(name, new NBTTagInteger(value));
	}

	public void setMedium(String name, int value)
	{
		this.setTag(name, new NBTTagMedium(value));
	}

	public void setInteger(String name, int value)
	{
		this.setTag(name, new NBTTagInteger(value));
	}

	public void setLong(String name, long value)
	{
		this.setTag(name, new NBTTagLong(value));
	}

	public void setFloat(String name, float value)
	{
		this.setTag(name, new NBTTagFloat(value));
	}

	public void setDouble(String name, double value)
	{
		this.setTag(name, new NBTTagDouble(value));
	}

	public void setString(String name, String value)
	{
		this.setTag(name, new NBTTagString(value));
	}

	@Deprecated
	public void setTagList(String name, NBTTagList list)
	{
		this.setTag(name, list);
	}

	@Deprecated
	public void setTagCompound(String name, NBTTagCompound compound)
	{
		this.setTag(name, compound);
	}

	@Deprecated
	public void setTagArray(String name, NBTTagArray array)
	{
		this.setTag(name, array);
	}

	public boolean getBoolean(String name)
	{
		NBTTagPrimitive tag = this.getPrimitiveTag(name);
		return tag != null && tag.getBool();
	}

	public byte getNibble(String name)
	{
		return (byte) (this.getByte(name) & 0xF);
	}

	public byte getByte(String name)
	{
		NBTTagPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getByte() : 0;
	}

	public short getShort(String name)
	{
		NBTTagPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getShort() : 0;
	}

	public char getChar(String name)
	{
		NBTTagPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getChar() : 0;
	}

	public int getMedium(String name)
	{
		return this.getInteger(name) & 0xFFFFFF;
	}

	public int getInteger(String name)
	{
		NBTTagPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getInt() : 0;
	}

	public long getLong(String name)
	{
		NBTTagPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getLong() : 0L;
	}

	public float getFloat(String name)
	{
		NBTTagPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getFloat() : 0F;
	}

	public double getDouble(String name)
	{
		NBTTagPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getDouble() : 0D;
	}

	public String getString(String name)
	{
		final NamedBinaryTag tag = this.getTag(name);
		return tag instanceof NBTTagString ? ((NBTTagString) tag).getString() : "";
	}

	public NBTTagPrimitive getPrimitiveTag(String name)
	{
		final NamedBinaryTag tag = this.getTag(name);
		return tag instanceof NBTTagPrimitive ? (NBTTagPrimitive) tag : null;
	}

	public NBTTagList getTagList(String name)
	{
		final NamedBinaryTag tag = this.getTag(name);
		return tag instanceof NBTTagList ? (NBTTagList) tag : null;
	}

	public NBTTagCompound getTagCompound(String name)
	{
		final NamedBinaryTag tag = this.getTag(name);
		return tag instanceof NBTTagCompound ? (NBTTagCompound) tag : null;
	}

	public NBTTagArray getTagArray(String name)
	{
		final NamedBinaryTag tag = this.getTag(name);
		return tag instanceof NBTTagArray ? (NBTTagArray) tag : null;
	}

	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.tags.equals(((NBTTagCompound) that).getValue());
	}

	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		for (String key : this.tags.keySet())
		{
			NamedBinaryTag value = this.tags.get(key);
			output.writeNBT(value);
		}
		output.writeEnd();
	}

	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		while (true)
		{
			final byte tag = input.readByte();
			if (tag == TYPE_END)
			{
				return;
			}

			final String name = input.readString();
			final NamedBinaryTag nbt = NBTParser.createFromType(tag);
			if (nbt != null)
			{
				nbt.readValue(input);
				this.setTag(name, nbt);
			}
		}
	}

	@Override
	public void toString(String indent, StringBuilder buffer)
	{
		int len = this.tags.size();
		buffer.append("{ ");

		for (Map.Entry<String, NamedBinaryTag> entry : this.getValue().entrySet())
		{
			buffer.append(entry.getKey()).append(": ").append(", ");
		}

		if (len > 0)
		{
			buffer.replace(buffer.length() - 2, buffer.length() - 1, " }");
		}
		else
		{
			buffer.append('}');
		}
	}
}
