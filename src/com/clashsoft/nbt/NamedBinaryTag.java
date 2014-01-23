package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.clashsoft.nbt.loader.NBTSerializer;

public abstract class NamedBinaryTag
{
	public static final byte		TYPE_END		= 0;
	public static final byte		TYPE_COMPOUND	= 1;
	public static final byte		TYPE_LIST		= 2;
	public static final byte		TYPE_ARRAY		= 3;
	
	public static final byte		TYPE_BOOLEAN	= 10;
	public static final byte		TYPE_BYTE		= 11;
	public static final byte		TYPE_SHORT		= 12;
	public static final byte		TYPE_CHAR		= 13;
	public static final byte		TYPE_INT		= 14;
	public static final byte		TYPE_LONG		= 15;
	public static final byte		TYPE_FLOAT		= 16;
	public static final byte		TYPE_DOUBLE		= 17;
	public static final byte		TYPE_STRING		= 18;
	
	public static final Class[]		TYPES			= new Class[256];
	
	static
	{
		TYPES[TYPE_END] = NBTTagEnd.class;
		TYPES[TYPE_COMPOUND] = NBTTagCompound.class;
		TYPES[TYPE_LIST] = NBTTagList.class;
		TYPES[TYPE_ARRAY] = NBTTagArray.class;
		TYPES[TYPE_BOOLEAN] = NBTTagBoolean.class;
		TYPES[TYPE_BYTE] = NBTTagByte.class;
		TYPES[TYPE_SHORT] = NBTTagShort.class;
		TYPES[TYPE_CHAR] = NBTTagChar.class;
		TYPES[TYPE_INT] = NBTTagInteger.class;
		TYPES[TYPE_LONG] = NBTTagLong.class;
		TYPES[TYPE_FLOAT] = NBTTagFloat.class;
		TYPES[TYPE_DOUBLE] = NBTTagDouble.class;
		TYPES[TYPE_STRING] = NBTTagString.class;
	}
	
	public static final NBTTagEnd	END				= new NBTTagEnd();
	
	public String					name;
	public byte						type;
	
	public NamedBinaryTag(byte type, String name)
	{
		this.type = type;
		this.name = name;
		if (NBTSerializer.useString() && (name.contains("[") || name.contains("]") || name.contains("{") || name.contains("}")))
		{
			throw new IllegalArgumentException("Name must not contain [ ] { } !");
		}
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.name == null ? 0 : this.name.hashCode());
		result = prime * result + this.type;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		NamedBinaryTag other = (NamedBinaryTag) obj;
		if (this.name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!this.name.equals(other.name))
		{
			return false;
		}
		if (this.type != other.type)
		{
			return false;
		}
		if (!this.valueEquals(other))
		{
			return false;
		}
		return true;
	}
	
	public abstract Object getValue();
	
	public boolean valueEquals(NamedBinaryTag that)
	{
		return Objects.equals(this.getValue(), that.getValue());
	}
	
	public final String createString(String prefix)
	{
		return "{t:" + this.type + "n:[" + this.name + "]v:[" + this.writeValueString(prefix) + "]}";
	}
	
	@Override
	public String toString()
	{
		return this.getValue().toString();
	}
	
	public abstract void writeValue(DataOutput output) throws IOException;
	
	public abstract void readValue(DataInput input) throws IOException;
	
	public abstract String writeValueString(String prefix);
	
	public abstract void readValueString(String dataString);
	
	public final boolean serialize(File out, boolean compressed)
	{
		return NBTSerializer.serialize(this, out, compressed);
	}
	
	public final void write(DataOutput output) throws IOException
	{
		output.writeByte(this.type);
		
		if (this.type != TYPE_END)
		{
			output.writeUTF(this.name);
			this.writeValue(output);
		}
	}
	
	public static NamedBinaryTag read(DataInput input) throws IOException
	{
		byte type = input.readByte();
		
		if (type == TYPE_END)
		{
			return END;
		}
		
		String name = input.readUTF();
		NamedBinaryTag nbt = createFromType(name, type);
		nbt.readValue(input);
		return nbt;
	}
	
	public static NamedBinaryTag createFromObject(Object value)
	{
		String name = value instanceof NamedBinaryTag ? ((NamedBinaryTag) value).name : "";
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
			return new NBTTagBoolean(tagName, (boolean) value);
		}
		else if (value instanceof Byte)
		{
			return new NBTTagByte(tagName, (byte) value);
		}
		else if (value instanceof Short)
		{
			return new NBTTagShort(tagName, (short) value);
		}
		else if (value instanceof Character)
		{
			return new NBTTagChar(tagName, (char) value);
		}
		else if (value instanceof Integer)
		{
			return new NBTTagInteger(tagName, (int) value);
		}
		else if (value instanceof Long)
		{
			return new NBTTagLong(tagName, (long) value);
		}
		else if (value instanceof Float)
		{
			return new NBTTagFloat(tagName, (float) value);
		}
		else if (value instanceof Double)
		{
			return new NBTTagDouble(tagName, (double) value);
		}
		else if (value instanceof String)
		{
			return new NBTTagString(tagName, (String) value);
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
			return new NBTTagBoolean(tagName, false);
		}
		else if (type == TYPE_BYTE)
		{
			return new NBTTagByte(tagName, (byte) 0);
		}
		else if (type == TYPE_SHORT)
		{
			return new NBTTagShort(tagName, (short) 0);
		}
		else if (type == TYPE_CHAR)
		{
			return new NBTTagChar(tagName, (char) 0);
		}
		else if (type == TYPE_INT)
		{
			return new NBTTagInteger(tagName, 0);
		}
		else if (type == TYPE_LONG)
		{
			return new NBTTagLong(tagName, 0L);
		}
		else if (type == TYPE_FLOAT)
		{
			return new NBTTagFloat(tagName, 0F);
		}
		else if (type == TYPE_DOUBLE)
		{
			return new NBTTagDouble(tagName, 0D);
		}
		else if (type == TYPE_STRING)
		{
			return new NBTTagString(tagName, "");
		}
		return null;
	}
}
