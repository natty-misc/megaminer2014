package cz.mm14.src.block;


public class BlockRegular extends Block
{
	public BlockRegular(String id, String name, String texloc, String genM, boolean solid)
	{
		super(name);
		this.setTextures(texloc);
		this.genMark = genM;
		this.solid = solid;
		this.id = id;
		this.name = name;
	}
}
