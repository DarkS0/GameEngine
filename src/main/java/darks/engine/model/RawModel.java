package main.java.darks.engine.model;

import org.lwjgl.opengl.GL20;

public class RawModel
{
	protected int vao, vertexCount;

	public RawModel(int vao, int vertexCount)
	{
		this.vao = vao;
		this.vertexCount = vertexCount;
	}
	public void enableAttribs()
	{
		GL20.glEnableVertexAttribArray(0);
	}

	public void disableAttribs()
	{
		GL20.glDisableVertexAttribArray(0);
	}

	public int getVao()
	{
		return vao;
	}

	public int getVertexCount()
	{
		return vertexCount;
	}
}
