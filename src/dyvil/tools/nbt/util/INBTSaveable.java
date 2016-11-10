package dyvil.tools.nbt.util;

import dyvil.tools.nbt.collection.NBTMap;

public interface INBTSaveable
{
	void writeToNBT(NBTMap nbt);

	void readFromNBT(NBTMap nbt);
}
