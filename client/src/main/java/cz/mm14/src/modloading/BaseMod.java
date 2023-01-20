package cz.mm14.src.modloading;

public abstract class BaseMod
{
	public String name;
	public String version;

	public BaseMod()
	{
		this.load();
	}
	
	public abstract void load();
}