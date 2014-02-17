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
	public static final byte	TYPE_NBT			= 0;
	
	public static final boolean	CONVERT_BOOLEANS	= false;
	
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
		int newlen = this.length + 1;
		if (type == TYPE_NBT)
		{
			NamedBinaryTag[] oldArray = this.getNBTArray();
			NamedBinaryTag[] newArray = new NamedBinaryTag[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = tag;
		}
		else if (type == TYPE_BOOLEAN)
		{
			boolean[] oldArray = this.getBooleanArray();
			boolean[] newArray = new boolean[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagBoolean)tag).getBool();
		}
		else if (type == TYPE_BYTE)
		{
			byte[] oldArray = this.getByteArray();
			byte[] newArray = new byte[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber)tag).getByte();
		}
		else if (type == TYPE_SHORT)
		{
			short[] oldArray = this.getShortArray();
			short[] newArray = new short[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber)tag).getShort();
		}
		else if (type == TYPE_CHAR)
		{
			char[] oldArray = this.getCharArray();
			char[] newArray = new char[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagChar)tag).getChar();
		}
		else if (type == TYPE_INT)
		{
			int[] oldArray = this.getIntArray();
			int[] newArray = new int[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber)tag).getInt();
		}
		else if (type == TYPE_LONG)
		{
			long[] oldArray = this.getLongArray();
			long[] newArray = new long[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber)tag).getLong();
		}
		else if (type == TYPE_FLOAT)
		{
			float[] oldArray = this.getFloatArray();
			float[] newArray = new float[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber)tag).getFloat();
		}
		else if (type == TYPE_DOUBLE)
		{
			double[] oldArray = this.getDoubleArray();
			double[] newArray = new double[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagNumber)tag).getDouble();
		}
		else if (type == TYPE_STRING)
		{
			String[] oldArray = this.getStringArray();
			String[] newArray = new String[newlen];
			System.arraycopy(oldArray, 0, newArray, 0, newlen);
			newArray[oldlen] = ((NBTTagString)tag).getValue();
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
			private int		len		= Array.getLength(this.array);
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
		// TODO
	}
	
	public static byte[] boolToByte(int l, boolean[] booleanArray)
	{
		int len = booleanArray.length;
		byte[] bytes = new byte[l];
		for (int i = 0; i < len; i++)
		{
			if (booleanArray[i])
			{
				int a = i >> 3;
				int p = i & 7;
				bytes[a] |= 1 << p;
			}
		}
		return bytes;
	}
	
	public static boolean[] byteToBool(int l, byte[] byteArray)
	{
		int len = byteArray.length;
		boolean[] bools = new boolean[l];
		for (int i = 0; i < l; i++)
		{
			int a = i >> 3;
			int p = i & 7;
			bools[i] = (byteArray[a] & 1 << p) != 0;
		}
		return bools;
	}
	
	@Override
	public void writeValue(NBTOutputStream output) throws IOException
	{
		byte type = this.subtype;
		int len = this.length;
		
		output.writeByte(type);
		output.writeInt(len);
		
		if (type == TYPE_NBT)
		{
			NamedBinaryTag[] nbtArray = (NamedBinaryTag[]) this.array;
			for (int i = 0; i < len; i++)
			{
				nbtArray[i].write(output);
			}
		}
		else if (type == TYPE_BOOLEAN)
		{
			boolean[] booleanArray = (boolean[]) this.array;
			if (CONVERT_BOOLEANS)
			{
				int l = (len >> 3) + 1;
				byte[] bytes = boolToByte(l, booleanArray);
				for (int i = 0; i < l; i++)
				{
					output.writeByte(bytes[i]);
				}
			}
			else
			{
				for (int i = 0; i < len; i++)
				{
					output.writeBoolean(booleanArray[i]);
				}
			}
		}
		else if (type == TYPE_BYTE)
		{
			byte[] byteArray = (byte[]) this.array;
			for (int i = 0; i < len; i++)
			{
				output.writeByte(byteArray[i]);
			}
		}
		else if (type == TYPE_SHORT)
		{
			short[] shortArray = (short[]) this.array;
			for (int i = 0; i < len; i++)
			{
				output.writeShort(shortArray[i]);
			}
		}
		else if (type == TYPE_CHAR)
		{
			char[] charArray = (char[]) this.array;
			for (int i = 0; i < len; i++)
			{
				output.writeChar(charArray[i]);
			}
		}
		else if (type == TYPE_INT)
		{
			int[] intArray = (int[]) this.array;
			for (int i = 0; i < len; i++)
			{
				output.writeInt(intArray[i]);
			}
		}
		else if (type == TYPE_LONG)
		{
			long[] longArray = (long[]) this.array;
			for (int i = 0; i < len; i++)
			{
				output.writeLong(longArray[i]);
			}
		}
		else if (type == TYPE_FLOAT)
		{
			float[] floatArray = (float[]) this.array;
			for (int i = 0; i < len; i++)
			{
				output.writeFloat(floatArray[i]);
			}
		}
		else if (type == TYPE_DOUBLE)
		{
			double[] doubleArray = (double[]) this.array;
			for (int i = 0; i < len; i++)
			{
				output.writeDouble(doubleArray[i]);
			}
		}
		else if (type == TYPE_STRING)
		{
			String[] stringArray = (String[]) this.array;
			for (int i = 0; i < len; i++)
			{
				output.writeUTF(stringArray[i]);
			}
		}
	}
	
	@Override
	public void readValue(NBTInputStream input) throws IOException
	{
		byte type = input.readByte();
		int len = input.readInt();
		
		if (type == TYPE_NBT)
		{
			NamedBinaryTag[] nbtArray = new NamedBinaryTag[len];
			for (int i = 0; i < len; i++)
			{
				nbtArray[i] = NamedBinaryTag.read(input);
			}
			this.array = nbtArray;
		}
		else if (type == TYPE_BOOLEAN)
		{
			boolean[] booleanArray;
			if (CONVERT_BOOLEANS)
			{
				int l = (len >> 3) + 1;
				byte[] bytes = new byte[l];
				for (int i = 0; i < l; i++)
				{
					bytes[i] = input.readByte();
				}
				booleanArray = byteToBool(l, bytes);
			}
			else
			{
				booleanArray = new boolean[len];
				for (int i = 0; i < len; i++)
				{
					booleanArray[i] = input.readBoolean();
				}
			}
			this.array = booleanArray;
		}
		else if (type == TYPE_BYTE)
		{
			byte[] byteArray = new byte[len];
			for (int i = 0; i < len; i++)
			{
				byteArray[i] = input.readByte();
			}
			this.array = byteArray;
		}
		else if (type == TYPE_SHORT)
		{
			short[] shortArray = new short[len];
			for (int i = 0; i < len; i++)
			{
				shortArray[i] = input.readShort();
			}
			this.array = shortArray;
		}
		else if (type == TYPE_CHAR)
		{
			char[] charArray = new char[len];
			for (int i = 0; i < len; i++)
			{
				charArray[i] = input.readChar();
			}
			this.array = charArray;
		}
		else if (type == TYPE_INT)
		{
			int[] intArray = new int[len];
			for (int i = 0; i < len; i++)
			{
				intArray[i] = input.readInt();
			}
			this.array = intArray;
		}
		else if (type == TYPE_LONG)
		{
			long[] longArray = new long[len];
			for (int i = 0; i < len; i++)
			{
				longArray[i] = input.readLong();
			}
			this.array = longArray;
		}
		else if (type == TYPE_FLOAT)
		{
			float[] floatArray = new float[len];
			for (int i = 0; i < len; i++)
			{
				floatArray[i] = input.readFloat();
			}
			this.array = floatArray;
		}
		else if (type == TYPE_DOUBLE)
		{
			double[] doubleArray = new double[len];
			for (int i = 0; i < len; i++)
			{
				doubleArray[i] = input.readDouble();
			}
			this.array = doubleArray;
		}
		else if (type == TYPE_STRING)
		{
			String[] stringArray = new String[len];
			for (int i = 0; i < len; i++)
			{
				stringArray[i] = input.readUTF();
			}
			this.array = stringArray;
		}
		
		this.subtype = type;
		this.length = len;
	}
	
	@Override
	public String writeString()
	{
		byte type = this.subtype;
		if (type == TYPE_NBT)
		{
			return Arrays.toString(this.getNBTArray());
		}
		else if (type == TYPE_BOOLEAN)
		{
			return Arrays.toString(this.getBooleanArray());
		}
		else if (type == TYPE_BYTE)
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
		else if (type == TYPE_INT)
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
			int index = Integer.parseInt(tag.getName());
			nbts[index] = tag;
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
		else if (type == TYPE_BYTE)
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
		else if (type == TYPE_INT)
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
