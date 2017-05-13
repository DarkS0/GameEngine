package main.java.darks.engine.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;

public abstract class ShaderBase
{
	public static final String SHADER_PATH = "src/main/resources/shader/";

	private int id, vertex, fragment;

	public ShaderBase(String vertex, String fragment)
	{
		this.vertex = load(vertex, GL20.GL_VERTEX_SHADER);
		this.fragment = load(fragment, GL20.GL_FRAGMENT_SHADER);
		id = GL20.glCreateProgram();
		GL20.glAttachShader(id, this.vertex);
		GL20.glAttachShader(id, this.fragment);
		GL20.glLinkProgram(id);
		GL20.glValidateProgram(id);
		bindAttribs();
	}

	public void start()
	{
		GL20.glUseProgram(id);
	}

	public void stop()
	{
		GL20.glUseProgram(0);
	}

	public void cleanup()
	{
		stop();
		GL20.glDetachShader(id, vertex);
		GL20.glDetachShader(id, fragment);
		GL20.glDeleteShader(vertex);
		GL20.glDeleteShader(fragment);
		GL20.glDeleteProgram(id);
	}

	protected abstract void bindAttribs();

	protected void bindAttrib(int attrib, String name)
	{
		GL20.glBindAttribLocation(id, attrib, name);
	}

	private static int load(String file, int type)
	{
		StringBuilder string = new StringBuilder();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			reader.lines().forEach(line -> string.append(line).append("\n"));
			reader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		int id = GL20.glCreateShader(type);
		GL20.glShaderSource(id, string);
		GL20.glCompileShader(id);
		if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.err.println(GL20.glGetShaderInfoLog(id, 512));
			System.exit(-1);
		}

		return id;
	}
}
