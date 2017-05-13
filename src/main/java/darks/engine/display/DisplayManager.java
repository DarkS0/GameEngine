package main.java.darks.engine.display;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.*;

public class DisplayManager
{
	public static String title;
	public static int width, height, fpsCap;

	public static void create(String title, int width, int height, int fpsCap)
	{
		DisplayManager.title = title;
		DisplayManager.width = width;
		DisplayManager.height = height;
		DisplayManager.fpsCap = fpsCap;

		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(new PixelFormat(), null,new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true));
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, width, height);
	}

	public static void update()
	{
		Display.sync(fpsCap);
	}

	public static void close()
	{
		Display.destroy();
	}
}
