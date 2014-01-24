package com.clashsoft.nbt.util;

import static com.clashsoft.nbt.NamedBinaryTag.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.clashsoft.nbt.*;

public class NBTHelper
{
	public static String parseName(String name)
	{
		int len = name.length();
		if (len >= 2 && name.startsWith("\"") && name.endsWith("\""))
		{
			return name.substring(1, len - 1);
		}
		return name;
	}
	
	public static NamedBinaryTag createTag(String data)
	{
		String[] split = listToArray(split(data, ':'));
		
		String type = null;
		String name = null;
		String value = null;
		
		if (split.length == 1)
		{
			value = split[0];
		}
		else if (split.length == 2)
		{
			name = parseName(split[0]);
			value = split[1];
		}
		else if (split.length == 3)
		{
			type = split[0];
			name = parseName(split[1]);
			value = split[2];
		}
		
		return createTag(type, name, value);
	}
	
	public static NamedBinaryTag createTag(String type, String name, String value)
	{
		byte t = type == null ? getTypeFromValue(value) : getTypeFromTypeName(type);
		NamedBinaryTag tag = createFromType(name, t);
		tag.readString(value);
		return tag;
	}
	
	public static byte getTypeFromValue(String value)
	{
		if (value == null)
		{
			return 0;
		}
		
		if (value.startsWith("{"))
		{
			return TYPE_COMPOUND;
		}
		else if (value.startsWith("["))
		{
			return TYPE_LIST;
		}
		else if (value.startsWith("<"))
		{
			return TYPE_ARRAY;
		}
		else if ("true".equals(value) || "false".equals(value))
		{
			return TYPE_BOOLEAN;
		}
		else if (value.endsWith("B"))
		{
			return TYPE_BYTE;
		}
		else if (value.endsWith("S"))
		{
			return TYPE_SHORT;
		}
		else if (value.startsWith("'") && value.endsWith("'"))
		{
			return TYPE_CHAR;
		}
		else if (value.endsWith("I"))
		{
			return TYPE_INT;
		}
		else if (value.endsWith("F"))
		{
			return TYPE_FLOAT;
		}
		else if (value.endsWith("D"))
		{
			return TYPE_DOUBLE;
		}
		else if (value.startsWith("\"") && value.endsWith("\""))
		{
			return TYPE_STRING;
		}
		else if (value.startsWith("class"))
		{
			return TYPE_CLASS;
		}
		else if (value.startsWith("date"))
		{
			return TYPE_DATE;
		}
		else if (value.startsWith("file"))
		{
			return TYPE_FILE;
		}
		else
		{
			if (value.contains("."))
			{
				try
				{
					Double.parseDouble(value);
					return TYPE_DOUBLE;
				}
				catch (NumberFormatException ex)
				{
				}
			}
			try
			{
				Long.parseLong(value);
				return TYPE_LONG;
			}
			catch (NumberFormatException ex)
			{
			}
		}
		return TYPE_STRING;
	}
	
	public static String[] listToArray(List<String> list)
	{
		return list.toArray(new String[list.size()]);
	}
	
	public static List<String> split(String text, char split)
	{
		int len = text.length();
		
		List<String> result = new ArrayList<String>();
		
		int depth1 = 0; // Depth of ( )
		int depth2 = 0; // Depth of [ ]
		int depth3 = 0; // Depth of { }
		int depth4 = 0; // Depth of < >
		
		boolean quote = false;
		int index = 0;
		char current = 0;
		char last = 0;
		
		for (int i = 0; i < len;)
		{
			int i0 = i + 1;
			current = text.charAt(i);
			
			if (last != '\\')
			{
				if (current == '"')
				{
					quote = !quote;
				}
				else if (!quote)
				{
					if (current == '(')
					{
						depth1++;
					}
					else if (current == ')')
					{
						depth1--;
					}
					else if (current == '[')
					{
						depth2++;
					}
					else if (current == ']')
					{
						depth2--;
					}
					else if (current == '{')
					{
						depth3++;
					}
					else if (current == '}')
					{
						depth3--;
					}
					else if (current == '<')
					{
						depth4++;
					}
					else if (current == '>')
					{
						depth4--;
					}
					
				}
				
				if (!quote && depth1 == 0 && depth2 == 0 && depth3 == 0 && depth4 == 0)
				{
					if (current == split)
					{
						result.add(text.substring(index, i));
						index = i0;
					}
					else if (i0 == len)
					{
						result.add(text.substring(index, i0));
					}
				}
			}
			last = current;
			i = i0;
		}
		return result;
	}
	
	public static Class getClassFromType(byte type)
	{
		return TYPES[type & 255];
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
		Class clazz = getClassFromType(type);
		if (clazz != null)
		{
			return clazz.getSimpleName().replace("NBTTag", "");
		}
		else
		{
			return "";
		}
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
		
		name = name.toLowerCase();
		
		for (int i = 0; i < 256; i++)
		{
			String typeName = getTypeName((byte) i).toLowerCase();
			if (typeName.startsWith(name))
			{
				return (byte) i;
			}
		}
		return -1;
	}
	
	public static boolean isWrappable(Object value)
	{
		if (value instanceof NamedBinaryTag)
		{
			return true;
		}
		else if (value instanceof Map || value instanceof List || value.getClass().isArray())
		{
			return true;
		}
		else if (value instanceof Boolean)
		{
			return true;
		}
		else if (value instanceof Byte || value instanceof Short || value instanceof Character || value instanceof Integer || value instanceof Long)
		{
			return true;
		}
		else if (value instanceof Float || value instanceof Double)
		{
			return true;
		}
		else if (value instanceof String)
		{
			return true;
		}
		else if (value instanceof Date || value instanceof File || value instanceof BufferedImage || value instanceof Class)
		{
			return true;
		}
		return false;
	}
	
	public static Object unwrap(NamedBinaryTag tag)
	{
		return tag == null ? null : tag.getValue();
	}
	
	public static NamedBinaryTag wrap(Object value)
	{
		return wrap("", value);
	}
	
	public static NamedBinaryTag wrap(String tagName, Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof NamedBinaryTag)
		{
			return (NamedBinaryTag) value;
		}
		else if (value instanceof Map)
		{
			return new NBTTagCompound(tagName, (Map) value);
		}
		else if (value instanceof List)
		{
			return new NBTTagList(tagName, (List) value);
		}
		else if (value.getClass().isArray())
		{
			return new NBTTagArray(tagName, value);
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
		else if (value instanceof Class)
		{
			return new NBTTagClass(tagName, (Class) value);
		}
		else if (value instanceof File)
		{
			return new NBTTagFile(tagName, (File) value);
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
		else if (type == TYPE_CLASS)
		{
			return new NBTTagClass(tagName);
		}
		else if (type == TYPE_FILE)
		{
			return new NBTTagFile(tagName);
		}
		else
		{
			Class clazz = getClassFromType(type);
			if (clazz != null)
			{
				try
				{
					Constructor<NamedBinaryTag> c = clazz.getConstructor(String.class);
					return c.newInstance(tagName);
				}
				catch (ReflectiveOperationException ex)
				{
					ex.printStackTrace();
				}
			}
			return null;
		}
	}
}
