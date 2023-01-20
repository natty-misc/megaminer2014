package cz.mm14.src.world;

import cz.mm14.src.Block;

public class BlockInWorld {
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
