package com.clashsoft.nbt;

public interface INBTSaveable
{
	public void writeToNBT(NBTTagCompound nbt);
	public void readFromNBT(NBTTagCompound nbt);
}
