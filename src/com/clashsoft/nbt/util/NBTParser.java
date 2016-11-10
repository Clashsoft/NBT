package com.clashsoft.nbt.util;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.tags.collection.NBTTagArray;
import com.clashsoft.nbt.tags.collection.NBTTagCompound;
import com.clashsoft.nbt.tags.collection.NBTTagList;
import com.clashsoft.nbt.tags.primitive.*;
import com.clashsoft.nbt.tags.string.NBTTagString;

import java.util.List;
import java.util.Map;

import static com.clashsoft.nbt.NamedBinaryTag.*;

public class NBTParser
{
	private NBTParser()
	{
	}

	public static Object unwrap(NamedBinaryTag tag)
	{
		return tag == null ? null : tag.getValue();
	}

	public static NamedBinaryTag wrap(Object value)
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
			NBTTagCompound nbt = new NBTTagCompound();
			for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet())
			{
				nbt.setTag(entry.getKey().toString(), wrap(entry.getValue()));
			}
			return nbt;
		}
		else if (value instanceof List)
		{
			final List<?> list = (List) value;
			final NBTTagList tagList = new NBTTagList(list.size());

			for (Object element : list)
			{
				tagList.add(wrap(element));
			}

			return tagList;
		}
		else if (value.getClass().isArray())
		{
			return new NBTTagArray(value);
		}
		else if (value instanceof Boolean)
		{
			return new NBTTagBoolean((Boolean) value);
		}
		else if (value instanceof Byte)
		{
			byte b = (Byte) value;
			if (b < 16)
			{
				return new NBTTagNibble(b);
			}

			return new NBTTagByte(b);
		}
		else if (value instanceof Short)
		{
			return new NBTTagShort((Short) value);
		}
		else if (value instanceof Character)
		{
			return new NBTTagChar((Character) value);
		}
		else if (value instanceof Integer)
		{
			return new NBTTagInteger((Integer) value);
		}
		else if (value instanceof Long)
		{
			return new NBTTagLong((Long) value);
		}
		else if (value instanceof Float)
		{
			return new NBTTagFloat((Float) value);
		}
		else if (value instanceof Double)
		{
			return new NBTTagDouble((Double) value);
		}
		else if (value instanceof String)
		{
			return new NBTTagString((String) value);
		}
		return null;
	}

	public static NamedBinaryTag createFromType(byte type)
	{
		switch (type)
		{
		case TYPE_COMPOUND:
			return new NBTTagCompound();
		case TYPE_LIST:
			return new NBTTagList();
		case TYPE_ARRAY:
			return new NBTTagArray();
		case TYPE_BOOLEAN:
			return new NBTTagBoolean(false);
		case TYPE_NIBBLE:
			return new NBTTagNibble((byte) 0);
		case TYPE_BYTE:
			return new NBTTagByte((byte) 0);
		case TYPE_SHORT:
			return new NBTTagShort((short) 0);
		case TYPE_CHAR:
			return new NBTTagChar((char) 0);
		case TYPE_MEDIUM:
			return new NBTTagMedium(0);
		case TYPE_INT:
			return new NBTTagInteger(0);
		case TYPE_LONG:
			return new NBTTagLong(0L);
		case TYPE_FLOAT:
			return new NBTTagFloat(0F);
		case TYPE_DOUBLE:
			return new NBTTagDouble(0D);
		case TYPE_STRING:
			return new NBTTagString("");
		}
		return null;
	}
}
