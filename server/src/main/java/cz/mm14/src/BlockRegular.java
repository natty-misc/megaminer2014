package cz.mm14.src;


public class BlockRegular extends Block
{
	public BlockRegular(String name, String id, boolean b)
	{
		super(id);
		this.name = name;
		this.genMark = id;
		this.c = b;
	}

	public Block setGenMark(String mark) {
		this.genMark = mark;
		
		return this;
	}
}
