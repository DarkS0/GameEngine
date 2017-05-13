package main.java.darks.engine.game;

import main.java.darks.engine.display.DisplayManager;
import main.java.darks.engine.loader.Loader;
import main.java.darks.engine.model.RawModel;
import main.java.darks.engine.renderer.Renderer;
import main.java.darks.engine.shader.ShaderStatic;
import main.java.darks.engine.util.Util;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game
{
	public Loader loader;
	public Renderer renderer;
	public Timer timer;
	public ShaderStatic shaderStatic;
	public RawModel model;

	public List<Runnable> loadQueue = Collections.synchronizedList(new ArrayList<>());

	public Game()
	{
		start();
	}

	private void start()
	{
		DisplayManager.create("Game Engine", 1920, 1080, 60);

		loader = new Loader();
		renderer = new Renderer();
		shaderStatic = new ShaderStatic();

		model = loader.load(new float[]{-0.5f, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0, 0.5f, 0.5f, 0}, new int[]{0, 1, 3, 3, 1, 2});

		timer = new Timer(20, 60, this::update, this::render);
		new Thread(timer, "Timer Thread").start();

		while (timer.isRunning())
			timer.runRender();
	}

	private void update()
	{
		if (Display.isCloseRequested())
		{
			timer.stop();
			return;
		}
	}

	private void render()
	{
		System.out.println(timer.getFrames());

		if (Display.isCloseRequested())
		{
			timer.stop();

			return;
		}

		runLoadQueue();

		renderer.pre();
		shaderStatic.start();

		renderer.render(model);

		shaderStatic.stop();
		DisplayManager.update();
	}

	private synchronized void runLoadQueue()
	{
		if (loadQueue.isEmpty()) return;

		Util.forEachClear(loadQueue, Runnable::run);
	}

	public void loadQueue(Runnable runnable)
	{
		loadQueue.add(runnable);
	}
}
