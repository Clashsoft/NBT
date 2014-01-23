package com.clashsoft.nbt.util;

import static com.clashsoft.nbt.NamedBinaryTag.*;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.clashsoft.nbt.*;

public class NBTHelper
{
	public static NamedBinaryTag createTag(String type, String name, String value)
	{
		byte t = (type == null ? getTypeFromValue(value) : getTypeFromTypeName(name));
		NamedBinaryTag tag = createFromType(name, t);
		tag.readString(value);
		return tag;
	}
	
	public static byte getTypeFromValue(String value)
	{
		return 0;
	}
	
	public static List<String> split(String text)
	{
		List<String> result = new ArrayList<String>();
		
		int depth1 = 0; // Depth of ( )
		int depth2 = 0; // Depth of [ ]
		int depth3 = 0; // Depth of { }
		int depth4 = 0; // Depth of < >
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
					depth1++;
				}
				else if (c == ')')
				{
					depth1--;
				}
				else if (c == '[')
				{
					depth2++;
				}
				else if (c == ']')
				{
					depth2--;
				}
				else if (c == '{')
				{
					depth3++;
				}
				else if (c == '}')
				{
					depth3--;
				}
				else if (c == '<')
				{
					depth4++;
				}
				else if (c == '>')
				{
					depth4--;
				}
				else if (c == ',')
				{
					if (depth1 == 0 && depth2 == 0 && depth3 == 0 && depth4 == 0 && index != -1)
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
	
	public static Class getClassFromType(byte type)
	{
		return TYPES[type];
	}
	
	public static byte getTypeFromClass(Class type)
	{
		for (int i = 0; i < 256; i++)
		{
			if (TYPES[i] == type)
			{
				return (byte) i;
			}
		}
		return -1;
	}
	
	public static String getTypeName(byte type)
	{
		return getClassFromType(type).getSimpleName().replace("NBT", "");
	}
	
	public static byte getTypeFromTypeName(String name)
	{
		try
		{
			return Byte.parseByte(name);
			// name is a number
		}
		catch (NumberFormatException ex)
		{
			// name is in name form
		}
		
		for (int i = 0; i < 256; i++)
		{
			if (name.equals(getTypeName((byte) i)))
			{
				return (byte) i;
			}
		}
		return -1;
	}
	
	public static NamedBinaryTag createFromObject(Object value)
	{
		String name = value instanceof NamedBinaryTag ? ((NamedBinaryTag) value).getName() : "";
		return createFromObject(name, value);
	}
	
	public static NamedBinaryTag createFromObject(String tagName, Object value)
	{
		if (value instanceof NamedBinaryTag)
		{
			return (NamedBinaryTag) value;
		}
		else if (value instanceof Boolean)
		{
			return new NBTTagBoolean(tagName, (Boolean) value);
		}
		else if (value instanceof Byte)
		{
			return new NBTTagByte(tagName, (Byte) value);
		}
		else if (value instanceof Short)
		{
			return new NBTTagShort(tagName, (Short) value);
		}
		else if (value instanceof Character)
		{
			return new NBTTagChar(tagName, (Character) value);
		}
		else if (value instanceof Integer)
		{
			return new NBTTagInteger(tagName, (Integer) value);
		}
		else if (value instanceof Long)
		{
			return new NBTTagLong(tagName, (Long) value);
		}
		else if (value instanceof Float)
		{
			return new NBTTagFloat(tagName, (Float) value);
		}
		else if (value instanceof Double)
		{
			return new NBTTagDouble(tagName, (Double) value);
		}
		else if (value instanceof String)
		{
			return new NBTTagString(tagName, (String) value);
		}
		else if (value instanceof Date)
		{
			return new NBTTagDate(tagName, (Date) value);
		}
		else if (value instanceof BufferedImage)
		{
			return new NBTTagImage(tagName, (BufferedImage) value);
		}
		return null;
	}
	
	public static NamedBinaryTag createFromType(String tagName, byte type)
	{
		if (type == TYPE_COMPOUND)
		{
			return new NBTTagCompound(tagName);
		}
		else if (type == TYPE_LIST)
		{
			return new NBTTagList(tagName);
		}
		else if (type == TYPE_ARRAY)
		{
			return new NBTTagArray(tagName);
		}
		else if (type == TYPE_BOOLEAN)
		{
			return new NBTTagBoolean(tagName);
		}
		else if (type == TYPE_BYTE)
		{
			return new NBTTagByte(tagName);
		}
		else if (type == TYPE_SHORT)
		{
			return new NBTTagShort(tagName);
		}
		else if (type == TYPE_CHAR)
		{
			return new NBTTagChar(tagName);
		}
		else if (type == TYPE_INT)
		{
			return new NBTTagInteger(tagName);
		}
		else if (type == TYPE_LONG)
		{
			return new NBTTagLong(tagName);
		}
		else if (type == TYPE_FLOAT)
		{
			return new NBTTagFloat(tagName);
		}
		else if (type == TYPE_DOUBLE)
		{
			return new NBTTagDouble(tagName);
		}
		else if (type == TYPE_STRING)
		{
			return new NBTTagString(tagName);
		}
		else if (type == TYPE_DATE)
		{
			return new NBTTagDate(tagName);
		}
		else if (type == TYPE_IMAGE)
		{
			return new NBTTagImage(tagName);
		}
		else
		{
			Class clazz = getClassFromType(type);
			try
			{
				Constructor<NamedBinaryTag> c = clazz.getConstructor(String.class);
				return c.newInstance(tagName);
			}
			catch (ReflectiveOperationException ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
	}
}
