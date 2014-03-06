package com.clashsoft.nbt.util;

import static com.clashsoft.nbt.NamedBinaryTag.*;

import java.util.ArrayList;
import java.util.List;

import com.clashsoft.nbt.tags.NBTTagEnd;
import com.clashsoft.nbt.tags.collection.NBTTagArray;
import com.clashsoft.nbt.tags.collection.NBTTagList;
import com.clashsoft.nbt.tags.collection.NBTTagMap;
import com.clashsoft.nbt.tags.collection.NBTTagSet;

public class NBTHelper
{
	public static final NBTTagEnd	END	= new NBTTagEnd();
	
	private NBTHelper()
	{
	}
	
	public static int ceil(float f)
	{
		int i = (int) f;
		return f > i ? i + 1 : i;
	}
	
	public static int parseInt(String string, int _default)
	{
		try
		{
			return Integer.parseInt(string);
		}
		catch (NumberFormatException ex)
		{
			return _default;
		}
	}
	
	public static byte getTypeFromValue(String value)
	{
		if (value == null)
		{
			return 0;
		}
		
		if (value.startsWith(NBTTagMap.START) && value.endsWith(NBTTagMap.END))
		{
			return TYPE_COMPOUND;
		}
		else if (value.startsWith(NBTTagSet.START) && value.endsWith(NBTTagSet.END))
		{
			return TYPE_SET;
		}
		else if (value.startsWith(NBTTagList.START) && value.endsWith(NBTTagList.END))
		{
			return TYPE_LIST;
		}
		else if (value.startsWith(NBTTagArray.START) && value.endsWith(NBTTagArray.END))
		{
			return TYPE_ARRAY;
		}
		else if ("true".equals(value) || "false".equals(value))
		{
			return TYPE_BOOLEAN;
		}
		else if (value.endsWith("N"))
		{
			return TYPE_NIBBLE;
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
		else if (value.endsWith("M"))
		{
			return TYPE_MEDIUM;
		}
		else if (value.endsWith("I"))
		{
			return TYPE_INT;
		}
		else if (value.endsWith("L"))
		{
			return TYPE_LONG;
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
			Integer.parseInt(value);
			return TYPE_INT;
		}
		catch (NumberFormatException ex)
		{
		}
		
		return TYPE_STRING;
	}
	
	public static String[] listToArray(List<String> list)
	{
		return list.toArray(new String[list.size()]);
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
}
