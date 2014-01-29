package com.clashsoft.nbt.tags.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.clashsoft.nbt.NamedBinaryTag;

public class NBTTagDate extends NamedBinaryTag
{
	public static DateFormat	FORMAT	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	protected Date				date;
	
	public NBTTagDate(String name)
	{
		this(name, new Date());
	}
	
	public NBTTagDate(String name, Date date)
	{
		super(TYPE_DATE, name);
		this.date = date;
	}
	
	@Override
	public Date getValue()
	{
		return this.getDate();
	}
	
	public Date getDate()
	{
		return this.date;
	}
	
	@Override
	public void writeValue(DataOutput output) throws IOException
	{
		long date = this.date == null ? -1 : this.date.getTime();
		output.writeLong(date);
	}
	
	@Override
	public void readValue(DataInput input) throws IOException
	{
		long date = input.readLong();
		this.date = date == -1 ? null : new Date(date);
	}
	
	@Override
	public String writeString()
	{
		String date = this.date == null ? "null" : FORMAT.format(this.date);
		return "date " + date;
	}
	
	@Override
	public void readString(String dataString)
	{
		dataString = dataString.substring(5);
		if ("null".equals(dataString))
		{
			this.date = null;
		}
		else
		{
			try
			{
				FORMAT.parse(dataString);
			}
			catch (ParseException ex)
			{
				this.date = null;
			}
		}
	}
	
}
