package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.clashsoft.nbt.util.NBTHelper;

public class NBTTagCompound extends NamedBinaryTag implements NBTTagContainer<String>
{
	private Map<String, NamedBinaryTag>	tags;
	
	public NBTTagCompound(String name)
	{
		this(name, new HashMap());
	}
	
	public NBTTagCompound(String name, Map<String, NamedBinaryTag> tags)
	{
		super(TYPE_COMPOUND, name);
		this.tags = tags;
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
	public Iterator<String> iterator()
	{
		return this.tags.keySet().iterator();
	}
	
	public NamedBinaryTag setTag(String name, NamedBinaryTag tag)
	{
		tag.setName(name);
		return this.setTag(name, tag);
	}
	
	public NamedBinaryTag setTag(NamedBinaryTag tag)
	{
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
		return tag != null ? tag.value : 0;
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
	
	@Override
	public boolean valueEquals(NamedBinaryTag that)
	{
		return this.tags.equals(((NBTTagCompound) that).tags);
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
			sb.append(value.toString()).append(',');
		}
		sb.append("}");
		
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
			NamedBinaryTag tag = NBTHelper.createTag(sub);
			this.addTag(tag);
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
			
			this.setTag(nbt);
		}
	}
}
