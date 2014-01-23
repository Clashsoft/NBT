package com.clashsoft.nbt;

public interface NBTTagContainer<T> extends Iterable<T>
{
	/**
	 * Adds a new tag to the container.
	 * <p>
	 * If an old value was replaced, that value is returned. This is currently
	 * only used by {@link NBTTagCompound}
	 * 
	 * @param tag
	 *            the new tag
	 * @return the old tag, or {@code null} if no old tag was replaced
	 */
	public NamedBinaryTag addTag(NamedBinaryTag tag);
	
	/**
	 * Removed the tag from this container.
	 * 
	 * @param tag
	 *            the tag to remove
	 */
	public void removeTag(NamedBinaryTag tag);
	
	/**
	 * Checks if a tag with the name {@code name} can be added to the container
	 * without replacing old tags.
	 * <p>
	 * This is currently only used by {@link NBTTagCompound}
	 * 
	 * @param name
	 *            the name of the tag to be added
	 * @return {@code true} if there is no tag with the name {@code name} in
	 *         this container
	 */
	public boolean canAddTag(String name);
}
