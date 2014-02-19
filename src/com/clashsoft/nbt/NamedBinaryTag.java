package com.clashsoft.nbt;

import static com.clashsoft.nbt.util.NBTParser.createFromType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;
import com.clashsoft.nbt.io.NBTSerializer;
import com.clashsoft.nbt.tags.NBTTagEnd;
import com.clashsoft.nbt.tags.collection.*;
import com.clashsoft.nbt.tags.data.*;
import com.clashsoft.nbt.tags.primitive.*;
import com.clashsoft.nbt.tags.string.NBTTagString;
import com.clashsoft.nbt.util.NBTHelper;

/**
 * The main superclass for all NBT classes. This class stores the name, the type
 * and the container that contains this tag.
 * 
 * @author Clashsoft
 */
public abstract class NamedBinaryTag
{
	public static final byte	TYPE_END		= 0;
	public static final byte	TYPE_COMPOUND	= 1;
	public static final byte	TYPE_LIST		= 2;
	public static final byte	TYPE_ARRAY		= 3;
	public static final byte	TYPE_SET		= 4;
	
	public static final byte	TYPE_CUSTOM		= 9;
	
	public static final byte	TYPE_BOOLEAN	= 10;
	public static final byte	TYPE_BYTE		= 11;
	public static final byte	TYPE_SHORT		= 12;
	public static final byte	TYPE_CHAR		= 13;
	public static final byte	TYPE_MEDIUM		= 14;
	public static final byte	TYPE_INT		= 15;
	public static final byte	TYPE_LONG		= 16;
	public static final byte	TYPE_FLOAT		= 17;
	public static final byte	TYPE_DOUBLE		= 18;
	
	public static final byte	TYPE_STRING		= 20;
	
	public static final byte	TYPE_DATE		= 30;
	public static final byte	TYPE_IMAGE		= 31;
	public static final byte	TYPE_CLASS		= 32;
	public static final byte	TYPE_FILE		= 33;
	
	public static final Class[]	TYPES			= new Class[256];
	
	static
	{
		TYPES[TYPE_END] = NBTTagEnd.class;
		TYPES[TYPE_COMPOUND] = NBTTagCompound.class;
		TYPES[TYPE_LIST] = NBTTagList.class;
		TYPES[TYPE_ARRAY] = NBTTagArray.class;
		TYPES[TYPE_SET] = NBTTagSet.class;
		
		TYPES[TYPE_CUSTOM] = NBTTagCustom.class;
		
		TYPES[TYPE_BOOLEAN] = NBTTagBoolean.class;
		TYPES[TYPE_BYTE] = NBTTagByte.class;
		TYPES[TYPE_SHORT] = NBTTagShort.class;
		TYPES[TYPE_CHAR] = NBTTagChar.class;
		TYPES[TYPE_MEDIUM] = NBTTagMedium.class;
		TYPES[TYPE_INT] = NBTTagInteger.class;
		TYPES[TYPE_LONG] = NBTTagLong.class;
		TYPES[TYPE_FLOAT] = NBTTagFloat.class;
		TYPES[TYPE_DOUBLE] = NBTTagDouble.class;
		TYPES[TYPE_STRING] = NBTTagString.class;
		
		TYPES[TYPE_DATE] = NBTTagDate.class;
		TYPES[TYPE_IMAGE] = NBTTagImage.class;
		TYPES[TYPE_CLASS] = NBTTagClass.class;
		TYPES[TYPE_FILE] = NBTTagFile.class;
	}
	
	/**
	 * The name of this tag
	 */
	private String				name;
	
	/**
	 * The type of this tag
	 */
	private final byte			type;
	
	/**
	 * The container that contains this tag
	 */
	private NBTTagContainer		container		= null;
	
	/**
	 * Creates a new abstract NBT without a value.
	 * 
	 * @param type
	 *            the tag type
	 * @param name
	 *            the tag name
	 */
	public NamedBinaryTag(byte type, String name)
	{
		this.type = type;
		this.name = name;
	}
	
	/**
	 * Returns the name of this tag
	 * 
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Returns the container that contains this tag. Possibly {@code null}.
	 * 
	 * @return the container
	 */
	public NBTTagContainer getContainer()
	{
		return this.container;
	}
	
	/**
	 * Returns the tag type of this tag
	 * 
	 * @return the tag type
	 */
	public byte getType()
	{
		return this.type;
	}
	
	/**
	 * Sets the new name of this tag to {@code name}. If a container of type
	 * {@link NBTTagMap} contains this tag, this tag will be removed from that
	 * container before renaming and then added to the container. This makes
	 * sure the container uses the right key to store this tag.
	 * 
	 * @param name
	 *            the new name
	 * @return this tag
	 */
	public NamedBinaryTag setName(String name)
	{
		if (this.container instanceof NBTTagMap)
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
	
	/**
	 * Sets the new container of this tag to {@code container}.
	 * 
	 * @param container
	 *            the new container
	 * @return this tag
	 */
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
	
	/**
	 * Returns true if and if only the value of the other tag {@code that} is
	 * the same as this tag's value.
	 * 
	 * @param that
	 *            the other tag
	 * @return true if the values are equal
	 */
	public boolean valueEquals(NamedBinaryTag that)
	{
		return Objects.equals(this.getValue(), that.getValue());
	}
	
	/**
	 * Returns the value of this tag.
	 * 
	 * @return this tag's value
	 */
	public abstract Object getValue();
	
	/**
	 * Writes this tag's value to an output stream.
	 * 
	 * @param output
	 *            the output stream
	 * @throws IOException
	 *             if an exception occurred
	 */
	public abstract void writeValue(NBTOutputStream output) throws IOException;
	
	/**
	 * Reads this tag's value from an input stream.
	 * 
	 * @param input
	 *            the input stream
	 * @throws IOException
	 *             if an exception occurred
	 */
	public abstract void readValue(NBTInputStream input) throws IOException;
	
	/**
	 * Writes this tag's value as a textual representation. If this method is no
	 * implemented, string parsing does not work with this tag. This means this
	 * tag type cannot be edited or created with editors.
	 * 
	 * @return the textual representation of this tag's value
	 */
	public abstract String writeString();
	
	/**
	 * Reads this tag's value from a textual representation. If this method is
	 * no implemented, string parsing does not work with this tag. This means
	 * this tag type cannot be edited or created with editors.
	 * 
	 * @param the
	 *            textual representation of this tag's value
	 */
	public abstract void readString(String dataString);
	
	/**
	 * Serializes this tag to an output file. If the {@code compressed} flag is
	 * set, the file will be compressed using GZIP.
	 * 
	 * @param out
	 *            the output file
	 * @param compressed
	 *            if the file should be compressed using GZIP
	 * @return true if successful
	 */
	public final boolean serialize(File out, boolean compressed)
	{
		return NBTSerializer.serialize(this, out, compressed);
	}
	
	/**
	 * Writes this tag's data to an output stream. At first, the type is written
	 * to the output stream as a byte value. If the type is not END, the tag
	 * name and value are written to the output stream. The value is written by
	 * the {@link NamedBinaryTag#writeValue(DataOutput)} method.
	 * 
	 * @param output
	 *            the output stream
	 * @throws IOException
	 *             if an exception occurred
	 */
	public final void write(NBTOutputStream output) throws IOException
	{
		output.writeByte(this.type);
		
		if (this.type != TYPE_END)
		{
			output.writeUTF(this.name);
			this.writeValue(output);
		}
	}
	
	/**
	 * Reads a new tag from an input stream. At first, a byte is read from the
	 * input stream that represents the tag type. If the type is END, the static
	 * {@link NBTTagEnd} instance {@link NBTHelper#END} is returned. Otherwise,
	 * a UTF string is read from the input stream and set as the name. Using the
	 * name and the type, a new tag object is created. Then the object reads
	 * it's data from the input stream using the
	 * {@link NBTTagCompound#readValue(DataInput)} method.
	 * 
	 * @param input
	 *            the input stream
	 * @throws IOException
	 *             if an exception occurred
	 * @return the new NBT object
	 */
	public static NamedBinaryTag read(NBTInputStream input) throws IOException
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
