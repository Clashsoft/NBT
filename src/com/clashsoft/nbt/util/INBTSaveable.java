package com.clashsoft.nbt.util;

import com.clashsoft.nbt.tags.collection.NBTTagCompound;

public interface INBTSaveable
{
	void writeToNBT(NBTTagCompound nbt);

	void readFromNBT(NBTTagCompound nbt);
}
