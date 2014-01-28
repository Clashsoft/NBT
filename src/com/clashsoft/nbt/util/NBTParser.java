package com.clashsoft.nbt.util;

import static com.clashsoft.nbt.NamedBinaryTag.*;
import static com.clashsoft.nbt.util.NBTHelper.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.tags.collection.NBTTagArray;
import com.clashsoft.nbt.tags.collection.NBTTagCompound;
import com.clashsoft.nbt.tags.collection.NBTTagList;
import com.clashsoft.nbt.tags.data.NBTTagClass;
import com.clashsoft.nbt.tags.data.NBTTagDate;
import com.clashsoft.nbt.tags.data.NBTTagFile;
import com.clashsoft.nbt.tags.data.NBTTagImage;
import com.clashsoft.nbt.tags.primitive.*;
import com.clashsoft.nbt.tags.string.NBTTagString;
import com.clashsoft.nbt.tags.string.NBTTagLongString;

public class NBTParser
{
	private NBTParser()
	{
	}
	
	public static String parseString(String name)
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
			name = parseString(split[0]);
			value = split[1];
		}
		else if (split.length == 3)
		{
			type = split[0];
			name = parseString(split[1]);
			value = split[2];
		}
		
		return createTag(type, name, value);
	}
	
	public static NamedBinaryTag createTag(String type, String name, String value)
	{
		byte t = type == null ? getTypeFromValue(value) : getTypeFromTypeName(type);
		return createTag(t, name, value);
	}
	
	public static NamedBinaryTag createTag(byte type, String name, String value)
	{
		NamedBinaryTag tag = createFromType(name, type);
		tag.readString(value);
		return tag;
	}
	
	public static boolean isWrappable(Object value)
	{
		return wrap("", value) != null;
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
			String val = (String) value;
			if (val.length() < 32768)
			{
				return new NBTTagString(tagName, val);
			}
			else
			{
				return new NBTTagLongString(tagName, val);
			}
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
		if (type == TYPE_END)
		{
			return NBTHelper.END;
		}
		else if (type == TYPE_COMPOUND)
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
		else if (type == TYPE_STRING_BUILDER)
		{
			return new NBTTagLongString(tagName);
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
