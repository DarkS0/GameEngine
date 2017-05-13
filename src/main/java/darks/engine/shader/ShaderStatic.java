package main.java.darks.engine.shader;

public class ShaderStatic extends ShaderBase
{
	public ShaderStatic()
	{
		super(SHADER_PATH + "static.vtx", SHADER_PATH + "static.frg");
	}

	@Override
	protected void bindAttribs()
	{
		bindAttrib(0, "position");
	}
}
