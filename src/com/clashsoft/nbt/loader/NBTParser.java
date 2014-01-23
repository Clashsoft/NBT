package com.clashsoft.nbt.loader;

import java.util.Collection;

import com.clashsoft.nbt.NamedBinaryTag;

public class NBTParser
{
	public static NamedBinaryTag parse(Collection<String> lines)
	{
		String allLines = convert(lines);
		
		return parseTag(allLines);
	}
	
	public static NamedBinaryTag parseTag(String tag)
	{
		StringBuilder tagType = new StringBuilder();
		StringBuilder tagName = new StringBuilder();
		StringBuilder tagValue = new StringBuilder();
		
		int depth1 = 0; // Depth of ( )
		int depth2 = 0; // Depth of [ ]
		int depth3 = 0; // Depth of { }
		boolean quote = false;
		
		int next = -1;
		boolean nextValid = false;
		
		int i0 = tag.length();
		for (int i = 0; i < i0; i++)
		{
			char c = tag.charAt(i);
			
			if (c == '"' && !(i > 0 && tag.charAt(i - 1) == '\\'))
			{
				quote = !quote;
			}
			
			if (!quote)
			{
				if (c == '(')
				{
					depth1++;
					continue;
				}
				if (c == '[')
				{
					depth2++;
					continue;
				}
				if (c == '{')
				{
					depth3++;
					continue;
				}
				if (c == ')')
				{
					depth1--;
				}
				if (c == ']')
				{
					depth2--;
				}
				if (c == '}')
				{
					depth3--;
				}
				
				if (depth3 == 1 && depth1 == 0 && depth2 == 0)
				{
					if (c == ':')
					{
						nextValid = true;
						continue;
					}
					
					if (c == 't')
					{
						next = 0;
						nextValid = false;
					}
					else if (c == 'n')
					{
						next = 1;
						nextValid = false;
					}
					else if (c == 'v')
					{
						next = 2;
						nextValid = false;
					}
					
				}
			}
			
			if (nextValid || quote)
			{
				if (depth2 == 0 && next == 0)
				{
					tagType.append(c);
				}
				else if (depth2 == 1 && next == 1)
				{
					tagName.append(c);
				}
				else if (depth1 == 0 && depth2 == 1 && depth3 == 1 && next == 2) // Value
				{
					int i1 = tag.lastIndexOf("}") - 1;
					tagValue.append(tag.substring(i, i1));
					break;
				}
			}
		}
		
		return parseTag2(tagType.toString(), tagName.toString(), tagValue.toString());
	}
	
	private static NamedBinaryTag parseTag2(String tagType, String tagName, String tagValue)
	{
		byte type = Byte.parseByte(tagType);
		NamedBinaryTag base = NamedBinaryTag.createFromType(tagName, type);
		if (base != null)
		{
			base.readValueString(tagValue);
		}
		return base;
	}
	
	private static String convert(Collection<String> lines)
	{
		StringBuilder sb = new StringBuilder(lines.size() * 10);
		for (String s : lines)
		{
			sb.append(s).append('\n');
		}
		return sb.toString();
	}
}
