package com.clashsoft.nbt.tags.data;

import java.io.DataInput;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.clashsoft.nbt.NamedBinaryTag;
import com.clashsoft.nbt.tags.collection.NBTTagCompound;
import com.clashsoft.nbt.tags.collection.NBTTagMap;
import com.clashsoft.nbt.util.NBTParser;

public class NBTTagCustom extends NBTTagMap
{
	private static final String	TAG_CLASS_NAME	= "$class$";
	
	protected Object			value;
	private int					mapDepth		= 0;
	
	public NBTTagCustom(String name)
	{
		this(name, null);
	}
	
	public NBTTagCustom(String name, Object object)
	{
		super(TYPE_CUSTOM, name);
		this.setValue(object);
	}
	
	@Override
	public Object getValue()
	{
		return this.value;
	}
	
	public NBTTagCustom setValue(Object object)
	{
		this.value = object;
		this.map();
		return this;
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		super.readValue(input);
		this.object();
	}
	
	@Override
	public void readString(String dataString)
	{
		super.readString(dataString);
		this.object();
	}
	
	protected Object newInstance(Class clazz)
	{
		try
		{
			Constructor c = clazz.getConstructor();
			return c.newInstance();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	protected String getFieldName(String subclass, Field field)
	{
		return subclass + "$" + field.getName();
	}
	
	protected void map()
	{
		this.mapDepth = 0;
		this.tags = new HashMap();
		this.map(this.tags, this.value);
	}
	
	protected void object()
	{
		if (this.value == null)
		{
			this.value = this.object(this.tags);
		}
	}
	
	protected void map(Map<String, NamedBinaryTag> map, Object object)
	{
		if (this.mapDepth < 32)
		{
			if (object == null)
			{
				return;
			}
			
			Class clazz = object.getClass();
			int subclass = 0;
			
			map.put(TAG_CLASS_NAME, new NBTTagClass(TAG_CLASS_NAME, clazz));
			
			while (clazz != Object.class)
			{
				this.map(map, clazz, subclass, object);
				clazz = clazz.getSuperclass();
				subclass++;
			}
			
			this.mapDepth++;
		}
	}
	
	protected Object object(Map<String, NamedBinaryTag> map)
	{
		NBTTagClass classTag = (NBTTagClass) map.get(TAG_CLASS_NAME);
		if (classTag != null)
		{
			Class clazz = classTag.clazz;
			Object object = this.newInstance(clazz);
			int subclass = 0;
			
			while (clazz != Object.class)
			{
				this.object(map, clazz, subclass, object);
				clazz = clazz.getSuperclass();
				subclass++;
			}
			
			return object;
		}
		return null;
	}
	
	protected void object(Map<String, NamedBinaryTag> map, Class clazz, int subclass, Object object)
	{
		Field[] fields = clazz.getDeclaredFields();
		String sub = subclass + "";
		int len = fields.length;
		
		for (int i = 0; i < len; i++)
		{
			Field field = fields[i];
			if (!Modifier.isStatic(field.getModifiers()))
			{
				field.setAccessible(true);
				try
				{
					String name = this.getFieldName(sub, field);
					NamedBinaryTag nbt = map.get(name);
					Object obj = NBTParser.unwrap(nbt);
					
					if (obj instanceof Map)
					{
						obj = this.object((Map) obj);
					}
					
					field.set(object, obj);
				}
				catch (ReflectiveOperationException ex)
				{
					
				}
			}
		}
	}
	
	protected void map(Map<String, NamedBinaryTag> map, Class clazz, int subclass, Object object)
	{
		Field[] fields = clazz.getDeclaredFields();
		String sub = subclass + "";
		int len = fields.length;
		
		for (int i = 0; i < len; i++)
		{
			Field field = fields[i];
			if (!Modifier.isStatic(field.getModifiers()))
			{
				field.setAccessible(true);
				try
				{
					String name = this.getFieldName(sub, field);
					Object obj = field.get(object);
					if (obj != null)
					{
						NamedBinaryTag nbt = NBTParser.wrap(name, obj);
						if (nbt == null && !name.contains("this$") && this.mapDepth < 32)
						{
							Map<String, NamedBinaryTag> map1 = new HashMap();
							this.map(map1, obj);
							nbt = new NBTTagCompound(name, map1);
						}
						map.put(name, nbt);
					}
				}
				catch (ReflectiveOperationException ex)
				{
					
				}
			}
		}
	}
}
