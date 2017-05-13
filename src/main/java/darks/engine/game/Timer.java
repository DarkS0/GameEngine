package main.java.darks.engine.game;

public class Timer implements Runnable
{
	private static int SEC = 1000_000_000;

	private long lastUpdated, lastRendered, lastSec, updatePeriod, renderPeriod;
	private int updateQueue, updatesCount, framesCount, ups, fps, ctxUpdates, ctxFrames;
	private boolean render, running = true, fpsCap = true;
	private float partialUpdate;

	private Runnable renderHook, updateHook;

	public Timer(int updates, int frames, Runnable updateHook, Runnable renderHook)
	{
		setUpdates(updates);
		setFrames(frames);
		setUpdateHook(updateHook);
		setRenderHook(renderHook);
	}

	@Override
	public void run()
	{
		running = true;
		while (running)
		{
			update();
			testLag();
		}
	}

	public void runRender()
	{
		while (updateQueue > 0)
		{
			updateHook.run();
			updateQueue--;
			updatesCount++;
		}

		if (!fpsCap) render();
		else if (render)
		{
			render = false;
			render();
			lastRendered = System.nanoTime();
			testLag();
		}
	}

	private void update()
	{
		long time = System.nanoTime();
		if (time - lastUpdated >= updatePeriod)
		{
			lastUpdated = time;
			updateQueue++;
		}

		time = System.nanoTime();
		if (!fpsCap || time - lastRendered >= renderPeriod)
			render = true;

		time = System.nanoTime();
		if (time - lastSec >= SEC)
		{
			lastSec = time;
			ctxUpdates = updatesCount;
			ctxFrames = framesCount;
			updatesCount = 0;
			framesCount = 0;
		}
	}

	private void render()
	{
		partialUpdate = (float) ((double) (System.nanoTime() - lastUpdated) / SEC * ups);
		renderHook.run();
		framesCount++;
	}

	private void testLag()
	{
		if (fpsCap && fps < ctxFrames * 1.2 && ups < ctxUpdates * 1.2) try
		{
			Thread.sleep(500000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void stop()
	{
		running = false;
	}

	public int getFrames()
	{
		return ctxFrames;
	}

	public void setUpdates(int updates)
	{
		this.ups = updates;
		updatePeriod = SEC / updates;
	}

	public int getUpdates()
	{
		return ctxUpdates;
	}

	public void setFrames(int frames)
	{
		this.fps = frames;
		renderPeriod = SEC / frames;
	}

	public float getPartialUpdate()
	{
		return partialUpdate;
	}

	public int getTargetFrames()
	{
		return fps;
	}

	public int getTargetUpdates()
	{
		return ups;
	}

	public void setFrameCap(boolean fpsCap)
	{
		this.fpsCap = fpsCap;
	}

	public boolean isRunning()
	{
		return running;
	}

	public void setUpdateHook(Runnable updateHook)
	{
		this.updateHook = updateHook;
	}

	public void setRenderHook(Runnable renderHook)
	{
		this.renderHook = renderHook;
	}
}
