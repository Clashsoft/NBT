package com.clashsoft.nbt;

import static com.clashsoft.nbt.util.NBTParser.createFromType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.clashsoft.nbt.tags.NBTTagEnd;
import com.clashsoft.nbt.tags.collection.NBTTagArray;
import com.clashsoft.nbt.tags.collection.NBTTagCompound;
import com.clashsoft.nbt.tags.collection.NBTTagContainer;
import com.clashsoft.nbt.tags.collection.NBTTagList;
import com.clashsoft.nbt.tags.data.*;
import com.clashsoft.nbt.tags.primitive.*;
import com.clashsoft.nbt.tags.string.NBTTagString;
import com.clashsoft.nbt.tags.string.NBTTagLongString;
import com.clashsoft.nbt.util.NBTHelper;
import com.clashsoft.nbt.util.NBTSerializer;

public abstract class NamedBinaryTag
{
	public static final byte	TYPE_END			= 0;
	public static final byte	TYPE_COMPOUND		= 1;
	public static final byte	TYPE_LIST			= 2;
	public static final byte	TYPE_ARRAY			= 3;
	
	public static final byte	TYPE_CUSTOM			= 9;
	
	public static final byte	TYPE_BOOLEAN		= 10;
	public static final byte	TYPE_BYTE			= 11;
	public static final byte	TYPE_SHORT			= 12;
	public static final byte	TYPE_CHAR			= 13;
	public static final byte	TYPE_INT			= 14;
	public static final byte	TYPE_LONG			= 15;
	public static final byte	TYPE_FLOAT			= 16;
	public static final byte	TYPE_DOUBLE			= 17;
	
	public static final byte	TYPE_STRING			= 18;
	public static final byte	TYPE_STRING_LONG	= 19;
	
	public static final byte	TYPE_DATE			= 30;
	public static final byte	TYPE_IMAGE			= 31;
	public static final byte	TYPE_CLASS			= 32;
	public static final byte	TYPE_FILE			= 33;
	
	public static final Class[]	TYPES				= new Class[256];
	
	static
	{
		TYPES[TYPE_END] = NBTTagEnd.class;
		TYPES[TYPE_COMPOUND] = NBTTagCompound.class;
		TYPES[TYPE_LIST] = NBTTagList.class;
		TYPES[TYPE_ARRAY] = NBTTagArray.class;
		
		TYPES[TYPE_CUSTOM] = NBTTagCustom.class;
		
		TYPES[TYPE_BOOLEAN] = NBTTagBoolean.class;
		TYPES[TYPE_BYTE] = NBTTagByte.class;
		TYPES[TYPE_SHORT] = NBTTagShort.class;
		TYPES[TYPE_CHAR] = NBTTagChar.class;
		TYPES[TYPE_INT] = NBTTagInteger.class;
		TYPES[TYPE_LONG] = NBTTagLong.class;
		TYPES[TYPE_FLOAT] = NBTTagFloat.class;
		TYPES[TYPE_DOUBLE] = NBTTagDouble.class;
		TYPES[TYPE_STRING] = NBTTagString.class;
		TYPES[TYPE_STRING_LONG] = NBTTagLongString.class;
		
		TYPES[TYPE_DATE] = NBTTagDate.class;
		TYPES[TYPE_IMAGE] = NBTTagImage.class;
		TYPES[TYPE_CLASS] = NBTTagClass.class;
		TYPES[TYPE_FILE] = NBTTagFile.class;
	}
	
	private String				name;
	private final byte			type;
	
	private NBTTagContainer		container			= null;
	
	public NamedBinaryTag(byte type, String name)
	{
		this.type = type;
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public NBTTagContainer getContainer()
	{
		return this.container;
	}
	
	public byte getType()
	{
		return this.type;
	}
	
	public NamedBinaryTag setName(String name)
	{
		if (this.container != null)
		{
			this.container.removeTag(this);
			this.name = name;
			this.container.addTag(this);
		}
		else
		{
			this.name = name;
		}
		
		return this;
	}
	
	public NamedBinaryTag setContainer(NBTTagContainer container)
	{
		this.container = container;
		return this;
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
	
	@Override
	public final String toString()
	{
		return "\"" + this.getName() + "\":" + this.writeString();
	}
	
	public boolean valueEquals(NamedBinaryTag that)
	{
		return Objects.equals(this.getValue(), that.getValue());
	}
	
	public abstract Object getValue();
	
	public abstract void writeValue(DataOutput output) throws IOException;
	
	public abstract void readValue(DataInput input) throws IOException;
	
	public abstract String writeString();
	
	public abstract void readString(String dataString);
	
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
			return NBTHelper.END;
		}
		
		String name = input.readUTF();
		NamedBinaryTag nbt = createFromType(name, type);
		nbt.readValue(input);
		return nbt;
	}
}
