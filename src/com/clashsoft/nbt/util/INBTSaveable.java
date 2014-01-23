package com.clashsoft.nbt.util;

import com.clashsoft.nbt.NBTTagCompound;

public interface INBTSaveable
{
	public void writeToNBT(NBTTagCompound nbt);
	
	public void readFromNBT(NBTTagCompound nbt);
}
