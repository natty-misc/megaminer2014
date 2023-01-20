package cz.mm14.world;

import cz.mm14.src.block.Block;

public class BlockInWorld
{
	public int x;
	public int y;
	public Block block;

	public BlockInWorld(int x, int y, Block block)
	{
		this.x = x;
		this.y = y;
		this.block = block;
	}
}