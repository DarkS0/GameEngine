package main.java.darks.engine.loader;


import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import main.java.darks.engine.model.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Loader
{
	private IntList vaos = new IntArrayList(), vbos = new IntArrayList();

	public RawModel load(float[] pos, int[] indices)
	{
		int vao = newVAO();

		storeIndices(indices);
		store(0, pos);

		GL30.glBindVertexArray(0);

		return new RawModel(vao, indices.length);
	}

	private void store(int attribute, float[] data)
	{
		newVBO();

		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, toBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attribute, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private void storeIndices(int[] data)
	{
		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);

		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, toBuffer(data), GL15.GL_STATIC_DRAW);
	}

	private int newVAO()
	{
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		vaos.add(vao);

		return vao;
	}

	private int newVBO()
	{
		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

		vbos.add(vbo);

		return vbo;
	}

	private IntBuffer toBuffer(int[] data)
	{
		return (IntBuffer) BufferUtils.createIntBuffer(data.length).put(data).flip();
	}

	private FloatBuffer toBuffer(float[] data)
	{
		return (FloatBuffer) BufferUtils.createFloatBuffer(data.length).put(data).flip();
	}

	public void cleanup()
	{
		vaos.forEach(GL30::glDeleteVertexArrays);
		vbos.forEach(GL15::glDeleteBuffers);
	}
}
