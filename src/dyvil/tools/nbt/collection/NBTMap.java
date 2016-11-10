package dyvil.tools.nbt.collection;

import dyvil.tools.nbt.NamedBinaryTag;
import dyvil.tools.nbt.primitive.*;
import dyvil.tools.nbt.util.NBTInputStream;
import dyvil.tools.nbt.util.NBTOutputStream;
import dyvil.tools.nbt.util.NBTParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NBTMap extends NamedBinaryTag implements Iterable<String>
{
	protected Map<String, NamedBinaryTag> tags;

	public NBTMap()
	{
		this(new HashMap<>());
	}

	public NBTMap(Map<String, NamedBinaryTag> tags)
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

	public boolean hasTag(String name)
	{
		return this.tags.containsKey(name);
	}

	// Getters

	public NamedBinaryTag getTag(String name)
	{
		return this.tags.get(name);
	}

	public boolean getBoolean(String name)
	{
		NBTPrimitive tag = this.getPrimitiveTag(name);
		return tag != null && tag.getBool();
	}

	public byte getNibble(String name)
	{
		return (byte) (this.getByte(name) & 0xF);
	}

	public byte getByte(String name)
	{
		NBTPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getByte() : 0;
	}

	public short getShort(String name)
	{
		NBTPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getShort() : 0;
	}

	public char getChar(String name)
	{
		NBTPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getChar() : 0;
	}

	public int getMedium(String name)
	{
		return this.getInteger(name) & 0xFFFFFF;
	}

	public int getInteger(String name)
	{
		NBTPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getInt() : 0;
	}

	public long getLong(String name)
	{
		NBTPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getLong() : 0L;
	}

	public float getFloat(String name)
	{
		NBTPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getFloat() : 0F;
	}

	public double getDouble(String name)
	{
		NBTPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getDouble() : 0D;
	}

	public String getString(String name)
	{
		final NamedBinaryTag tag = this.getTag(name);
		return tag instanceof NBTString ? ((NBTString) tag).getString() : "";
	}

	public NBTPrimitive getPrimitiveTag(String name)
	{
		final NamedBinaryTag tag = this.getTag(name);
		return tag instanceof NBTPrimitive ? (NBTPrimitive) tag : null;
	}

	public NBTList getTagList(String name)
	{
		final NamedBinaryTag tag = this.getTag(name);
		return tag instanceof NBTList ? (NBTList) tag : null;
	}

	public NBTMap getTagCompound(String name)
	{
		final NamedBinaryTag tag = this.getTag(name);
		return tag instanceof NBTMap ? (NBTMap) tag : null;
	}

	public NBTArray getTagArray(String name)
	{
		final NamedBinaryTag tag = this.getTag(name);
		return tag instanceof NBTArray ? (NBTArray) tag : null;
	}

	// Setters

	public void setTag(String name, NamedBinaryTag tag)
	{
		this.tags.put(name, tag);
	}

	public void setBoolean(String name, boolean value)
	{
		this.setTag(name, new NBTBoolean(value));
	}

	public void setNibble(String name, byte value)
	{
		this.setTag(name, new NBTNibble(value));
	}

	public void setByte(String name, byte value)
	{
		this.setTag(name, new NBTByte(value));
	}

	public void setShort(String name, short value)
	{
		this.setTag(name, new NBTShort(value));
	}

	public void setChar(String name, char value)
	{
		this.setTag(name, new NBTInteger(value));
	}

	public void setMedium(String name, int value)
	{
		this.setTag(name, new NBTMedium(value));
	}

	public void setInteger(String name, int value)
	{
		this.setTag(name, new NBTInteger(value));
	}

	public void setLong(String name, long value)
	{
		this.setTag(name, new NBTLong(value));
	}

	public void setFloat(String name, float value)
	{
		this.setTag(name, new NBTFloat(value));
	}

	public void setDouble(String name, double value)
	{
		this.setTag(name, new NBTDouble(value));
	}

	public void setString(String name, String value)
	{
		this.setTag(name, new NBTString(value));
	}

	@Deprecated
	public void setTagList(String name, NBTList list)
	{
		this.setTag(name, list);
	}

	@Deprecated
	public void setTagCompound(String name, NBTMap compound)
	{
		this.setTag(name, compound);
	}

	@Deprecated
	public void setTagArray(String name, NBTArray array)
	{
		this.setTag(name, array);
	}

	// Serialization, Equality, toString

	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.tags.equals(((NBTMap) that).getValue());
	}

	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		for (Map.Entry<String, NamedBinaryTag> entry : this.tags.entrySet())
		{
			final NamedBinaryTag nbt = entry.getValue();

			output.writeByte(nbt.getType());
			output.writeString(entry.getKey());

			nbt.writeValue(output);
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
