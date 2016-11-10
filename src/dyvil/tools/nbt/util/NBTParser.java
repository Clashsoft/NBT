package dyvil.tools.nbt.util;

import dyvil.tools.nbt.NamedBinaryTag;
import dyvil.tools.nbt.collection.NBTArray;
import dyvil.tools.nbt.collection.NBTMap;
import dyvil.tools.nbt.collection.NBTList;
import dyvil.tools.nbt.primitive.*;

import java.util.List;
import java.util.Map;

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
			NBTMap nbt = new NBTMap();
			for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet())
			{
				nbt.setTag(entry.getKey().toString(), wrap(entry.getValue()));
			}
			return nbt;
		}
		else if (value instanceof List)
		{
			final List<?> list = (List) value;
			final NBTList tagList = new NBTList(list.size());

			for (Object element : list)
			{
				tagList.addTag(wrap(element));
			}

			return tagList;
		}
		else if (value.getClass().isArray())
		{
			return new NBTArray(value);
		}
		else if (value instanceof Boolean)
		{
			return new NBTBoolean((Boolean) value);
		}
		else if (value instanceof Byte)
		{
			byte b = (Byte) value;
			if (b < 16)
			{
				return new NBTNibble(b);
			}

			return new NBTByte(b);
		}
		else if (value instanceof Short)
		{
			return new NBTShort((Short) value);
		}
		else if (value instanceof Character)
		{
			return new NBTChar((Character) value);
		}
		else if (value instanceof Integer)
		{
			return new NBTInteger((Integer) value);
		}
		else if (value instanceof Long)
		{
			return new NBTLong((Long) value);
		}
		else if (value instanceof Float)
		{
			return new NBTFloat((Float) value);
		}
		else if (value instanceof Double)
		{
			return new NBTDouble((Double) value);
		}
		else if (value instanceof String)
		{
			return new NBTString((String) value);
		}
		return null;
	}

	public static NamedBinaryTag createFromType(byte type)
	{
		switch (type)
		{
		case NamedBinaryTag.TYPE_COMPOUND:
			return new NBTMap();
		case NamedBinaryTag.TYPE_LIST:
			return new NBTList();
		case NamedBinaryTag.TYPE_ARRAY:
			return new NBTArray();
		case NamedBinaryTag.TYPE_BOOLEAN:
			return new NBTBoolean(false);
		case NamedBinaryTag.TYPE_NIBBLE:
			return new NBTNibble((byte) 0);
		case NamedBinaryTag.TYPE_BYTE:
			return new NBTByte((byte) 0);
		case NamedBinaryTag.TYPE_SHORT:
			return new NBTShort((short) 0);
		case NamedBinaryTag.TYPE_CHAR:
			return new NBTChar((char) 0);
		case NamedBinaryTag.TYPE_MEDIUM:
			return new NBTMedium(0);
		case NamedBinaryTag.TYPE_INT:
			return new NBTInteger(0);
		case NamedBinaryTag.TYPE_LONG:
			return new NBTLong(0L);
		case NamedBinaryTag.TYPE_FLOAT:
			return new NBTFloat(0F);
		case NamedBinaryTag.TYPE_DOUBLE:
			return new NBTDouble(0D);
		case NamedBinaryTag.TYPE_STRING:
			return new NBTString("");
		}
		return null;
	}
}
