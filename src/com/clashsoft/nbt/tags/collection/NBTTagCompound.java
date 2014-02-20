package com.clashsoft.nbt.tags.collection;

import java.util.Map;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.tags.primitive.*;
import com.clashsoft.nbt.tags.string.NBTTagString;

public class NBTTagCompound extends NBTTagMap implements NBTTagContainer<String>
{
	public NBTTagCompound(String name)
	{
		super(TYPE_COMPOUND, name);
	}
	
	public NBTTagCompound(String name, Map<String, NamedBinaryTag> tags)
	{
		super(TYPE_COMPOUND, name, tags);
	}
	
	@Override
	public Map<String, NamedBinaryTag> getValue()
	{
		return this.tags;
	}
	
	@Override
	public NamedBinaryTag addTag(NamedBinaryTag tag)
	{
		return this.setTag(tag);
	}
	
	@Override
	public void removeTag(NamedBinaryTag tag)
	{
		this.tags.remove(tag.getName());
	}
	
	@Override
	public boolean canAddTag(String name)
	{
		return !this.hasTag(name);
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
	
	public void setBoolean(String name, boolean value)
	{
		this.setTag(new NBTTagBoolean(name, value));
	}
	
	public void setNibble(String name, byte value)
	{
		this.setTag(new NBTTagNibble(name, value));
	}
	
	public void setByte(String name, byte value)
	{
		this.setTag(new NBTTagByte(name, value));
	}
	
	public void setShort(String name, short value)
	{
		this.setTag(new NBTTagShort(name, value));
	}
	
	public void setChar(String name, char value)
	{
		this.setTag(new NBTTagInteger(name, value));
	}
	
	public void setMedium(String name, int value)
	{
		this.setTag(new NBTTagMedium(name, value));
	}
	
	public void setInteger(String name, int value)
	{
		this.setTag(new NBTTagInteger(name, value));
	}
	
	public void setLong(String name, long value)
	{
		this.setTag(new NBTTagLong(name, value));
	}
	
	public void setFloat(String name, float value)
	{
		this.setTag(new NBTTagFloat(name, value));
	}
	
	public void setDouble(String name, double value)
	{
		this.setTag(new NBTTagDouble(name, value));
	}
	
	public void setString(String name, String value)
	{
		this.setTag(new NBTTagString(name, value));
	}
	
	public void setTagList(NBTTagList list)
	{
		this.setTag(list);
	}
	
	public void setTagSet(NBTTagSet set)
	{
		this.setTag(set);
	}
	
	public void setTagCompound(NBTTagCompound compound)
	{
		this.setTag(compound);
	}
	
	public void setTagArray(NBTTagArray array)
	{
		this.setTag(array);
	}
	
	public boolean getBoolean(String name)
	{
		NBTTagPrimitive tag = this.getPrimitiveTag(name);
		return tag != null ? tag.getBool() : false;
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
		try
		{
			NBTTagString tag = (NBTTagString) this.getTag(name);
			return tag != null ? tag.getString() : "";
		}
		catch (ClassCastException ex)
		{
			return "";
		}
	}
	
	public NBTTagPrimitive getPrimitiveTag(String name)
	{
		try
		{
			return (NBTTagPrimitive) this.getTag(name);
		}
		catch (ClassCastException ex)
		{
			return null;
		}
	}
	
	public NBTTagList getTagList(String name)
	{
		try
		{
			return (NBTTagList) this.getTag(name);
		}
		catch (ClassCastException ex)
		{
			return new NBTTagList(name);
		}
	}
	
	public NBTTagCompound getTagCompound(String name)
	{
		try
		{
			return (NBTTagCompound) this.getTag(name);
		}
		catch (ClassCastException ex)
		{
			return new NBTTagCompound(name);
		}
	}
	
	public NBTTagArray getTagArray(String name)
	{
		try
		{
			return (NBTTagArray) this.getTag(name);
		}
		catch (ClassCastException ex)
		{
			return new NBTTagArray(name);
		}
	}
}
