package main.java.darks.engine.renderer;

import main.java.darks.engine.model.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Renderer
{
	public void pre()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(0, 0, 1, 1);
	}

	public void render(RawModel model)
	{
		GL30.glBindVertexArray(model.getVao());
		model.enableAttribs();
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		model.disableAttribs();
		GL30.glBindVertexArray(0);
	}
}
