package com.clashsoft.nbt;

import java.util.Map;

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
	
	public void setBoolean(String name, boolean value)
	{
		this.setTag(new NBTTagBoolean(name, value));
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
		NBTTagBoolean tag = (NBTTagBoolean) this.getTag(name);
		return tag != null ? tag.value : false;
	}
	
	public Number getNumber(String name)
	{
		NBTTagNumber tag = (NBTTagNumber) this.getTag(name);
		return tag != null ? tag.getValue() : 0;
	}
	
	public byte getByte(String name)
	{
		return this.getNumber(name).byteValue();
	}
	
	public short getShort(String name)
	{
		return this.getNumber(name).shortValue();
	}
	
	public char getChar(String name)
	{
		NBTTagChar tag = (NBTTagChar) this.getTag(name);
		return tag != null ? tag.value : 0;
	}
	
	public int getInteger(String name)
	{
		return this.getNumber(name).intValue();
	}
	
	public long getLong(String name)
	{
		return this.getNumber(name).longValue();
	}
	
	public float getFloat(String name)
	{
		return this.getNumber(name).floatValue();
	}
	
	public double getDouble(String name)
	{
		return this.getNumber(name).doubleValue();
	}
	
	public String getString(String name)
	{
		NBTTagString tag = (NBTTagString) this.getTag(name);
		return tag != null ? tag.value : "";
	}
	
	public NBTTagList getTagList(String name)
	{
		return (NBTTagList) this.getTag(name);
	}
	
	public NBTTagCompound getTagCompound(String name)
	{
		return (NBTTagCompound) this.getTag(name);
	}
	
	public NBTTagArray getTagArray(String name)
	{
		return (NBTTagArray) this.getTag(name);
	}
	
	public void clear()
	{
		this.tags.clear();
	}
}
