package dyvil.tools.nbt.collection;

import dyvil.tools.nbt.NamedBinaryTag;
import dyvil.tools.nbt.util.NBTInputStream;
import dyvil.tools.nbt.util.NBTOutputStream;
import dyvil.tools.nbt.primitive.NBTBoolean;
import dyvil.tools.nbt.primitive.NBTChar;
import dyvil.tools.nbt.primitive.NBTNumber;
import dyvil.tools.nbt.primitive.NBTString;
import dyvil.tools.nbt.util.NBTParser;

import java.io.IOException;
import java.lang.reflect.Array;

/**
 * A named binary tag representing an array with constant size.
 * <p>
 * The array is either of type {@link NamedBinaryTag}, {@link String} or a
 * primitive type.
 *
 * @author Clashsoft
 */
public class NBTArray extends NamedBinaryTag
{
	public static final byte TYPE_NBT = 0;

	private Object array;
	private byte   subtype;
	private int    length;

	/**
	 * Constructor used for deserialization
	 */
	public NBTArray()
	{
	}

	/**
	 * Creates a new tag array from an array. The array is converted into a
	 * {@link NamedBinaryTag} array and then reverse-converted to a primitive
	 * array.
	 *
	 * @param array
	 * 	the array
	 */
	public NBTArray(Object array)
	{
		this.array = array;
		this.unwrap(array);
	}

	public NBTArray(Object array, int length, byte type)
	{
		this.array = array;
		this.subtype = type;
		this.length = length;
	}

	public NBTArray(NamedBinaryTag[] nbtArray)
	{
		this.setNBTArray(nbtArray);
	}

	public NBTArray(boolean[] booleanArray)
	{
		this.setBooleanArray(booleanArray);
	}

	public NBTArray(byte[] byteArray)
	{
		this.setByteArray(byteArray);
	}

	public NBTArray(short[] shortArray)
	{
		this.setShortArray(shortArray);
	}

	public NBTArray(char[] charArray)
	{
		this.setCharArray(charArray);
	}

	public static NBTArray mediumArray(int[] mediumArray)
	{
		return new NBTArray().setMediumArray(mediumArray);
	}

	public NBTArray(int[] intArray)
	{
		this.setIntArray(intArray);
	}

	public NBTArray(long[] longArray)
	{
		this.setLongArray(longArray);
	}

	public NBTArray(float[] floatArray)
	{
		this.setFloatArray(floatArray);
	}

	public NBTArray(double[] doubleArray)
	{
		this.setDoubleArray(doubleArray);
	}

	public NBTArray(String[] stringArray)
	{
		this.setStringArray(stringArray);
	}

	@Override
	public byte getType()
	{
		return TYPE_ARRAY;
	}

	public int size()
	{
		return this.length;
	}

	@Override
	public Object getValue()
	{
		return this.array;
	}

	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		byte type = this.subtype;

		output.writeByte(type);

		switch (type)
		{
		case TYPE_NBT:
			NamedBinaryTag[] nbtArray = (NamedBinaryTag[]) this.array;
			int len = nbtArray.length;

			output.writeInt(len);
			for (NamedBinaryTag aNbtArray : nbtArray)
			{
				output.writeNBT(aNbtArray);
			}
			break;
		case TYPE_BOOLEAN:
			boolean[] booleanArray = (boolean[]) this.array;
			output.writeBooleanArray(booleanArray);
			break;
		case TYPE_NIBBLE:
			byte[] nibbleArray = (byte[]) this.array;
			output.writeNibbleArray(nibbleArray);
			break;
		case TYPE_BYTE:
			byte[] byteArray = (byte[]) this.array;
			output.writeByteArray(byteArray);
			break;
		case TYPE_SHORT:
			short[] shortArray = (short[]) this.array;
			output.writeShortArray(shortArray);
			break;
		case TYPE_CHAR:
			char[] charArray = (char[]) this.array;
			output.writeCharArray(charArray);
			break;
		case TYPE_MEDIUM:
			int[] mediumArray = (int[]) this.array;
			output.writeMediumArray(mediumArray);
			break;
		case TYPE_INT:
			int[] intArray = (int[]) this.array;
			output.writeIntArray(intArray);
			break;
		case TYPE_LONG:
			long[] longArray = (long[]) this.array;
			output.writeLongArray(longArray);
			break;
		case TYPE_FLOAT:
			float[] floatArray = (float[]) this.array;
			output.writeFloatArray(floatArray);
			break;
		case TYPE_DOUBLE:
			double[] doubleArray = (double[]) this.array;
			output.writeDoubleArray(doubleArray);
			break;
		case TYPE_STRING:
			String[] stringArray = (String[]) this.array;
			output.writeStringArray(stringArray);
			break;
		}
	}

	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		final byte subtype = input.readByte();
		this.subtype = subtype;

		switch (subtype)
		{
		case TYPE_NBT:
			final int size = input.readInt();
			final NamedBinaryTag[] nbtArray = new NamedBinaryTag[size];
			for (int i = 0; i < size; i++)
			{
				final byte tag = input.readByte();
				if (tag == TYPE_END)
				{
					break;
				}
				final NamedBinaryTag nbt = NBTParser.createFromType(tag);
				if (nbt != null)
				{
					nbt.readValue(input);
					nbtArray[i] = nbt;
				}
			}
			this.array = nbtArray;
			break;
		case TYPE_BOOLEAN:
		{
			boolean[] a = input.readBooleanArray();
			this.length = a.length;
			this.array = a;
			break;
		}
		case TYPE_NIBBLE:
		{
			byte[] a = input.readNibbleArray();
			this.length = a.length;
			this.array = a;
			break;
		}
		case TYPE_BYTE:
		{
			byte[] a = input.readByteArray();
			this.length = a.length;
			this.array = a;
			break;
		}
		case TYPE_SHORT:
		{
			short[] a = input.readShortArray();
			this.length = a.length;
			this.array = a;
			break;
		}
		case TYPE_CHAR:
		{
			char[] a = input.readCharArray();
			this.length = a.length;
			this.array = a;
			break;
		}
		case TYPE_MEDIUM:
		{
			int[] a = input.readMediumArray();
			this.length = a.length;
			this.array = a;
			break;
		}
		case TYPE_INT:
		{
			int[] a = input.readIntArray();
			this.length = a.length;
			this.array = a;
			break;
		}
		case TYPE_LONG:
		{
			long[] a = input.readLongArray();
			this.length = a.length;
			this.array = a;
			break;
		}
		case TYPE_FLOAT:
		{
			float[] a = input.readFloatArray();
			this.length = a.length;
			this.array = a;
			break;
		}
		case TYPE_DOUBLE:
		{
			double[] a = input.readDoubleArray();
			this.length = a.length;
			this.array = a;
			break;
		}
		case TYPE_STRING:
		{
			String[] a = input.readStringArray();
			this.length = a.length;
			this.array = a;
			break;
		}
		}

		this.subtype = subtype;
	}

	@Override
	public void toString(String indent, StringBuilder buffer)
	{
		if (this.length == 0)
		{
			buffer.append("[]");
			return;
		}

		if (this.subtype != TYPE_NBT)
		{
			buffer.append("[ ");
			buffer.append(Array.get(this.array, 0));

			for (int i = 1; i < this.length; i++)
			{
				buffer.append(", ").append(Array.get(this.array, i));
			}

			buffer.append(" ]");
			return;
		}

		final NamedBinaryTag[] tags = (NamedBinaryTag[]) this.array;

		buffer.append("[ ");
		tags[0].toString(indent, buffer);

		for (int i = 1; i < this.length; i++)
		{
			buffer.append(", ");
			tags[i].toString(indent, buffer);
		}

		buffer.append(" ]");
	}

	public void unwrap(NamedBinaryTag[] nbtArray)
	{
		int len = nbtArray.length;
		byte type = -1;

		for (NamedBinaryTag tag : nbtArray)
		{
			byte t = tag.getType();
			if (type == -1)
			{
				type = t;
			}
			else if (type != t)
			{
				this.setNBTArray(nbtArray);
				return;
			}
		}

		this.subtype = type;
		this.length = len;

		if (type == TYPE_BOOLEAN)
		{
			boolean[] booleanArray = new boolean[len];
			for (int i = 0; i < len; i++)
			{
				NBTBoolean tag = (NBTBoolean) nbtArray[i];
				booleanArray[i] = tag.getBool();
			}
			this.array = booleanArray;
		}
		else if (type == TYPE_BYTE || type == TYPE_NIBBLE)
		{
			byte[] byteArray = new byte[len];
			for (int i = 0; i < len; i++)
			{
				NBTNumber tag = (NBTNumber) nbtArray[i];
				byteArray[i] = tag.getByte();
			}
			this.array = byteArray;
		}
		else if (type == TYPE_SHORT)
		{
			short[] shortArray = new short[len];
			for (int i = 0; i < len; i++)
			{
				NBTNumber tag = (NBTNumber) nbtArray[i];
				shortArray[i] = tag.getShort();
			}
			this.array = shortArray;
		}
		else if (type == TYPE_CHAR)
		{
			char[] charArray = new char[len];
			for (int i = 0; i < len; i++)
			{
				NBTChar tag = (NBTChar) nbtArray[i];
				charArray[i] = tag.getChar();
			}
			this.array = charArray;
		}
		else if (type == TYPE_INT || type == TYPE_MEDIUM)
		{
			int[] intArray = new int[len];
			for (int i = 0; i < len; i++)
			{
				NBTNumber tag = (NBTNumber) nbtArray[i];
				intArray[i] = tag.getInt();
			}
			this.array = intArray;
		}
		else if (type == TYPE_LONG)
		{
			long[] longArray = new long[len];
			for (int i = 0; i < len; i++)
			{
				NBTNumber tag = (NBTNumber) nbtArray[i];
				longArray[i] = tag.getLong();
			}
			this.array = longArray;
		}
		else if (type == TYPE_FLOAT)
		{
			float[] floatArray = new float[len];
			for (int i = 0; i < len; i++)
			{
				NBTNumber tag = (NBTNumber) nbtArray[i];
				floatArray[i] = tag.getFloat();
			}
			this.array = floatArray;
		}
		else if (type == TYPE_DOUBLE)
		{
			double[] doubleArray = new double[len];
			for (int i = 0; i < len; i++)
			{
				NBTNumber tag = (NBTNumber) nbtArray[i];
				doubleArray[i] = tag.getDouble();
			}
			this.array = doubleArray;
		}
		else if (type == TYPE_STRING)
		{
			String[] stringArray = new String[len];
			for (int i = 0; i < len; i++)
			{
				NBTString tag = (NBTString) nbtArray[i];
				stringArray[i] = tag.getString();
			}
			this.array = stringArray;
		}
	}

	public void unwrap(Object array)
	{
		int len = Array.getLength(array);
		NamedBinaryTag[] nbtArray = new NamedBinaryTag[len];

		for (int i = 0; i < len; i++)
		{
			Object obj = Array.get(array, i);
			NamedBinaryTag tag = NBTParser.wrap(obj);
			nbtArray[i] = tag;
		}

		this.unwrap(nbtArray);
	}

	public NamedBinaryTag getWrapper(int index)
	{
		return NBTParser.wrap(this.getObject(index));
	}

	public Object getObject(int index)
	{
		return Array.get(this.array, index);
	}

	public NamedBinaryTag[] getNBTArray()
	{
		return (NamedBinaryTag[]) this.array;
	}

	public boolean[] getBooleanArray()
	{
		return (boolean[]) this.array;
	}

	public byte[] getByteArray()
	{
		return (byte[]) this.array;
	}

	public short[] getShortArray()
	{
		return (short[]) this.array;
	}

	public char[] getCharArray()
	{
		return (char[]) this.array;
	}

	public int[] getIntArray()
	{
		return (int[]) this.array;
	}

	public long[] getLongArray()
	{
		return (long[]) this.array;
	}

	public float[] getFloatArray()
	{
		return (float[]) this.array;
	}

	public double[] getDoubleArray()
	{
		return (double[]) this.array;
	}

	public String[] getStringArray()
	{
		return (String[]) this.array;
	}

	protected NBTArray setNBTArray(NamedBinaryTag[] objects)
	{
		this.array = objects;
		this.subtype = TYPE_NBT;
		this.length = objects.length;
		return this;
	}

	protected NBTArray setBooleanArray(boolean[] booleanArray)
	{
		this.array = booleanArray;
		this.subtype = TYPE_BOOLEAN;
		this.length = booleanArray.length;
		return this;
	}

	protected NBTArray setNibbleArray(byte[] nibbleArray)
	{
		this.array = nibbleArray;
		this.subtype = TYPE_NIBBLE;
		this.length = nibbleArray.length;
		return this;
	}

	protected NBTArray setByteArray(byte[] byteArray)
	{
		this.array = byteArray;
		this.subtype = TYPE_BYTE;
		this.length = byteArray.length;
		return this;
	}

	protected NBTArray setShortArray(short[] shortArray)
	{
		this.array = shortArray;
		this.subtype = TYPE_SHORT;
		this.length = shortArray.length;
		return this;
	}

	protected NBTArray setCharArray(char[] charArray)
	{
		this.array = charArray;
		this.subtype = TYPE_CHAR;
		this.length = charArray.length;
		return this;
	}

	protected NBTArray setMediumArray(int[] mediumArray)
	{
		this.array = mediumArray;
		this.subtype = TYPE_MEDIUM;
		this.length = mediumArray.length;
		return this;
	}

	protected NBTArray setIntArray(int[] intArray)
	{
		this.array = intArray;
		this.subtype = TYPE_INT;
		this.length = intArray.length;
		return this;
	}

	protected NBTArray setLongArray(long[] longArray)
	{
		this.array = longArray;
		this.subtype = TYPE_LONG;
		this.length = longArray.length;
		return this;
	}

	protected NBTArray setFloatArray(float[] floatArray)
	{
		this.array = floatArray;
		this.subtype = TYPE_FLOAT;
		this.length = floatArray.length;
		return this;
	}

	protected NBTArray setDoubleArray(double[] doubleArray)
	{
		this.array = doubleArray;
		this.subtype = TYPE_DOUBLE;
		this.length = doubleArray.length;
		return this;
	}

	protected NBTArray setStringArray(String[] stringArray)
	{
		this.array = stringArray;
		this.subtype = TYPE_STRING;
		this.length = stringArray.length;
		return this;
	}
}
