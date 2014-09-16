package com.clashsoft.nbt.tags.collection;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.io.NBTInputStream;
import com.clashsoft.nbt.io.NBTOutputStream;
import com.clashsoft.nbt.tags.primitive.NBTTagBoolean;
import com.clashsoft.nbt.tags.primitive.NBTTagChar;
import com.clashsoft.nbt.tags.primitive.NBTTagNumber;
import com.clashsoft.nbt.tags.string.NBTTagString;
import com.clashsoft.nbt.util.NBTHelper;
import com.clashsoft.nbt.util.NBTParser;

/**
 * A named binary tag representing an array with constant size.
 * <p>
 * The array is either of type {@link NamedBinaryTag}, {@link String} or a
 * primitive type.
 * 
 * @author Clashsoft
 */
public class NBTTagArray extends NamedBinaryTag implements NBTTagContainer
{
	public static final String	START		= "[";
	public static final String	END			= "]";
	
	public static final byte	TYPE_NBT	= 0;
	
	private Object				array;
	private byte				subtype;
	private int					length;
	
	/**
	 * Constructor used for deserialization
	 * 
	 * @param name
	 */
	public NBTTagArray(String name)
	{
		super(TYPE_ARRAY, name);
	}
	
	/**
	 * Creates a new tag array from an array. The array is converted into a
	 * {@link NamedBinaryTag} array and then reverse-converted to a primitive
	 * array.
	 * 
	 * @param name
	 * @param array
	 */
	public NBTTagArray(String name, Object array)
	{
		this(name);
		this.array = array;
		this.unwrap(array);
	}
	
	public NBTTagArray(String name, Object array, int length, byte type)
	{
		this(name);
		this.array = array;
		this.subtype = type;
		this.length = length;
	}
	
	public NBTTagArray(String name, Class<? extends NamedBinaryTag> type, int length)
	{
		this(name, Array.newInstance(type, length), length, TYPE_NBT);
	}
	
	public NBTTagArray(String name, NamedBinaryTag[] nbtArray)
	{
		this(name);
		this.setNBTArray(nbtArray);
	}
	
	public NBTTagArray(String name, boolean[] booleanArray)
	{
		this(name);
		this.setBooleanArray(booleanArray);
	}
	
	public NBTTagArray(String name, byte[] byteArray)
	{
		this(name);
		this.setByteArray(byteArray);
	}
	
	public NBTTagArray(String name, short[] shortArray)
	{
		this(name);
		this.setShortArray(shortArray);
	}
	
	public NBTTagArray(String name, char[] charArray)
	{
		this(name);
		this.setCharArray(charArray);
	}
	
	public static NBTTagArray mediumArray(String name, int[] mediumArray)
	{
		return new NBTTagArray(name).setMediumArray(mediumArray);
	}
	
	public NBTTagArray(String name, int[] intArray)
	{
		this(name);
		this.setIntArray(intArray);
	}
	
	public NBTTagArray(String name, long[] longArray)
	{
		this(name);
		this.setLongArray(longArray);
	}
	
	public NBTTagArray(String name, float[] floatArray)
	{
		this(name);
		this.setFloatArray(floatArray);
	}
	
	public NBTTagArray(String name, double[] doubleArray)
	{
		this(name);
		this.setDoubleArray(doubleArray);
	}
	
	public NBTTagArray(String name, String[] stringArray)
	{
		this(name);
		this.setStringArray(stringArray);
	}
	
	@Override
	public Object getValue()
	{
		return this.array;
	}
	
	@Override
	public NamedBinaryTag addTag(NamedBinaryTag tag)
	{
		byte type = this.subtype;
		int oldlen = this.length;
		int newlen = ++this.length;
		if (type == TYPE_NBT)
		{
			NamedBinaryTag[] oldArray = this.getNBTArray();
			NamedBinaryTag[] newArray = new NamedBinaryTag[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = tag;
			this.array = newArray;
		}
		else if (type == TYPE_BOOLEAN)
		{
			boolean[] oldArray = this.getBooleanArray();
			boolean[] newArray = new boolean[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagBoolean) tag).getBool();
			this.array = newArray;
		}
		else if (type == TYPE_BYTE || type == TYPE_NIBBLE)
		{
			byte[] oldArray = this.getByteArray();
			byte[] newArray = new byte[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber) tag).getByte();
			this.array = newArray;
		}
		else if (type == TYPE_SHORT)
		{
			short[] oldArray = this.getShortArray();
			short[] newArray = new short[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber) tag).getShort();
			this.array = newArray;
		}
		else if (type == TYPE_CHAR)
		{
			char[] oldArray = this.getCharArray();
			char[] newArray = new char[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagChar) tag).getChar();
			this.array = newArray;
		}
		else if (type == TYPE_INT || type == TYPE_MEDIUM)
		{
			int[] oldArray = this.getIntArray();
			int[] newArray = new int[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber) tag).getInt();
			this.array = newArray;
		}
		else if (type == TYPE_LONG)
		{
			long[] oldArray = this.getLongArray();
			long[] newArray = new long[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber) tag).getLong();
			this.array = newArray;
		}
		else if (type == TYPE_FLOAT)
		{
			float[] oldArray = this.getFloatArray();
			float[] newArray = new float[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber) tag).getFloat();
			this.array = newArray;
		}
		else if (type == TYPE_DOUBLE)
		{
			double[] oldArray = this.getDoubleArray();
			double[] newArray = new double[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber) tag).getDouble();
			this.array = newArray;
		}
		else if (type == TYPE_STRING)
		{
			String[] oldArray = this.getStringArray();
			String[] newArray = new String[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagString) tag).getValue();
			this.array = newArray;
		}
		return null;
	}
	
	@Override
	public void removeTag(NamedBinaryTag tag)
	{
		// TODO
	}
	
	@Override
	public boolean canAddTag(String name)
	{
		return true;
	}
	
	/**
	 * Creates a new array iterator that returns the value, not a
	 * {@code NamedBinaryTag} wrapper object.
	 */
	@Override
	public Iterator iterator()
	{
		return new Iterator()
		{
			private Object	array	= NBTTagArray.this.array;
			private int		len		= NBTTagArray.this.length;
			private int		index	= 0;
			
			@Override
			public boolean hasNext()
			{
				return this.index < this.len;
			}
			
			@Override
			public Object next()
			{
				Object value = Array.get(this.array, this.index);
				this.index++;
				return value;
			}
			
			@Override
			public void remove()
			{
				// TODO
			}
		};
	}
	
	@Override
	public int size()
	{
		return this.length;
	}
	
	@Override
	public void clear()
	{
		byte type = this.subtype;
		if (type == TYPE_NBT)
		{
			this.array = new NamedBinaryTag[0];
		}
		else if (type == TYPE_BOOLEAN)
		{
			this.array = new boolean[0];
		}
		else if (type == TYPE_BYTE || type == TYPE_NIBBLE)
		{
			this.array = new byte[0];
		}
		else if (type == TYPE_SHORT)
		{
			this.array = new short[0];
		}
		else if (type == TYPE_CHAR)
		{
			this.array = new char[0];
		}
		else if (type == TYPE_INT || type == TYPE_MEDIUM)
		{
			this.array = new int[0];
		}
		else if (type == TYPE_LONG)
		{
			this.array = new long[0];
		}
		else if (type == TYPE_FLOAT)
		{
			this.array = new float[0];
		}
		else if (type == TYPE_DOUBLE)
		{
			this.array = new double[0];
		}
		else if (type == TYPE_STRING)
		{
			this.array = new String[0];
		}
	}
	
	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		byte type = this.subtype;
		
		output.writeByte(type);
		
		if (type == TYPE_NBT)
		{
			NamedBinaryTag[] nbtArray = (NamedBinaryTag[]) this.array;
			int len = nbtArray.length;
			
			output.writeInt(len);
			for (int i = 0; i < len; i++)
			{
				output.writeNBT(nbtArray[i]);
			}
		}
		else if (type == TYPE_BOOLEAN)
		{
			boolean[] booleanArray = (boolean[]) this.array;
			output.writeBooleanArray(booleanArray);
		}
		else if (type == TYPE_NIBBLE)
		{
			byte[] nibbleArray = (byte[]) this.array;
			output.writeNibbleArray(nibbleArray);
		}
		else if (type == TYPE_BYTE)
		{
			byte[] byteArray = (byte[]) this.array;
			output.writeByteArray(byteArray);
		}
		else if (type == TYPE_SHORT)
		{
			short[] shortArray = (short[]) this.array;
			output.writeShortArray(shortArray);
		}
		else if (type == TYPE_CHAR)
		{
			char[] charArray = (char[]) this.array;
			output.writeCharArray(charArray);
		}
		else if (type == TYPE_MEDIUM)
		{
			int[] mediumArray = (int[]) this.array;
			output.writeMediumArray(mediumArray);
		}
		else if (type == TYPE_INT)
		{
			int[] intArray = (int[]) this.array;
			output.writeIntArray(intArray);
		}
		else if (type == TYPE_LONG)
		{
			long[] longArray = (long[]) this.array;
			output.writeLongArray(longArray);
		}
		else if (type == TYPE_FLOAT)
		{
			float[] floatArray = (float[]) this.array;
			output.writeFloatArray(floatArray);
		}
		else if (type == TYPE_DOUBLE)
		{
			double[] doubleArray = (double[]) this.array;
			output.writeDoubleArray(doubleArray);
		}
		else if (type == TYPE_STRING)
		{
			String[] stringArray = (String[]) this.array;
			output.writeStringArray(stringArray);
		}
	}
	
	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		byte type = input.readByte();
		
		if (type == TYPE_NBT)
		{
			int len = input.readInt();
			NamedBinaryTag[] nbtArray = new NamedBinaryTag[len];
			for (int i = 0; i < len; i++)
			{
				nbtArray[i] = input.readNBT();
			}
			this.array = nbtArray;
		}
		else if (type == TYPE_BOOLEAN)
		{
			boolean[] a = input.readBooleanArray();
			this.length = a.length;
			this.array = a;
		}
		else if (type == TYPE_NIBBLE)
		{
			byte[] a = input.readNibbleArray();
			this.length = a.length;
			this.array = a;
		}
		else if (type == TYPE_BYTE)
		{
			byte[] a = input.readByteArray();
			this.length = a.length;
			this.array = a;
		}
		else if (type == TYPE_SHORT)
		{
			short[] a = input.readShortArray();
			this.length = a.length;
			this.array = a;
		}
		else if (type == TYPE_CHAR)
		{
			char[] a = input.readCharArray();
			this.length = a.length;
			this.array = a;
		}
		else if (type == TYPE_MEDIUM)
		{
			int[] a = input.readMediumArray();
			this.length = a.length;
			this.array = a;
		}
		else if (type == TYPE_INT)
		{
			int[] a = input.readIntArray();
			this.length = a.length;
			this.array = a;
		}
		else if (type == TYPE_LONG)
		{
			long[] a = input.readLongArray();
			this.length = a.length;
			this.array = a;
		}
		else if (type == TYPE_FLOAT)
		{
			float[] a = input.readFloatArray();
			this.length = a.length;
			this.array = a;
		}
		else if (type == TYPE_DOUBLE)
		{
			double[] a = input.readDoubleArray();
			this.length = a.length;
			this.array = a;
		}
		else if (type == TYPE_STRING)
		{
			String[] a = input.readStringArray();
			this.length = a.length;
			this.array = a;
		}
		
		this.subtype = type;
	}
	
	@Override
	public String writeString()
	{
		byte type = this.subtype;
		if (type == TYPE_NBT)
		{
			NamedBinaryTag[] tags = (NamedBinaryTag[]) this.array;
			
			if (tags == null)
				return "null";
			int max = tags.length - 1;
			if (max == -1)
				return "[]";
			
			StringBuilder b = new StringBuilder();
			b.append('[');
			for (int i = 0;; i++)
			{
				b.append(tags[i].writeString());
				if (i == max)
				{
					break;
				}
				b.append(',');
			}
			b.append(']');
			return b.toString();
		}
		else if (type == TYPE_BOOLEAN)
		{
			return Arrays.toString(this.getBooleanArray());
		}
		else if (type == TYPE_BYTE || type == TYPE_NIBBLE)
		{
			return Arrays.toString(this.getByteArray());
		}
		else if (type == TYPE_SHORT)
		{
			return Arrays.toString(this.getShortArray());
		}
		else if (type == TYPE_CHAR)
		{
			return Arrays.toString(this.getCharArray());
		}
		else if (type == TYPE_INT || type == TYPE_MEDIUM)
		{
			return Arrays.toString(this.getIntArray());
		}
		else if (type == TYPE_LONG)
		{
			return Arrays.toString(this.getLongArray());
		}
		else if (type == TYPE_FLOAT)
		{
			return Arrays.toString(this.getFloatArray());
		}
		else if (type == TYPE_DOUBLE)
		{
			return Arrays.toString(this.getDoubleArray());
		}
		else if (type == TYPE_STRING)
		{
			return Arrays.toString(this.getStringArray());
		}
		else
		{
			return "[]";
		}
	}
	
	@Override
	public void readString(String dataString)
	{
		if ("[]".equals(dataString))
		{
			this.length = 0;
			return;
		}
		
		int pos1 = dataString.indexOf('[') + 1;
		int pos2 = dataString.lastIndexOf(']');
		if (pos1 < 0 || pos2 < 0)
		{
			return;
		}
		dataString = dataString.substring(pos1, pos2);
		
		List<String> tags = NBTHelper.split(dataString, ',');
		int len = tags.size();
		
		NamedBinaryTag[] nbts = new NamedBinaryTag[len];
		
		for (int i = 0; i < len; i++)
		{
			String sub = tags.get(i);
			NamedBinaryTag tag = NBTParser.createTag(sub);
			
			try
			{
				int index = NBTHelper.parseInt(tag.getName(), i);
				nbts[index] = tag;
			}
			catch (NumberFormatException ex)
			{
				nbts[i] = tag;
			}
		}
		
		this.unwrap(nbts);
	}
	
	public void unwrap(NamedBinaryTag[] nbtArray)
	{
		int len = nbtArray.length;
		byte type = -1;
		
		for (int i = 0; i < len; i++)
		{
			byte t = nbtArray[i].getType();
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
				NBTTagBoolean tag = (NBTTagBoolean) nbtArray[i];
				booleanArray[i] = tag.getBool();
			}
			this.array = booleanArray;
		}
		else if (type == TYPE_BYTE || type == TYPE_NIBBLE)
		{
			byte[] byteArray = new byte[len];
			for (int i = 0; i < len; i++)
			{
				NBTTagNumber tag = (NBTTagNumber) nbtArray[i];
				byteArray[i] = tag.getByte();
			}
			this.array = byteArray;
		}
		else if (type == TYPE_SHORT)
		{
			short[] shortArray = new short[len];
			for (int i = 0; i < len; i++)
			{
				NBTTagNumber tag = (NBTTagNumber) nbtArray[i];
				shortArray[i] = tag.getShort();
			}
			this.array = shortArray;
		}
		else if (type == TYPE_CHAR)
		{
			char[] charArray = new char[len];
			for (int i = 0; i < len; i++)
			{
				NBTTagChar tag = (NBTTagChar) nbtArray[i];
				charArray[i] = tag.getChar();
			}
			this.array = charArray;
		}
		else if (type == TYPE_INT || type == TYPE_MEDIUM)
		{
			int[] intArray = new int[len];
			for (int i = 0; i < len; i++)
			{
				NBTTagNumber tag = (NBTTagNumber) nbtArray[i];
				intArray[i] = tag.getInt();
			}
			this.array = intArray;
		}
		else if (type == TYPE_LONG)
		{
			long[] longArray = new long[len];
			for (int i = 0; i < len; i++)
			{
				NBTTagNumber tag = (NBTTagNumber) nbtArray[i];
				longArray[i] = tag.getLong();
			}
			this.array = longArray;
		}
		else if (type == TYPE_FLOAT)
		{
			float[] floatArray = new float[len];
			for (int i = 0; i < len; i++)
			{
				NBTTagNumber tag = (NBTTagNumber) nbtArray[i];
				floatArray[i] = tag.getFloat();
			}
			this.array = floatArray;
		}
		else if (type == TYPE_DOUBLE)
		{
			double[] doubleArray = new double[len];
			for (int i = 0; i < len; i++)
			{
				NBTTagNumber tag = (NBTTagNumber) nbtArray[i];
				doubleArray[i] = tag.getDouble();
			}
			this.array = doubleArray;
		}
		else if (type == TYPE_STRING)
		{
			String[] stringArray = new String[len];
			for (int i = 0; i < len; i++)
			{
				NBTTagString tag = (NBTTagString) nbtArray[i];
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
	
	protected NBTTagArray setNBTArray(NamedBinaryTag[] objects)
	{
		this.array = objects;
		this.subtype = TYPE_NBT;
		this.length = objects.length;
		return this;
	}
	
	protected NBTTagArray setBooleanArray(boolean[] booleanArray)
	{
		this.array = booleanArray;
		this.subtype = TYPE_BOOLEAN;
		this.length = booleanArray.length;
		return this;
	}
	
	protected NBTTagArray setNibbleArray(byte[] nibbleArray)
	{
		this.array = nibbleArray;
		this.subtype = TYPE_NIBBLE;
		this.length = nibbleArray.length;
		return this;
	}
	
	protected NBTTagArray setByteArray(byte[] byteArray)
	{
		this.array = byteArray;
		this.subtype = TYPE_BYTE;
		this.length = byteArray.length;
		return this;
	}
	
	protected NBTTagArray setShortArray(short[] shortArray)
	{
		this.array = shortArray;
		this.subtype = TYPE_SHORT;
		this.length = shortArray.length;
		return this;
	}
	
	protected NBTTagArray setCharArray(char[] charArray)
	{
		this.array = charArray;
		this.subtype = TYPE_CHAR;
		this.length = charArray.length;
		return this;
	}
	
	protected NBTTagArray setMediumArray(int[] mediumArray)
	{
		this.array = mediumArray;
		this.subtype = TYPE_MEDIUM;
		this.length = mediumArray.length;
		return this;
	}
	
	protected NBTTagArray setIntArray(int[] intArray)
	{
		this.array = intArray;
		this.subtype = TYPE_INT;
		this.length = intArray.length;
		return this;
	}
	
	protected NBTTagArray setLongArray(long[] longArray)
	{
		this.array = longArray;
		this.subtype = TYPE_LONG;
		this.length = longArray.length;
		return this;
	}
	
	protected NBTTagArray setFloatArray(float[] floatArray)
	{
		this.array = floatArray;
		this.subtype = TYPE_FLOAT;
		this.length = floatArray.length;
		return this;
	}
	
	protected NBTTagArray setDoubleArray(double[] doubleArray)
	{
		this.array = doubleArray;
		this.subtype = TYPE_DOUBLE;
		this.length = doubleArray.length;
		return this;
	}
	
	protected NBTTagArray setStringArray(String[] stringArray)
	{
		this.array = stringArray;
		this.subtype = TYPE_STRING;
		this.length = stringArray.length;
		return this;
	}
}
