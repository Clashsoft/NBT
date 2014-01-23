package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

import com.clashsoft.nbt.loader.NBTParser;

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
		this.tags.remove(tag.name);
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
		tag.name = name;
		return this.setTag(name, tag);
	}
	
	public NamedBinaryTag setTag(NamedBinaryTag tag)
	{
		return this.tags.put(tag.name, tag);
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
	
	public byte getByte(String name)
	{
		NBTTagByte tag = (NBTTagByte) this.getTag(name);
		return tag != null ? tag.value : 0;
	}
	
	public short getShort(String name)
	{
		NBTTagShort tag = (NBTTagShort) this.getTag(name);
		return tag != null ? tag.value : 0;
	}
	
	public char getChar(String name)
	{
		NBTTagChar tag = (NBTTagChar) this.getTag(name);
		return tag != null ? tag.value : 0;
	}
	
	public int getInteger(String name)
	{
		NBTTagInteger tag = (NBTTagInteger) this.getTag(name);
		return tag != null ? tag.value : 0;
	}
	
	public long getLong(String name)
	{
		NBTTagLong tag = (NBTTagLong) this.getTag(name);
		return tag != null ? tag.value : 0L;
	}
	
	public float getFloat(String name)
	{
		NBTTagFloat tag = (NBTTagFloat) this.getTag(name);
		return tag != null ? tag.value : 0F;
	}
	
	public double getDouble(String name)
	{
		NBTTagDouble tag = (NBTTagDouble) this.getTag(name);
		return tag != null ? tag.value : 0D;
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
	public String writeValueString(String prefix)
	{
		StringBuilder sb = new StringBuilder(this.tags.size() * 100);
		
		sb.append("\n" + prefix + "{");
		
		for (String key : this.tags.keySet())
		{
			NamedBinaryTag value = this.tags.get(key);
			sb.append("\n").append(prefix).append(" (").append(key).append(':');
			sb.append(value.createString(prefix + " ")).append(')');
		}
		
		sb.append("\n" + prefix + "}");
		return sb.toString();
	}
	
	@Override
	public void readValueString(String dataString)
	{
		int pos1 = dataString.indexOf('{') + 1;
		int pos2 = dataString.lastIndexOf('}');
		if (pos1 < 0 || pos2 < 0)
		{
			return;
		}
		
		dataString = dataString.substring(pos1, pos2).trim();
		
		for (String sub : split(dataString))
		{
			int point = sub.indexOf(':');
			String tagName = sub.substring(0, point);
			String tag = sub.substring(point + 1, sub.length());
			NamedBinaryTag base = NBTParser.parseTag(tag);
			this.setTag(tagName, base);
		}
	}
	
	protected static List<String> split(String text)
	{
		List<String> result = new ArrayList<String>();
		
		int depth1 = 0; // Depth of ( )
		int depth2 = 0; // Depth of [ ]
		int depth3 = 0; // Depth of { }
		boolean quote = false;
		
		String tag = "";
		int index = -1;
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			
			if (c == '"' && !(i > 0 && text.charAt(i - 1) == '\\'))
			{
				quote = !quote;
			}
			
			if (!quote)
			{
				if (c == '(')
				{
					if (depth1 == 0)
					{
						index = i;
					}
					depth1++;
					continue;
				}
				else if (c == ')')
				{
					depth1--;
					
					if (depth1 == 0 && index != -1)
					{
						tag = text.substring(index + 1, i);
						result.add(tag);
					}
				}
			}
			else
			{
				tag += c;
			}
		}
		return result;
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
