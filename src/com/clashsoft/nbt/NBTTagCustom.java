package com.clashsoft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.clashsoft.nbt.util.NBTHelper;

public class NBTTagCustom extends NBTTagMap
{
	private static final String	TAG_CLASS_NAME	= "$class$";
	
	private Object				object;
	
	private int					mapDepth		= 0;
	
	public NBTTagCustom(String name)
	{
		this(name, null);
	}
	
	public NBTTagCustom(String name, Object object)
	{
		super(TYPE_CUSTOM, name);
		this.object = object;
	}
	
	@Override
	public Object getValue()
	{
		return this.object;
	}
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		this.map();
		super.writeValue(output);
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		super.readValue(input);
		this.object();
	}
	
	@Override
	public String writeString()
	{
		this.map();
		return super.writeString();
	}
	
	@Override
	public void readString(String dataString)
	{
		super.readString(dataString);
		this.object();
	}
	
	protected Object newInstance(String className)
	{
		try
		{
			Class clazz = Class.forName(className);
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
		if (this.tags == null)
		{
			this.mapDepth = 0;
			this.tags = new HashMap();
			this.map(this.tags, this.object);
		}
	}
	
	protected void object()
	{
		if (this.object == null)
		{
			this.object = this.object(this.tags);
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
			
			while (clazz != Object.class)
			{
				this.map(map, clazz, subclass, object);
				clazz = clazz.getSuperclass();
				subclass++;
			}
			map.put(TAG_CLASS_NAME, new NBTTagClass(TAG_CLASS_NAME, clazz));
			
			this.mapDepth++;
		}
	}
	
	protected Object object(Map<String, NamedBinaryTag> map)
	{
		String className = ((NBTTagString) map.get(TAG_CLASS_NAME)).getValue();
		Object object = this.newInstance(className);
		Class clazz = object.getClass();
		int subclass = 0;
		
		while (clazz != Object.class)
		{
			this.object(map, clazz, subclass, object);
			clazz = clazz.getSuperclass();
			subclass++;
		}
		
		return object;
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
					Object obj = NBTHelper.unwrap(nbt);
					
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
						NamedBinaryTag nbt = NBTHelper.wrap(name, obj);
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
