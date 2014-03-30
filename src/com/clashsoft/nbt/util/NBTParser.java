package com.clashsoft.nbt.util;

import static com.clashsoft.nbt.NamedBinaryTag.*;
import static com.clashsoft.nbt.util.NBTHelper.getClassFromType;
import static com.clashsoft.nbt.util.NBTHelper.getTypeFromTypeName;
import static com.clashsoft.nbt.util.NBTHelper.getTypeFromValue;
import static com.clashsoft.nbt.util.NBTHelper.listToArray;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.*;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.tags.collection.NBTTagArray;
import com.clashsoft.nbt.tags.collection.NBTTagCompound;
import com.clashsoft.nbt.tags.collection.NBTTagList;
import com.clashsoft.nbt.tags.collection.NBTTagSet;
import com.clashsoft.nbt.tags.data.NBTTagClass;
import com.clashsoft.nbt.tags.data.NBTTagDate;
import com.clashsoft.nbt.tags.data.NBTTagFile;
import com.clashsoft.nbt.tags.data.NBTTagImage;
import com.clashsoft.nbt.tags.primitive.*;
import com.clashsoft.nbt.tags.string.NBTTagString;

public class NBTParser
{
	private NBTParser()
	{
	}
	
	public static String parseString(String name)
	{
		int len = name.length();
		if (len > 1 && name.startsWith("\"") && name.endsWith("\""))
		{
			return name.substring(1, len - 1);
		}
		return name;
	}
	
	public static NamedBinaryTag createTag(String data)
	{
		String[] split = listToArray(NBTHelper.split(data, ':'));
		
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
		else if (value instanceof Set)
		{
			return new NBTTagSet(tagName, (Set) value);
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
			byte b = ((Byte) value).byteValue();
			if (b < 16)
			{
				return new NBTTagNibble(tagName, b);
			}
			else
			{
				return new NBTTagByte(tagName, b);
			}
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
	
	public static NamedBinaryTag createFromType(String name, byte type)
	{
		if (type == TYPE_END)
		{
			return NBTHelper.END;
		}
		else if (type == TYPE_COMPOUND)
		{
			return new NBTTagCompound(name);
		}
		else if (type == TYPE_LIST)
		{
			return new NBTTagList(name);
		}
		else if (type == TYPE_ARRAY)
		{
			return new NBTTagArray(name);
		}
		else if (type == TYPE_BOOLEAN)
		{
			return new NBTTagBoolean(name);
		}
		else if (type == TYPE_NIBBLE)
		{
			return new NBTTagNibble(name);
		}
		else if (type == TYPE_BYTE)
		{
			return new NBTTagByte(name);
		}
		else if (type == TYPE_SHORT)
		{
			return new NBTTagShort(name);
		}
		else if (type == TYPE_CHAR)
		{
			return new NBTTagChar(name);
		}
		else if (type == TYPE_MEDIUM)
		{
			return new NBTTagMedium(name);
		}
		else if (type == TYPE_INT)
		{
			return new NBTTagInteger(name);
		}
		else if (type == TYPE_LONG)
		{
			return new NBTTagLong(name);
		}
		else if (type == TYPE_FLOAT)
		{
			return new NBTTagFloat(name);
		}
		else if (type == TYPE_DOUBLE)
		{
			return new NBTTagDouble(name);
		}
		else if (type == TYPE_STRING)
		{
			return new NBTTagString(name);
		}
		else if (type == TYPE_DATE)
		{
			return new NBTTagDate(name);
		}
		else if (type == TYPE_IMAGE)
		{
			return new NBTTagImage(name);
		}
		else if (type == TYPE_CLASS)
		{
			return new NBTTagClass(name);
		}
		else if (type == TYPE_FILE)
		{
			return new NBTTagFile(name);
		}
		else
		{
			Class clazz = getClassFromType(type);
			if (clazz != null)
			{
				try
				{
					Constructor<NamedBinaryTag> c = clazz.getConstructor(String.class);
					return c.newInstance(name);
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
