package com.clashsoft.nbt;

import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;
import com.clashsoft.nbt.io.NBTSerializer;
import com.clashsoft.nbt.tags.collection.NBTTagArray;
import com.clashsoft.nbt.tags.collection.NBTTagCompound;
import com.clashsoft.nbt.tags.collection.NBTTagList;
import com.clashsoft.nbt.tags.primitive.*;
import com.clashsoft.nbt.tags.string.NBTTagString;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * The main superclass for all NBT classes. This class stores the name, the type
 * and the container that contains this tag.
 *
 * @author Clashsoft
 */
public abstract class NamedBinaryTag
{
	public static final byte TYPE_END      = 0;
	public static final byte TYPE_COMPOUND = 1;
	public static final byte TYPE_LIST     = 2;
	public static final byte TYPE_ARRAY    = 3;

	public static final byte TYPE_BOOLEAN = 10;
	public static final byte TYPE_NIBBLE  = 11;
	public static final byte TYPE_BYTE    = 12;
	public static final byte TYPE_SHORT   = 13;
	public static final byte TYPE_CHAR    = 14;
	public static final byte TYPE_MEDIUM  = 15;
	public static final byte TYPE_INT     = 16;
	public static final byte TYPE_LONG    = 17;
	public static final byte TYPE_FLOAT   = 18;
	public static final byte TYPE_DOUBLE  = 19;

	public static final byte TYPE_STRING = 20;

	public static final Class[] TYPES = new Class[256];

	static
	{
		TYPES[TYPE_COMPOUND] = NBTTagCompound.class;
		TYPES[TYPE_LIST] = NBTTagList.class;
		TYPES[TYPE_ARRAY] = NBTTagArray.class;

		TYPES[TYPE_BOOLEAN] = NBTTagBoolean.class;
		TYPES[TYPE_NIBBLE] = NBTTagNibble.class;
		TYPES[TYPE_BYTE] = NBTTagByte.class;
		TYPES[TYPE_SHORT] = NBTTagShort.class;
		TYPES[TYPE_CHAR] = NBTTagChar.class;
		TYPES[TYPE_MEDIUM] = NBTTagMedium.class;
		TYPES[TYPE_INT] = NBTTagInteger.class;
		TYPES[TYPE_LONG] = NBTTagLong.class;
		TYPES[TYPE_FLOAT] = NBTTagFloat.class;
		TYPES[TYPE_DOUBLE] = NBTTagDouble.class;
		TYPES[TYPE_STRING] = NBTTagString.class;
	}

	/**
	 * Returns the tag type of this tag
	 *
	 * @return the tag type
	 */
	public abstract byte getType();

	@Override
	public int hashCode()
	{
		return this.getType() * 31 + this.getValue().hashCode();
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
		return this.valueEquals(other);
	}

	@Override
	public final String toString()
	{
		final StringBuilder buffer = new StringBuilder();
		this.toString("", buffer);
		return buffer.toString();
	}

	/**
	 * Returns true if and if only the value of the other tag {@code that} is
	 * the same as this tag's value.
	 *
	 * @param that
	 * 	the other tag
	 *
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
	 * 	the output stream
	 *
	 * @throws IOException
	 * 	if an exception occurred
	 */
	public abstract void writeValue(NBTOutputStream output) throws IOException;

	/**
	 * Reads this tag's value from an input stream.
	 *
	 * @param input
	 * 	the input stream
	 *
	 * @throws IOException
	 * 	if an exception occurred
	 */
	public abstract void readValue(NBTInputStream input) throws IOException;

	public abstract void toString(String indent, StringBuilder buffer);

	/**
	 * Serializes this tag to an output file. If the {@code compressed} flag is
	 * set, the file will be compressed using GZIP.
	 *
	 * @param out
	 * 	the output file
	 * @param flags
	 * 	the encoding flags
	 *
	 * @return true if successful
	 */
	public final boolean serialize(File out, int flags)
	{
		return NBTSerializer.serialize(this, out, flags);
	}
}
